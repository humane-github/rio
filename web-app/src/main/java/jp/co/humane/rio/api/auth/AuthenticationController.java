package jp.co.humane.rio.api.auth;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jp.co.humane.rio.api.consts.URL;
import jp.co.humane.rio.common.consts.AuthenticateId;
import jp.co.humane.rio.common.consts.DateFormat;
import jp.co.humane.rio.common.consts.LogId;
import jp.co.humane.rio.common.consts.ResultCode;
import jp.co.humane.rio.common.dto.ApiResult;
import jp.co.humane.rio.common.dto.ErrorInfo;
import jp.co.humane.rio.common.exception.ApplicationException;
import jp.co.humane.rio.common.utils.DateUtils;
import jp.co.humane.rio.common.utils.ValidationUtils;
import jp.co.humane.rio.orm.dao.EntryHistoryInfoDAO;
import jp.co.humane.rio.orm.dao.PersonMasterDAO;
import jp.co.humane.rio.orm.dto.PersonMasterDTO;
import jp.co.humane.rio.service.FaceRecognizeService;

/**
 * 認証IF。
 * @author terada
 *
 */
@RestController
public class AuthenticationController {

    /** ロガー */
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);

    /** 履歴画像ファイルの格納ディレクトリ */
    @Value("${rio.history.img-path}")
    private String historyDir = null;

    /** 顔認識サービス */
    @Autowired
    private FaceRecognizeService service = null;

    /** 個人情報マスタDAO */
    @Autowired
    private PersonMasterDAO personDao = null;

    /** 入退出履歴情報DAO */
    @Autowired
    private EntryHistoryInfoDAO historyDao = null;

    /** 顔認証のマッチング閾値 */
    @Value("${rio.openbr.threashold-ratio}")
    private double threasholdRatio = 0.0;

    /**
     * 認証IFコントローラ処理。
     * @param req    リクエストデータ。
     * @param result バリデーションチェック結果。
     * @return レスポンスデータ。
     */
    @PostMapping(URL.AUTH)
    @ResponseBody
    public ApiResult<Boolean>
        authenticate(@ModelAttribute @Valid AuthenticationRequest req, BindingResult result) {

        // レスポンスデータ
        ApiResult<Boolean> apiResult = new ApiResult<>();

        if (result.hasErrors()) {

            // バリデーションチェック結果がNGの場合はエラーを返却
            apiResult = new ApiResult<>();
            apiResult.setResultCode(ResultCode.VALIDATION_ERROR);
            List<ErrorInfo> errList = ValidationUtils.getErrorList(result);
            apiResult.setErrorList(errList);

            // エラー内容をログに出力
            LOGGER.info(LogId.I_CMN_VALID_ERR, ValidationUtils.getMessage(errList));

        } else {

            // 認証処理を実施する
            apiResult = doAuthenticate(req);
        }

        return apiResult;
    }

    /**
     * 認証処理を実施する。
     * @param req リクエストデータ。
     */
    private ApiResult<Boolean> doAuthenticate(AuthenticationRequest req) {

        ApiResult<Boolean> ret = new ApiResult<>();
        ret.setResultCode(ResultCode.SUCCESS);
        ret.setResultInfo(Boolean.FALSE);

        // openbrを使って顔認証を実施
        Map<String, Double> map = service.recognize(req.getImage());

        // 認証結果から個人IDを取得し、認証できない場合は認証NGを返す
        String personId = getRecognizedPersonId(map);
        if (null == personId) {
            return ret;
        }

        // 該当個人IDが指定のカメラ位置の入退室が可能か確認する
        String cameraId = req.getCameraId();
        Boolean canGoThrough = hasAuthentication(personId, cameraId);

        // 認証結果を履歴に蓄積する
        addAuthHistory(personId, cameraId, canGoThrough, req.getImage());

        // 蓄積出来たら正常終了を返す
        ret.setResultInfo(canGoThrough);
        return ret;
    }

    /**
     * openbrの認証結果から閾値を超えた個人IDを特定して返す。
     * 閾値を超えていない場合はnullを返す。
     *
     * @param map openbrの認証結果情報。
     * @return 認証された個人ID。
     */
    private String getRecognizedPersonId(Map<String, Double> map) {

        Entry<String, Double> maxEntry = map.entrySet().stream().reduce(null, (maxRatioEntry, entry) -> {

            // 最初は元の値を使う
            if (null == maxRatioEntry) {
                return entry;
            }

            // 以前の最大値よりも大きなマッチ率を持つものがあればそれを採用する
            Double maxRatio = maxRatioEntry.getValue();
            Double ratio = entry.getValue();
            if (maxRatio < ratio) {
                return entry;
            } else {
                return maxRatioEntry;
            }
        });

        // パスは 「個人ID/ファイル名」 の形式になっているのでそこから個人IDを抽出
        String personId = StringUtils.split(maxEntry.getKey(), "/")[0];
        LOGGER.debug("最大マッチ率={}、対象個人ID={}", maxEntry.getValue(), personId);

        // 最大のマッチ率が閾値を超えていない場合はnullを返す
        if (maxEntry.getValue() < threasholdRatio) {
            return null;
        }

        // 個人IDを返す
        return personId;
    }

    /**
     * 個人IDが指定カメラの部屋への入退室が可能かを確認する。
     * @param personId 個人ID。
     * @param cameraId カメラID。
     * @return 入退室可否。
     */
    private boolean hasAuthentication(String personId, String cameraId) {

        // 個人IDが存在しない場合はfalse、個人の権限がALLの場合はtrueを返す
        PersonMasterDTO cond = new PersonMasterDTO();
        cond.setPersonId(personId);
        PersonMasterDTO person = personDao.selectOneAvailable(cond);
        if (null == person) {
            return false;
        } else if (StringUtils.equals(person.getAuthId(), AuthenticateId.ALL)) {
            return true;
        }

        // それ以外の場合は入退室可能なカメラか否かで判断する
        return personDao.hasAuthentication(personId, cameraId);
    }

    /**
     * 認証の履歴を登録する。
     * @param personId   個人ID。
     * @param cameraId   カメラID。
     * @param authResult 認証結果。
     * @param image      認証に使用した画像情報。
     */
    private void addAuthHistory(String personId, String cameraId,
                                 boolean authResult, MultipartFile image) {

        // 現在日時を認証日時とする
        Date certifyDate = DateUtils.now();

        // 履歴の画像ファイルは「年/月/日/時分秒_カメラID.png」とする
        String dir = DateUtils.format(certifyDate, DateFormat.YMD_S);
        String fileName = DateUtils.format(certifyDate, DateFormat.HMS) + cameraId + ".png";

        // フォルダが存在しない場合は作成
        try {
            Files.createDirectories(Paths.get(historyDir, dir));
        }  catch (IOException ex) {
            LOGGER.error(LogId.E_AUTH_CREATE_DIR_ERR, dir);
            throw new ApplicationException(ex);
        }

        // 画像ファイルを保存する
        Path imgPath = Paths.get(historyDir, dir, fileName).toAbsolutePath();
        try {
            Files.write(imgPath, image.getBytes());
            LOGGER.debug("認証画像を保存しました。カメラID={}、個人ID={}、パス={}", cameraId, personId, imgPath.toString());
        } catch (IOException ex) {
            LOGGER.error(LogId.E_AUTH_IMG_FILE_SAVE_ERR, imgPath.toString());
            throw new ApplicationException(ex);
        }

        // DBに履歴を登録する
        try {
            historyDao.insertHistory(personId, Boolean.valueOf(authResult), cameraId,
                                    certifyDate, fileName);
        } catch (Exception ex) {
            // 履歴が登録できない場合は画像ファイルも削除する
            try {
                Files.delete(imgPath);
            } catch (IOException e) {
                // 処理なし
            }
            throw new ApplicationException("入退出履歴テーブル登録失敗。", ex);
        }
    }
}

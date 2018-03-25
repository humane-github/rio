package jp.co.humane.rio.api.entry.history;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jp.co.humane.rio.api.consts.URL;
import jp.co.humane.rio.common.consts.DateFormat;
import jp.co.humane.rio.common.consts.LogId;
import jp.co.humane.rio.common.consts.ResultCode;
import jp.co.humane.rio.common.dto.ApiResult;
import jp.co.humane.rio.common.dto.ErrorInfo;
import jp.co.humane.rio.common.utils.DateUtils;
import jp.co.humane.rio.common.utils.ValidationUtils;
import jp.co.humane.rio.orm.dao.EntryHistoryInfoDAO;
import jp.co.humane.rio.orm.dto.EntryHistoryDetailInfoDTO;
import jp.co.humane.rio.orm.dto.EntryHistoryDetailInfoParamDTO;

/**
 * 入退室履歴情報一覧取得IFコントローラ。
 * @author terada
 *
 */
@RestController
public class EntryHistoryController {

    /** ロガー */
    private static final Logger LOGGER = LoggerFactory.getLogger(EntryHistoryController.class);

    /** 入退出履歴情報DAO */
    @Autowired
    private EntryHistoryInfoDAO dao = null;

    /** 履歴URL */
    @Value("${rio.history.url}")
    private String historyUrl = null;

    /**
     * 入退室履歴情報一覧取得IFコントローラ処理。
     * @param req    リクエストデータ。
     * @param result バリデーションチェック結果。
     * @return レスポンスデータ。
     */
    @PostMapping(URL.ENTRY_HISTORY_SELECT)
    @ResponseBody
    public ApiResult<List<EntryHistoryResponse>>
        selectList(@RequestBody @Valid EntryHistoryRequest req, BindingResult result) {

        // レスポンスデータ
        ApiResult<List<EntryHistoryResponse>> apiResult = new ApiResult<>();

        if (result.hasErrors()) {

            // バリデーションチェック結果がNGの場合はエラーを返却
            apiResult = new ApiResult<>();
            apiResult.setResultCode(ResultCode.VALIDATION_ERROR);
            List<ErrorInfo> errList = ValidationUtils.getErrorList(result);
            apiResult.setErrorList(errList);

            // エラー内容をログに出力
            LOGGER.info(LogId.I_CMN_VALID_ERR, ValidationUtils.getMessage(errList));

        } else {

            // 取得処理を実施する
            apiResult = doSelectList(req);
        }

        return apiResult;
    }

    /**
     * 入退室履歴情報一覧取得処理を実施する。
     * @param req リクエストデータ。
     */
    private ApiResult<List<EntryHistoryResponse>> doSelectList(EntryHistoryRequest req) {

        // 検索条件を設定
        EntryHistoryDetailInfoParamDTO condition = new EntryHistoryDetailInfoParamDTO();
        condition.setBuildingId(toParam(req.getBuildingId()));
        condition.setRoomId(toParam(req.getRoomId()));
        condition.setIoType(toParam(req.getIoType()));
        condition.setCertifyDateFrom(toDateParam(req.getCertifyDateFrom()));
        condition.setCertifyDateTo(toDateParam(req.getCertifyDateTo()));
        condition.setSectionId(toParam(req.getSectionId()));
        condition.setPersonId(toParam(req.getPersonId()));
        condition.setPersonName(toParam(req.getPersonName()));

        // 検索を実施
        List<EntryHistoryDetailInfoDTO> dtoList = dao.selectDetail(condition);

        // レスポンスデータに変換
        List<EntryHistoryResponse> retList = dtoList.stream().map(dto -> {

            String path = createHistoryUrl(dto);

            EntryHistoryResponse res = new EntryHistoryResponse();
            res.setRoomId(dto.getRoomId());
            res.setBuildingName(dto.getBuildingName());
            res.setRoomName(dto.getRoomName());
            res.setIoType(dto.getIoType());
            res.setCertifyDate(DateUtils.format(dto.getCertifyDate(), DateFormat.YMDHMS_SC));
            res.setCertifyResult(dto.getCertifyResult());
            res.setSectionName(dto.getSectionName());
            res.setPersonId(dto.getPersonId());
            res.setPersonName(dto.getPersonName());
            res.setCertifyImageFilePath(path);
            return res;
        })
        .collect(Collectors.toList());

        // 正常終了として返す
        ApiResult<List<EntryHistoryResponse>> ret = new ApiResult<>();
        ret.setResultCode(ResultCode.SUCCESS);
        ret.setResultInfo(retList);

        return ret;
    }

    /**
     * 検索条件として適切な形に変換する。
     * @param val 検索値。
     * @return 検索条件の値。
     */
    private String toParam(String val) {
        return (StringUtils.isEmpty(val)) ? null : val;
    }

    /**
     * 検索条件として適切な日時型に変換する。
     * @param val 検索値。
     * @return 検索条件の値。
     */
    private Date toDateParam(String val) {
        if (StringUtils.isEmpty(val)) {
            return null;
        } else {
            return DateUtils.parse(val, DateFormat.YMDHM_SC);
        }
    }

    /**
     * 履歴レコードに対する履歴画像ファイルのURLを生成する。
     * @param dto 履歴レコード。
     * @return 履歴画像ファイルのURL。
     */
    private String createHistoryUrl(EntryHistoryDetailInfoDTO dto) {
        String ymd = DateUtils.format(dto.getCertifyDate(), DateFormat.YMD_S);
        return historyUrl + ymd + "/" + dto.getImageFileName();
    }

}

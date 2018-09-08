package jp.co.humane.rio.api.person.update;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

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
import jp.co.humane.rio.common.consts.LogId;
import jp.co.humane.rio.common.consts.ResultCode;
import jp.co.humane.rio.common.dto.ApiResult;
import jp.co.humane.rio.common.dto.ErrorInfo;
import jp.co.humane.rio.common.exception.ApplicationException;
import jp.co.humane.rio.common.utils.ValidationUtils;
import jp.co.humane.rio.orm.dao.PersonMasterDAO;
import jp.co.humane.rio.orm.dto.PersonMasterDTO;

/**
 * 個人情報更新IFコントローラ。
 * @author terada
 *
 */
@RestController
public class PersonUpdateController {

    /** ロガー */
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonUpdateController.class);

    /** 認証用画像格納パス */
    @Value("${rio.auth.img-path}")
    private String imgPath = null;

    /** 認証用画像更新ファイルのパス */
    @Value("${rio.auth.update-file-path}")
    private String updateFilePath = null;

    /** 個人マスタDAO */
    @Autowired
    private PersonMasterDAO dao = null;

    /**
     * 個人情報更新IFコントローラ処理。
     * @param req    リクエストデータ。
     * @param result バリデーションチェック結果。
     * @return レスポンスデータ。
     */
    @PostMapping(URL.PERSON_UPDATE)
    @ResponseBody
    public ApiResult<Boolean>
        selectList(@ModelAttribute @Valid PersonUpdateRequest req, BindingResult result) {

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

            // 更新処理を実施する
            apiResult = doUpdate(req);
        }

        return apiResult;
    }

    /**
     * 個人情報更新処理を実施する。
     * @param req リクエストデータ。
     */
    private ApiResult<Boolean> doUpdate(PersonUpdateRequest req) {

        ApiResult<Boolean> result = new ApiResult<>();
        result.setResultCode(ResultCode.SUCCESS);

        // 指定された値で名前を更新
        String id = req.getPersonId();
        String name = req.getPersonName();
        boolean isSuccess = updatePersonName(id, name);
        if (!isSuccess) {
            result.setResultInfo(Boolean.FALSE);
            return result;
        }

        // この機能で初回登録もできるようにフォルダがなければ作成する
        makeDir(id);

        // 更新対象のファイルを取得
        MultipartFile frontFile = req.getFrontImageFile();
        MultipartFile leftFile = req.getLeftImageFile();
        MultipartFile rightFile = req.getRightImageFile();
        Map<Path, MultipartFile> fileMap = new HashMap<>();
        if (frontFile != null && !frontFile.isEmpty()) {
            fileMap.put(Paths.get(imgPath, id, URL.FRONT_IMG), frontFile);
        }
        if (leftFile != null && !leftFile.isEmpty()) {
            fileMap.put(Paths.get(imgPath, id, URL.LEFT_IMG), leftFile);
        }
        if (rightFile != null && !rightFile.isEmpty()) {
            fileMap.put(Paths.get(imgPath, id, URL.RIGHT_IMG), rightFile);
        }

        // ファイルの更新がない場合はtrueを返す
        if (fileMap.isEmpty()) {
            result.setResultInfo(Boolean.TRUE);
            return result;
        }

        // 更新前のファイルは削除
        deleteAuthImg(fileMap);

        // アップロードされたファイルを配置
        saveAuthImg(fileMap);

        // 更新用ファイルを作成する
        createUpdateFile();

        result.setResultInfo(Boolean.TRUE);
        return result;
    }

    /**
     * 指定の個人IDを持つ個人情報レコードの氏名を更新する。
     *
     * @param id   個人ID。
     * @param name 氏名。
     * @return 正常終了時はtrue。
     */
    private boolean updatePersonName(String id, String name) {

        // 該当の個人IDを持つユーザがいない場合は失敗
        PersonMasterDTO condition = new PersonMasterDTO();
        condition.setPersonId(id);
        PersonMasterDTO person = dao.selectOneAvailable(condition);
        if (null == person) {
            LOGGER.info(LogId.I_PERSON_UPD_PERSON_NOT_FOUND, id);
            return false;
        }

        // ユーザがいる場合は氏名を更新
        person.setPersonName(name);
        dao.update(person);

        return true;
    }

    /**
     * 指定の個人IDの認証用画像ファイルの配置ディレクトリがなければ作成する。
     * @param id 個人ID。
     */
    private void makeDir(String id) {
        File idDir = Paths.get(imgPath, id).toFile();
        if (!idDir.exists()) {
            idDir.mkdir();
        }
    }

    /**
     * 指定の個人IDに対する認証用画像ファイルを削除する。
     * @param map 更新対象ファイルマップ。
     */
    private void deleteAuthImg(Map<Path, MultipartFile> fileMap) {

        fileMap.keySet()
               .stream()
               .filter(path -> {
                   return path.toFile().exists();
               })
               .forEach(path -> {
                   try {
                       Files.delete(path);
                   } catch (IOException ex) {
                       LOGGER.error(LogId.E_PERSON_UPD_FILE_DEL_ERR, path.toAbsolutePath().toString());
                       throw new ApplicationException(ex);
                   }
               });
    }

    /**
     * 認証用画像ファイルを保存する。
     *
     * @param fileMap 更新対象ファイルマップ。
     */
    private void saveAuthImg(Map<Path, MultipartFile> fileMap) {

        fileMap.entrySet().stream()
            .forEach(entry -> {
                Path path = entry.getKey();
                MultipartFile file = entry.getValue();
                try {
                    Files.write(path, file.getBytes());
                } catch (IOException ex) {
                    LOGGER.error(LogId.E_PERSON_UPD_IMG_FILE_SAVE_ERR, path.toAbsolutePath().toString());
                    throw new ApplicationException(ex);
                }
            });
    }

    /**
     * 更新用ファイルを作成する。
     */
    private void createUpdateFile() {
        File file = Paths.get(updateFilePath).toFile();
        if (file.exists()) {
            return;
        }
        newFile(file);
    }

    /**
     * 同期処理でファイルの作成を行う。
     * @param file 作成するファイル。
     */
    private synchronized void newFile(File file) {
        if (file.exists()) {
            return;
        }
        try {
            new FileOutputStream(file).close();
        } catch (IOException ex) {
            LOGGER.error(LogId.E_PERSON_UPD_UPD_FILE_MAKE_ERR, file.toString());
            throw new ApplicationException(ex);
        }
    }

}

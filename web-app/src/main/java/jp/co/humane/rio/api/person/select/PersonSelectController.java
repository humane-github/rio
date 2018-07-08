package jp.co.humane.rio.api.person.select;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jp.co.humane.rio.api.consts.URL;
import jp.co.humane.rio.common.consts.LogId;
import jp.co.humane.rio.common.consts.ResultCode;
import jp.co.humane.rio.common.dto.ApiResult;
import jp.co.humane.rio.common.dto.ErrorInfo;
import jp.co.humane.rio.common.utils.ValidationUtils;
import jp.co.humane.rio.orm.dao.PersonMasterDAO;
import jp.co.humane.rio.orm.dto.PersonMasterDTO;

/**
 * 個人情報取得IFコントローラ。
 * @author terada
 *
 */
@RestController
public class PersonSelectController {

    /** ロガー */
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonSelectController.class);

    /** 個人マスタDAO */
    @Autowired
    private PersonMasterDAO personMaster = null;

    /**
     * 個人情報取得IFコントローラ処理。
     * @param req    リクエストデータ。
     * @param result バリデーションチェック結果。
     * @return レスポンスデータ。
     */
    @PostMapping(URL.PERSON_SELECT)
    @ResponseBody
    public ApiResult<PersonSelectResponse>
        selectList(@RequestBody @Valid PersonSelectRequest req, BindingResult result) {

        // レスポンスデータ
        ApiResult<PersonSelectResponse> apiResult = new ApiResult<>();

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
     * 個人情報一覧取得処理を実施する。
     * @param req リクエストデータ。
     */
    private ApiResult<PersonSelectResponse> doSelectList(PersonSelectRequest req) {

        PersonSelectResponse res = null;

        // 個人IDで個人情報を検索
        PersonMasterDTO condition = new PersonMasterDTO();
        condition.setPersonId(req.getPersonId());
        PersonMasterDTO dto = personMaster.selectOneAvailable(condition);

        // 検索できた場合はファイルパスを設定
        if (null != dto) {
            res = new PersonSelectResponse();
            String id = dto.getPersonId();
            res.setPersonId(id);
            res.setPersonName(dto.getPersonName());
            res.setFrontImageFilePath(URL.PERSON_IMG_PATH + id + "/" + URL.FRONT_IMG);
            res.setLeftImageFilePath(URL.PERSON_IMG_PATH + id + "/" + URL.LEFT_IMG);
            res.setRightImageFilePath(URL.PERSON_IMG_PATH + id + "/" + URL.RIGHT_IMG);
        }

        ApiResult<PersonSelectResponse> ret = new ApiResult<>();
        ret.setResultCode(ResultCode.SUCCESS);
        ret.setResultInfo(res);

        return ret;
    }
}

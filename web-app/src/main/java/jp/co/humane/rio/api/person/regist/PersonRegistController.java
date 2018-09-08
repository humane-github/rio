package jp.co.humane.rio.api.person.regist;

import java.text.MessageFormat;
import java.util.Collections;
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
import jp.co.humane.rio.common.consts.AuthenticateId;
import jp.co.humane.rio.common.consts.DeleteFlag;
import jp.co.humane.rio.common.consts.LogId;
import jp.co.humane.rio.common.consts.ResultCode;
import jp.co.humane.rio.common.dto.ApiResult;
import jp.co.humane.rio.common.dto.ErrorInfo;
import jp.co.humane.rio.common.utils.ValidationUtils;
import jp.co.humane.rio.orm.dao.PersonMasterDAO;
import jp.co.humane.rio.orm.dto.PersonMasterDTO;

/**
 * 個人情報登録IFコントローラ。
 * @author terada
 *
 */
@RestController
public class PersonRegistController {

    /** ロガー */
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonRegistController.class);

    /** 重複時のメッセージ */
    private static final MessageFormat message = new MessageFormat("個人Id [{0}] は既に使用されています。");

    /** 個人マスタDAO */
    @Autowired
    private PersonMasterDAO personMaster = null;

    /**
     * 個人情報取得IFコントローラ処理。
     * @param req    リクエストデータ。
     * @param result バリデーションチェック結果。
     * @return レスポンスデータ。
     */
    @PostMapping(URL.PERSON_REGIST)
    @ResponseBody
    public ApiResult<Boolean>
        regist(@RequestBody @Valid PersonRegistRequest req, BindingResult result) {

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

            // 取得処理を実施する
            apiResult = doRegist(req);
        }

        return apiResult;
    }

    /**
     * 個人情報登録を実施する。
     * @param req リクエストデータ。
     */
    private ApiResult<Boolean> doRegist(PersonRegistRequest req) {

        ApiResult<Boolean> ret = new ApiResult<>();

        // 既に同じ個人IDが使用されている場合はfalseを返す
        if (existPersonId(req.getPersonId())) {
            ErrorInfo errInfo = new ErrorInfo();
            errInfo.setKeyName("personId");
            errInfo.setMessage(message.format(new Object[]{req.getPersonId()}));
            ret.setResultInfo(false);
            ret.setErrorList(Collections.singletonList(errInfo));
            return ret;
        }

        // ALLの権限で個人情報を登録
        PersonMasterDTO dto = new PersonMasterDTO();
        dto.setPersonId(req.getPersonId());
        dto.setPersonName(req.getPersonName());
        dto.setAuthId(AuthenticateId.ALL);
        dto.setSectionId(req.getSectionId());
        dto.setDeleteFlg(DeleteFlag.OFF);
        personMaster.insert(dto);

        // 登録成功を返却
        ret.setResultCode(ResultCode.SUCCESS);
        return ret;
    }

    /**
     * 個人IDの存在有無を返す。
     * @param personId 個人ID。
     * @return 存在している場合はtrue、存在していない場合はfalse。
     */
    private boolean existPersonId(String personId) {
        PersonMasterDTO dto = new PersonMasterDTO();
        dto.setPersonId(personId);
        PersonMasterDTO person = personMaster.selectOne(dto);
        return (person != null);
    }
}

package jp.co.humane.rio.api.entrance.person;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
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
import jp.co.humane.rio.orm.dao.ViewPresenceDAO;
import jp.co.humane.rio.orm.dto.PresenceDTO;

/**
 * 入室者情報一覧取得IFコントローラ。
 * @author terada
 *
 */
@RestController
public class EntrancePersonController {

    /** ロガー */
    private static final Logger LOGGER = LoggerFactory.getLogger(EntrancePersonController.class);

    /** 在室状況ビューDAO */
    @Autowired
    private ViewPresenceDAO dao = null;

    /**
     * 入室者情報一覧取得IFコントローラ処理。
     * @param req    リクエストデータ。
     * @param result バリデーションチェック結果。
     * @return レスポンスデータ。
     */
    @PostMapping(URL.ENTRANCE_PERSON_SELECT)
    @ResponseBody
    public ApiResult<List<EntrancePersonResponse>>
        selectList(@RequestBody @Valid EntrancePersonRequest req, BindingResult result) {

        // レスポンスデータ
        ApiResult<List<EntrancePersonResponse>> apiResult = new ApiResult<>();

        if (result.hasErrors()) {

            // バリデーションチェック結果がNGの場合はエラーを返却
            apiResult = new ApiResult<>();
            apiResult.setResultCode(ResultCode.VALIDATION_ERROR);
            List<ErrorInfo> errList = ValidationUtils.getErrorList(result);
            apiResult.setErrorList(errList);

            // エラー内容をログに出力
            LOGGER.info(LogId.I_CMN_VALID_ERR, ValidationUtils.getMessage(errList));

        } else {

            // 検索処理を実施する
            apiResult = doSelectList(req);
        }

        return apiResult;
    }

    /**
     * 入室者情報一覧取得処理を実施する。
     * @param req リクエストデータ。
     */
    private ApiResult<List<EntrancePersonResponse>> doSelectList(EntrancePersonRequest req) {

        // 在室状況を取得する
        PresenceDTO condition = new PresenceDTO();
        String buildingId = (StringUtils.equals(req.getBuildingId(), "")) ? null : req.getBuildingId();
        String roomId = (StringUtils.equals(req.getRoomId(), "")) ? null : req.getRoomId();
        condition.setBuildingId(buildingId);
        condition.setRoomId(roomId);
        List<PresenceDTO> dtoList = dao.selectPresence(condition);

        // レスポンスデータに変換する
        List<EntrancePersonResponse> resList = dtoList.stream().map(dto -> {
            EntrancePersonResponse res = new EntrancePersonResponse();
            res.setBuildingId(dto.getBuildingId());
            res.setRoomId(dto.getRoomId());
            res.setBuildingName(dto.getBuildingName());
            res.setRoomName(dto.getRoomName());
            res.setEntrancePersonNum(String.valueOf(dto.getEntrancePersonNum()));
            return res;
        })
        .collect(Collectors.toList());

        // 正常終了データとして返却
        ApiResult<List<EntrancePersonResponse>> ret = new ApiResult<>();
        ret.setResultCode(ResultCode.SUCCESS);
        ret.setResultInfo(resList);

        return ret;
    }

}

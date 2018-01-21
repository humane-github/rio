package jp.co.humane.rio.api.room;

import java.util.List;
import java.util.stream.Collectors;

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
import jp.co.humane.rio.orm.dao.RoomMasterDAO;
import jp.co.humane.rio.orm.dto.RoomMasterDTO;

/**
 * 部屋情報一覧取得IFコントローラ。
 * @author terada
 *
 */
@RestController
public class RoomController {

    /** ロガー */
    private static final Logger LOGGER = LoggerFactory.getLogger(RoomController.class);

    /** 部屋マスタDAO */
    @Autowired
    private RoomMasterDAO dao = null;

    /**
     * 部屋情報一覧取得IFコントローラ処理。
     * @param req    リクエストデータ。
     * @param result バリデーションチェック結果。
     * @return レスポンスデータ。
     */
    @PostMapping(URL.ROOM_SELECT)
    @ResponseBody
    public ApiResult<List<RoomResponse>>
        selectList(@RequestBody @Valid RoomRequest req, BindingResult result) {

        // レスポンスデータ
        ApiResult<List<RoomResponse>> apiResult = new ApiResult<>();

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
     * 部屋情報一覧取得処理を実施する。
     * @param req リクエストデータ。
     */
    private ApiResult<List<RoomResponse>> doSelectList(RoomRequest req) {

        // 建物IDで部屋情報を検索
        List<RoomMasterDTO> dtoList = dao.selectByBuildingId(req.getBuildingId());

        // 部屋情報を返却データに変換
        List<RoomResponse> resList = dtoList.stream().map(dto -> {
            RoomResponse roomRes = new RoomResponse();
            roomRes.setRoomId(dto.getRoomId());
            roomRes.setRoomName(dto.getRoomName());
            return roomRes;
        })
        .collect(Collectors.toList());

        // 応答情報に格納して返す
        ApiResult<List<RoomResponse>> ret = new ApiResult<>();
        ret.setResultCode(ResultCode.SUCCESS);
        ret.setResultInfo(resList);

        return ret;
    }
}

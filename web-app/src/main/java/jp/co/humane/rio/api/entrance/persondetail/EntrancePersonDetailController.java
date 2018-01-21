package jp.co.humane.rio.api.entrance.persondetail;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jp.co.humane.rio.api.consts.URL;
import jp.co.humane.rio.common.consts.DateFormat;
import jp.co.humane.rio.common.consts.IOType;
import jp.co.humane.rio.common.consts.LogId;
import jp.co.humane.rio.common.consts.ResultCode;
import jp.co.humane.rio.common.dto.ApiResult;
import jp.co.humane.rio.common.dto.ErrorInfo;
import jp.co.humane.rio.common.utils.ValidationUtils;
import jp.co.humane.rio.orm.dao.ViewPresenceDAO;
import jp.co.humane.rio.orm.dto.ViewPresenceDTO;

/**
 * 入室者詳細情報一覧取得IFコントローラ。
 * @author terada
 *
 */
@RestController
public class EntrancePersonDetailController {

    /** ロガー */
    private static final Logger LOGGER = LoggerFactory.getLogger(EntrancePersonDetailController.class);

    /** 在室状況ビューDTO */
    @Autowired
    private ViewPresenceDAO dao = null;

    /**
     * 入室者詳細情報一覧取得IFコントローラ処理。
     * @param req    リクエストデータ。
     * @param result バリデーションチェック結果。
     * @return レスポンスデータ。
     */
    @PostMapping(URL.ENTRANCE_PERSON_DETAIL_SELECT)
    @ResponseBody
    public ApiResult<List<EntrancePersonDetailResponse>>
        selectList(@RequestBody @Valid EntrancePersonDetailRequest req, BindingResult result) {

        // レスポンスデータ
        ApiResult<List<EntrancePersonDetailResponse>> apiResult = new ApiResult<>();

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
     * 入室者詳細情報一覧取得処理を実施する。
     * @param req リクエストデータ。
     */
    private ApiResult<List<EntrancePersonDetailResponse>> doSelectList(EntrancePersonDetailRequest req) {

        // リクエストデータを検索条件に変換
        ViewPresenceDTO condition = new ViewPresenceDTO();
        condition.setBuildingId(toParam(req.getBuildingId()));
        condition.setRoomId(toParam(req.getRoomId()));
        condition.setPersonId(toParam(req.getPersonId()));
        condition.setPersonName(toParam(req.getPersonName()));

        // 入退室状況を取得
        List<ViewPresenceDTO> dtoList = dao.selectDetail(condition);

        // 取得結果をレスポンスデータに変換
        List<EntrancePersonDetailResponse> resList = dtoList.stream().map(dto -> {
            EntrancePersonDetailResponse res = new EntrancePersonDetailResponse();
            Date outDate = dto.getCertifyDateOut();
            String ioType = (null == dto.getCertifyDateOut()) ? IOType.IN : IOType.OUT;
            String entranceLastDate = DateFormatUtils.format(dto.getCertifyDateIn(), DateFormat.YMDHMS_SC);
            String exitLastDate = (null == outDate) ? "" : DateFormatUtils.format(outDate, DateFormat.YMDHMS_SC);
            res.setSectionName(dto.getSectionName());
            res.setPersonId(dto.getPersonId());
            res.setPersonName(dto.getPersonName());
            res.setIoType(ioType);
            res.setEntranceLastDate(entranceLastDate);
            res.setExitLastDate(exitLastDate);
            return res;
        })
        .collect(Collectors.toList());

        // 正常終了のデータとして返却する
        ApiResult<List<EntrancePersonDetailResponse>> ret = new ApiResult<>();
        ret.setResultCode(ResultCode.SUCCESS);
        ret.setResultInfo(resList);

        return ret;
    }

    /**
     * パレメータ文字列をSQLの条件に合うように変換する。
     * @param str 文字列。
     * @return 返還後の文字列。
     */
    private String toParam(String str) {
        return StringUtils.isEmpty(str) ? null : str;
    }



}

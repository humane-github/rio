package jp.co.humane.rio.api.building;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jp.co.humane.rio.api.consts.URL;
import jp.co.humane.rio.common.consts.ResultCode;
import jp.co.humane.rio.common.dto.ApiResult;
import jp.co.humane.rio.orm.dao.BuildingMasterDAO;
import jp.co.humane.rio.orm.dto.BuildingMasterDTO;

/**
 * 建物情報一覧取得IFコントローラ。
 * @author terada
 *
 */
@RestController
public class BuildingController {

    /** ロガー */
    private static final Logger LOGGER = LoggerFactory.getLogger(BuildingController.class);

    /** 建物マスタDAO */
    @Autowired
    private BuildingMasterDAO dao = null;

    /**
     * 建物情報一覧取得IFコントローラ処理。
     * @param req    リクエストデータ。
     * @param result バリデーションチェック結果。
     * @return レスポンスデータ。
     */
    @PostMapping(URL.BUILDING_SELECT)
    @ResponseBody
    public ApiResult<List<BuildingResponse>> selectList() {

        // レスポンスデータ
        ApiResult<List<BuildingResponse>> apiResult = new ApiResult<>();

        // 検索処理を実施する
        apiResult = doSelectList();

        return apiResult;
    }

    /**
     * 建物情報一覧取得処理を実施する。
     * @param req リクエストデータ。
     */
    private ApiResult<List<BuildingResponse>> doSelectList() {

        // 建物一覧を取得する
        List<BuildingMasterDTO> buildings = dao.selectAllAvailable();

        // 返却用データに変換する
        List<BuildingResponse> resList = buildings.stream().map(dto -> {
            BuildingResponse res = new BuildingResponse();
            res.setBuildingId(dto.getBuildingId());
            res.setBuildingName(dto.getBuildingName());
            return res;
        })
        .collect(Collectors.toList());

        // 返却データを返す
        ApiResult<List<BuildingResponse>> ret = new ApiResult<>();
        ret.setResultCode(ResultCode.SUCCESS);
        ret.setResultInfo(resList);
        return ret;
    }
}

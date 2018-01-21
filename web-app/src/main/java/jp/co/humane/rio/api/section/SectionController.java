package jp.co.humane.rio.api.section;

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
import jp.co.humane.rio.orm.dao.SectionMasterDAO;
import jp.co.humane.rio.orm.dto.SectionMasterDTO;

/**
 * 部署情報一覧取得IFコントローラ。
 * @author terada
 *
 */
@RestController
public class SectionController {

    /** ロガー */
    private static final Logger LOGGER = LoggerFactory.getLogger(SectionController.class);

    /** 部署マスタDAO */
    @Autowired
    private SectionMasterDAO sectionMaster = null;

    /**
     * 部署情報一覧取得IFコントローラ処理。
     * @param req    リクエストデータ。
     * @param result バリデーションチェック結果。
     * @return レスポンスデータ。
     */
    @PostMapping(URL.SECTION_SELECT)
    @ResponseBody
    public ApiResult<List<SectionResponse>> selectList() {

        // レスポンスデータ
        ApiResult<List<SectionResponse>> apiResult = new ApiResult<>();

        // 取得処理を実施する
        apiResult = doSelectList();

        return apiResult;
    }

    /**
     * 部署情報一覧取得処理を実施する。
     * @param req リクエストデータ。
     */
    private ApiResult<List<SectionResponse>> doSelectList() {

        List<SectionMasterDTO> sectionList = sectionMaster.selectAllAvailable();
        List<SectionResponse> resultList = sectionList.stream()
                .map(dto -> {
                    SectionResponse response = new SectionResponse();
                    response.setSectionId(dto.getSectionId());
                    response.setSectionName(dto.getSectionName());
                    return response;
                })
                .collect(Collectors.toList());

        ApiResult<List<SectionResponse>> ret = new ApiResult<>();
        ret.setResultCode(ResultCode.SUCCESS);
        ret.setResultInfo(resultList);

        return ret;
    }
}

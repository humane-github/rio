package jp.co.humane.rio.api.building;

import javax.validation.constraints.NotNull;

import jp.co.humane.rio.common.dto.BaseDTO;

/**
 * 建物情報一覧取得IFレスポンスDTO。
 * @author terada
 *
 */
public class BuildingResponse extends BaseDTO {

    /** 建物ID */
    @NotNull
    private String buildingId = null;

    /** 建物名 */
    @NotNull
    private String buildingName = null;

    /**
     * デフォルトコンストラクタ。
     */
    public BuildingResponse() {
        super();
    }

    /**
     * buildingIdを取得する。
     * @return buildingId buildingId。
     */
    public String getBuildingId() {
        return buildingId;
    }

    /**
     * buildingIdを設定する。
     * @param buildingId buildingId。
     */
    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    /**
     * buildingNameを取得する。
     * @return buildingName buildingName。
     */
    public String getBuildingName() {
        return buildingName;
    }

    /**
     * buildingNameを設定する。
     * @param buildingName buildingName。
     */
    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }
}

package jp.co.humane.rio.orm.dto;

import jp.co.humane.rio.common.dto.BaseDTO;

/**
 * 建物マスタDTO
 */
public class BuildingMasterDTO extends BaseDTO {

    /** 建物ID */
    private String buildingId = null;

    /** 建物名 */
    private String buildingName = null;

    /** 削除フラグ */
    private String deleteFlg = null;

    /**
     * デフォルトコンストラクタ
     */
    public BuildingMasterDTO() {
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

    /**
     * deleteFlgを取得する。
     * @return deleteFlg deleteFlg。
     */
    public String getDeleteFlg() {
        return deleteFlg;
    }

    /**
     * deleteFlgを設定する。
     * @param deleteFlg deleteFlg。
     */
    public void setDeleteFlg(String deleteFlg) {
        this.deleteFlg = deleteFlg;
    }
}
package jp.co.humane.rio.orm.dto;

import jp.co.humane.rio.common.dto.BaseDTO;

/**
 * 部署マスタDTO
 */
public class SectionMasterDTO extends BaseDTO {

    /** 部署ID */
    private String sectionId = null;

    /** 部署名 */
    private String sectionName = null;

    /** 削除フラグ */
    private String deleteFlg = null;

    /**
     * デフォルトコンストラクタ
     */
    public SectionMasterDTO() {
        super();
    }

    /**
     * sectionIdを取得する。
     * @return sectionId sectionId。
     */
    public String getSectionId() {
        return sectionId;
    }

    /**
     * sectionIdを設定する。
     * @param sectionId sectionId。
     */
    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    /**
     * sectionNameを取得する。
     * @return sectionName sectionName。
     */
    public String getSectionName() {
        return sectionName;
    }

    /**
     * sectionNameを設定する。
     * @param sectionName sectionName。
     */
    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
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
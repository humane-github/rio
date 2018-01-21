package jp.co.humane.rio.orm.dto;

import jp.co.humane.rio.common.dto.BaseDTO;

/**
 * 個人マスタDTO
 */
public class PersonMasterDTO extends BaseDTO {

    /** 個人ID */
    private String personId = null;

    /** 氏名 */
    private String personName = null;

    /** 権限ID */
    private String authId = null;

    /** 部署ID */
    private String sectionId = null;

    /** 最終認証カメラID */
    private String lastestCertifyCameraId = null;

    /** 削除フラグ */
    private String deleteFlg = null;

    /**
     * デフォルトコンストラクタ
     */
    public PersonMasterDTO() {
        super();
    }

    /**
     * personIdを取得する。
     * @return personId personId。
     */
    public String getPersonId() {
        return personId;
    }

    /**
     * personIdを設定する。
     * @param personId personId。
     */
    public void setPersonId(String personId) {
        this.personId = personId;
    }

    /**
     * personNameを取得する。
     * @return personName personName。
     */
    public String getPersonName() {
        return personName;
    }

    /**
     * personNameを設定する。
     * @param personName personName。
     */
    public void setPersonName(String personName) {
        this.personName = personName;
    }

    /**
     * authIdを取得する。
     * @return authId authId。
     */
    public String getAuthId() {
        return authId;
    }

    /**
     * authIdを設定する。
     * @param authId authId。
     */
    public void setAuthId(String authId) {
        this.authId = authId;
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
     * lastestCertifyCameraIdを取得する。
     * @return lastestCertifyCameraId lastestCertifyCameraId。
     */
    public String getLastestCertifyCameraId() {
        return lastestCertifyCameraId;
    }

    /**
     * lastestCertifyCameraIdを設定する。
     * @param lastestCertifyCameraId lastestCertifyCameraId。
     */
    public void setLastestCertifyCameraId(String lastestCertifyCameraId) {
        this.lastestCertifyCameraId = lastestCertifyCameraId;
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
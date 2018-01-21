package jp.co.humane.rio.orm.dto;

import java.util.Date;

import jp.co.humane.rio.common.dto.BaseDTO;

/**
 * 入退出履歴情報DTO
 */
public class EntryHistoryInfoDTO extends BaseDTO {

    /** 個人ID */
    private String personId = null;

    /** 部署ID */
    private String sectionId = null;

    /** 認証結果 */
    private Boolean certifyResult = null;

    /** カメラID */
    private String cameraId = null;

    /** 認証日時 */
    private Date certifyDate = null;

    /** 画像ファイル名 */
    private String imageFileName = null;

    /**
     * デフォルトコンストラクタ
     */
    public EntryHistoryInfoDTO() {
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
     * certifyResultを取得する。
     * @return certifyResult certifyResult。
     */
    public Boolean getCertifyResult() {
        return certifyResult;
    }

    /**
     * certifyResultを設定する。
     * @param certifyResult certifyResult。
     */
    public void setCertifyResult(Boolean certifyResult) {
        this.certifyResult = certifyResult;
    }

    /**
     * cameraIdを取得する。
     * @return cameraId cameraId。
     */
    public String getCameraId() {
        return cameraId;
    }

    /**
     * cameraIdを設定する。
     * @param cameraId cameraId。
     */
    public void setCameraId(String cameraId) {
        this.cameraId = cameraId;
    }

    /**
     * certifyDateを取得する。
     * @return certifyDate certifyDate。
     */
    public Date getCertifyDate() {
        return certifyDate;
    }

    /**
     * certifyDateを設定する。
     * @param certifyDate certifyDate。
     */
    public void setCertifyDate(Date certifyDate) {
        this.certifyDate = certifyDate;
    }

    /**
     * imageFileNameを取得する。
     * @return imageFileName imageFileName。
     */
    public String getImageFileName() {
        return imageFileName;
    }

    /**
     * imageFileNameを設定する。
     * @param imageFileName imageFileName。
     */
    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }
}
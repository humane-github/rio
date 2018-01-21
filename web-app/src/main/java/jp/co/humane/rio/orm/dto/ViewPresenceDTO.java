package jp.co.humane.rio.orm.dto;

import java.util.Date;

import jp.co.humane.rio.common.dto.BaseDTO;

/**
 * 在室状況ビューDTO
 */
public class ViewPresenceDTO extends BaseDTO {

    /** 建物ID */
    private String buildingId = null;

    /** 建物名 */
    private String buildingName = null;

    /** 部屋ID */
    private String roomId = null;

    /** 部屋名 */
    private String roomName = null;

    /** 個人ID */
    private String personId = null;

    /** 個人名 */
    private String personName = null;

    /** 部署ID */
    private String sectionId = null;

    /** 部署名 */
    private String sectionName = null;

    /** 入室日時 */
    private Date certifyDateIn = null;

    /** 退室時刻 */
    private Date certifyDateOut = null;

    /**
     * デフォルトコンストラクタ
     */
    public ViewPresenceDTO() {
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
     * roomIdを取得する。
     * @return roomId roomId。
     */
    public String getRoomId() {
        return roomId;
    }

    /**
     * roomIdを設定する。
     * @param roomId roomId。
     */
    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    /**
     * roomNameを取得する。
     * @return roomName roomName。
     */
    public String getRoomName() {
        return roomName;
    }

    /**
     * roomNameを設定する。
     * @param roomName roomName。
     */
    public void setRoomName(String roomName) {
        this.roomName = roomName;
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
     * certifyDateInを取得する。
     * @return certifyDateIn certifyDateIn。
     */
    public Date getCertifyDateIn() {
        return certifyDateIn;
    }

    /**
     * certifyDateInを設定する。
     * @param certifyDateIn certifyDateIn。
     */
    public void setCertifyDateIn(Date certifyDateIn) {
        this.certifyDateIn = certifyDateIn;
    }

    /**
     * certifyDateOutを取得する。
     * @return certifyDateOut certifyDateOut。
     */
    public Date getCertifyDateOut() {
        return certifyDateOut;
    }

    /**
     * certifyDateOutを設定する。
     * @param certifyDateOut certifyDateOut。
     */
    public void setCertifyDateOut(Date certifyDateOut) {
        this.certifyDateOut = certifyDateOut;
    }
}
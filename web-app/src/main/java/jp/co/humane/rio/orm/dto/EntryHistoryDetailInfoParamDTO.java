package jp.co.humane.rio.orm.dto;

import java.util.Date;

import jp.co.humane.rio.common.dto.BaseDTO;

/**
 * 入退室履歴詳細情報の検索パラメータDTO。
 * @author terada
 *
 */
public class EntryHistoryDetailInfoParamDTO extends BaseDTO {

    /** 建物ID */
    private String buildingId = null;

    /** 部屋ID */
    private String roomId = null;

    /** IN/OUT種別 */
    private String ioType = null;

    /** 認証日時（From） */
    private Date certifyDateFrom = null;

    /** 認証日時（To） */
    private Date certifyDateTo = null;

    /** 部署ID */
    private String sectionId = null;

    /** 個人ID */
    private String personId = null;

    /** 氏名 */
    private String personName = null;

    /**
     * デフォルトコンストラクタ。
     */
    public EntryHistoryDetailInfoParamDTO() {
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
     * ioTypeを取得する。
     * @return ioType ioType。
     */
    public String getIoType() {
        return ioType;
    }

    /**
     * ioTypeを設定する。
     * @param ioType ioType。
     */
    public void setIoType(String ioType) {
        this.ioType = ioType;
    }

    /**
     * certifyDateFromを取得する。
     * @return certifyDateFrom certifyDateFrom。
     */
    public Date getCertifyDateFrom() {
        return certifyDateFrom;
    }

    /**
     * certifyDateFromを設定する。
     * @param certifyDateFrom certifyDateFrom。
     */
    public void setCertifyDateFrom(Date certifyDateFrom) {
        this.certifyDateFrom = certifyDateFrom;
    }

    /**
     * certifyDateToを取得する。
     * @return certifyDateTo certifyDateTo。
     */
    public Date getCertifyDateTo() {
        return certifyDateTo;
    }

    /**
     * certifyDateToを設定する。
     * @param certifyDateTo certifyDateTo。
     */
    public void setCertifyDateTo(Date certifyDateTo) {
        this.certifyDateTo = certifyDateTo;
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
}

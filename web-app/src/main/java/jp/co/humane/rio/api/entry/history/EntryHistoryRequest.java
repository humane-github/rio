package jp.co.humane.rio.api.entry.history;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import jp.co.humane.rio.common.dto.BaseDTO;
import jp.co.humane.rio.validator.AlphaNumeric;
import jp.co.humane.rio.validator.DateFormat;
import jp.co.humane.rio.validator.Numeric;
import jp.co.humane.rio.validator.Wide;

/**
 * 入退室履歴情報一覧取得IFのリクエストDTO。
 * @author terada
 *
 */
public class EntryHistoryRequest extends BaseDTO {

    /** 建物ID */
    @NotNull
    @Length(max = 5)
    @AlphaNumeric
    private String buildingId = null;

    /** 部屋ID */
    @Length(max = 5)
    @AlphaNumeric
    private String roomId = null;

    /** IN/OUT種別 */
    @Length(max = 1)
    @Numeric
    private String ioType = null;

    /** 認証日時（From） */
    @Length(max = 16)
    @DateFormat(format = "yyyy/MM/dd HH:mm")
    private String certifyDateFrom = null;

    /** 認証日時（To） */
    @Length(max = 16)
    @DateFormat(format = "yyyy/MM/dd HH:mm")
    private String certifyDateTo = null;

    /** 部署ID */
    @Length(max = 5)
    @AlphaNumeric
    private String sectionId = null;

    /** 個人ID */
    @Length(max = 5)
    @AlphaNumeric
    private String personId = null;

    /** 氏名 */
    @Length(max = 20)
    @Wide
    private String personName = null;

    /**
     * デフォルトコンストラクタ。
     */
    public EntryHistoryRequest() {
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
    public String getCertifyDateFrom() {
        return certifyDateFrom;
    }

    /**
     * certifyDateFromを設定する。
     * @param certifyDateFrom certifyDateFrom。
     */
    public void setCertifyDateFrom(String certifyDateFrom) {
        this.certifyDateFrom = certifyDateFrom;
    }

    /**
     * certifyDateToを設定する。
     * @param certifyDateTo certifyDateTo。
     */
    public void setCertifyDateTo(String certifyDateTo) {
        this.certifyDateTo = certifyDateTo;
    }

    /**
     * certifyDateToを取得する。
     * @return certifyDateTo certifyDateTo。
     */
    public String getCertifyDateTo() {
        return certifyDateTo;
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

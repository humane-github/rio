package jp.co.humane.rio.api.entry.history;

import javax.validation.constraints.NotNull;

import jp.co.humane.rio.common.dto.BaseDTO;
import jp.co.humane.rio.validator.DateFormat;

/**
 * 入退室履歴情報一覧取得IFのレスポンスDTO。
 * @author terada
 *
 */
public class EntryHistoryResponse extends BaseDTO {

    /** 部屋ID */
    @NotNull
    private String roomId = null;

    /** 建物名 */
    @NotNull
    private String buildingName = null;

    /** 部屋名 */
    @NotNull
    private String roomName = null;

    /** IN/OUT種別 */
    @NotNull
    private String ioType = null;

    /** 認証日時 */
    @NotNull
    @DateFormat(format = "yyyy/MM/dd hh:mm:ss")
    private String certifyDate = null;

    /** 認証成否 */
    @NotNull
    private Boolean certifyResult = null;

    /** 部署名 */
    private String sectionName = null;

    /** 個人ID */
    private String personId = null;

    /** 氏名 */
    private String personName = null;

    /** 認証画像ファイルパス */
    private String certifyImageFilePath = null;

    /**
     * デフォルトコンストラクタ。
     */
    public EntryHistoryResponse() {
        super();
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
     * certifyDateを取得する。
     * @return certifyDate certifyDate。
     */
    public String getCertifyDate() {
        return certifyDate;
    }

    /**
     * certifyDateを設定する。
     * @param certifyDate certifyDate。
     */
    public void setCertifyDate(String certifyDate) {
        this.certifyDate = certifyDate;
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
     * certifyImageFilePathを取得する。
     * @return certifyImageFilePath certifyImageFilePath。
     */
    public String getCertifyImageFilePath() {
        return certifyImageFilePath;
    }

    /**
     * certifyImageFilePathを設定する。
     * @param certifyImageFilePath certifyImageFilePath。
     */
    public void setCertifyImageFilePath(String certifyImageFilePath) {
        this.certifyImageFilePath = certifyImageFilePath;
    }
}

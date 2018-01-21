package jp.co.humane.rio.api.entrance.persondetail;

import javax.validation.constraints.NotNull;

import jp.co.humane.rio.common.dto.BaseDTO;
import jp.co.humane.rio.validator.DateFormat;

/**
 * 入室者詳細情報一覧取得IFレスポンスDTO。
 * @author terada
 *
 */
public class EntrancePersonDetailResponse extends BaseDTO {

    /** 部署名 */
    @NotNull
    private String sectionName = null;

    /** 個人ID */
    @NotNull
    private String personId = null;

    /** 氏名 */
    @NotNull
    private String personName = null;

    /** IN/OUT種別 */
    @NotNull
    private String ioType = null;

    /** 最終入室日時 */
    @NotNull
    @DateFormat(format = "yyyy/MM/dd hh:mm:ss")
    private String entranceLastDate = null;

    /** 最終退室日時 */
    @DateFormat(format = "yyyy/MM/dd hh:mm:ss")
    private String exitLastDate = null;

    /**
     * デフォルトコンストラクタ。
     */
    public EntrancePersonDetailResponse() {
        super();
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
     * entranceLastDateを取得する。
     * @return entranceLastDate entranceLastDate。
     */
    public String getEntranceLastDate() {
        return entranceLastDate;
    }

    /**
     * entranceLastDateを設定する。
     * @param entranceLastDate entranceLastDate。
     */
    public void setEntranceLastDate(String entranceLastDate) {
        this.entranceLastDate = entranceLastDate;
    }

    /**
     * exitLastDateを取得する。
     * @return exitLastDate exitLastDate。
     */
    public String getExitLastDate() {
        return exitLastDate;
    }

    /**
     * exitLastDateを設定する。
     * @param exitLastDate exitLastDate。
     */
    public void setExitLastDate(String exitLastDate) {
        this.exitLastDate = exitLastDate;
    }

}

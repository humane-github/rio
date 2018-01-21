package jp.co.humane.rio.api.entrance.persondetail;

import org.hibernate.validator.constraints.Length;

import jp.co.humane.rio.common.dto.BaseDTO;
import jp.co.humane.rio.validator.AlphaNumeric;
import jp.co.humane.rio.validator.Wide;

/**
 * 入室者詳細情報一覧取得IFリクエストDTO。
 * @author terada
 *
 */
public class EntrancePersonDetailRequest extends BaseDTO {

    /** 建物ID */
    @Length(max = 5)
    @AlphaNumeric
    private String buildingId = null;

    /** 部屋ID */
    @Length(max = 5)
    @AlphaNumeric
    private String roomId = null;

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
    public EntrancePersonDetailRequest() {
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

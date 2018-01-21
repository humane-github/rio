package jp.co.humane.rio.api.entrance.person;

import org.hibernate.validator.constraints.Length;

import jp.co.humane.rio.common.dto.BaseDTO;
import jp.co.humane.rio.validator.AlphaNumeric;

/**
 * 入室者情報一覧取得IFのリクエストDTO。
 * @author terada
 *
 */
public class EntrancePersonRequest extends BaseDTO {

    /** 建物ID */
    @Length(max = 5)
    @AlphaNumeric
    private String buildingId = null;

    /** 部屋ID */
    @Length(max = 5)
    @AlphaNumeric
    private String roomId = null;

    /**
     * デフォルトコンストラクタ。
     */
    public EntrancePersonRequest() {
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
}

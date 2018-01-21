package jp.co.humane.rio.api.entrance.person;

import javax.validation.constraints.NotNull;

import jp.co.humane.rio.common.dto.BaseDTO;

/**
 * 入室者情報一覧取得IFのレスポンスDTO。
 * @author terada
 *
 */
public class EntrancePersonResponse extends BaseDTO {

    /** 建物ID */
    @NotNull
    private String buildingId = null;

    /** 部屋ID */
    @NotNull
    private String roomId = null;

    /** 建物名 */
    @NotNull
    private String buildingName = null;

    /** 部屋名 */
    @NotNull
    private String roomName = null;

    /** 入室者数 */
    @NotNull
    private String entrancePersonNum = null;

    /**
     * デフォルトコンストラクタ。
     */
    public EntrancePersonResponse() {
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
     * entrancePersonNumを取得する。
     * @return entrancePersonNum entrancePersonNum。
     */
    public String getEntrancePersonNum() {
        return entrancePersonNum;
    }

    /**
     * entrancePersonNumを設定する。
     * @param entrancePersonNum entrancePersonNum。
     */
    public void setEntrancePersonNum(String entrancePersonNum) {
        this.entrancePersonNum = entrancePersonNum;
    }

}

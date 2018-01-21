package jp.co.humane.rio.orm.dto;

import jp.co.humane.rio.common.dto.BaseDTO;

/**
 * 入室者情報DTO。
 * @author terada
 *
 */
public class PresenceDTO extends BaseDTO {

    /** 建物ID */
    private String buildingId = null;

    /** 部屋ID */
    private String roomId = null;

    /** 建物名 */
    private String buildingName = null;

    /** 部屋名 */
    private String roomName = null;

    /** 入室者数 */
    private Integer entrancePersonNum = null;

    /**
     * デフォルトコンストラクタ。
     */
    public PresenceDTO() {
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
    public Integer getEntrancePersonNum() {
        return entrancePersonNum;
    }

    /**
     * entrancePersonNumを設定する。
     * @param entrancePersonNum entrancePersonNum。
     */
    public void setEntrancePersonNum(Integer entrancePersonNum) {
        this.entrancePersonNum = entrancePersonNum;
    }
}

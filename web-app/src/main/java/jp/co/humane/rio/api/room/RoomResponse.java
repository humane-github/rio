package jp.co.humane.rio.api.room;

import javax.validation.constraints.NotNull;

import jp.co.humane.rio.common.dto.BaseDTO;

/**
 * 部屋情報一覧取得IFレスポンスDTO。
 * @author terada
 *
 */
public class RoomResponse extends BaseDTO {

    /** 部屋ID */
    @NotNull
    private String roomId = null;

    /** 部屋名 */
    @NotNull
    private String roomName = null;

    /**
     * デフォルトコンストラクタ。
     */
    public RoomResponse() {
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
}

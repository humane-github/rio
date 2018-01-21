package jp.co.humane.rio.orm.dto;

import jp.co.humane.rio.common.dto.BaseDTO;

/**
 * カメラマスタDTO
 */
public class CameraMasterDTO extends BaseDTO {

    /** カメラID */
    private String cameraId = null;

    /** IN/OUT種別 */
    private String ioType = null;

    /** 部屋ID */
    private String roomId = null;

    /** 削除フラグ */
    private String deleteFlg = null;

    /**
     * デフォルトコンストラクタ
     */
    public CameraMasterDTO() {
        super();
    }

    /**
     * cameraIdを取得する。
     * @return cameraId cameraId。
     */
    public String getCameraId() {
        return cameraId;
    }

    /**
     * cameraIdを設定する。
     * @param cameraId cameraId。
     */
    public void setCameraId(String cameraId) {
        this.cameraId = cameraId;
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
     * deleteFlgを取得する。
     * @return deleteFlg deleteFlg。
     */
    public String getDeleteFlg() {
        return deleteFlg;
    }

    /**
     * deleteFlgを設定する。
     * @param deleteFlg deleteFlg。
     */
    public void setDeleteFlg(String deleteFlg) {
        this.deleteFlg = deleteFlg;
    }
}
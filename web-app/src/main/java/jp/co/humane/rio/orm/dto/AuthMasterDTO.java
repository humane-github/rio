package jp.co.humane.rio.orm.dto;

import jp.co.humane.rio.common.dto.BaseDTO;

/**
 * 権限マスタDTO
 */
public class AuthMasterDTO extends BaseDTO {

    /** 権限ID */
    private String authId = null;

    /** 部屋ID */
    private String roomId = null;

    /** 削除フラグ */
    private String deleteFlg = null;

    /**
     * デフォルトコンストラクタ
     */
    public AuthMasterDTO() {
        super();
    }

    /**
     * authIdを取得する。
     * @return authId authId。
     */
    public String getAuthId() {
        return authId;
    }

    /**
     * authIdを設定する。
     * @param authId authId。
     */
    public void setAuthId(String authId) {
        this.authId = authId;
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
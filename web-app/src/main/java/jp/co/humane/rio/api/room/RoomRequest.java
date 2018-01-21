package jp.co.humane.rio.api.room;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import jp.co.humane.rio.common.dto.BaseDTO;
import jp.co.humane.rio.validator.AlphaNumeric;

/**
 * 部屋情報一覧取得IFリクエストDTO。
 * @author terada
 *
 */
public class RoomRequest extends BaseDTO {

    /** 建物ID */
    @NotNull
    @Length(max = 5)
    @AlphaNumeric
    private String buildingId = null;

    /**
     * デフォルトコンストラクタ。
     */
    public RoomRequest() {
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

}

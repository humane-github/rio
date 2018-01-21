package jp.co.humane.rio.orm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import jp.co.humane.rio.orm.dto.RoomMasterDTO;

/**
 * 部屋マスタDAO
 */
public interface RoomMasterDAO {

    /**
     * 部屋マスタを全レコードを取得する。
     * @return 部屋マスタの全レコード。
     */
    @Select({
        "SELECT",
          "room_id,",
          "building_id,",
          "room_name,",
          "delete_flg",
        "FROM",
          "room_master"
    })
    public List<RoomMasterDTO> selectAll();

    /**
     * 部屋マスタを1レコードを取得する。
     * @param dto 検索条件(PKのフィールドを使用)。
     * @return 部屋マスタの1レコード。存在しない場合はnull。
     */
    @Select({
        "SELECT",
          "room_id,",
          "building_id,",
          "room_name,",
          "delete_flg",
        "FROM",
          "room_master",
        "WHERE",
          "room_id = #{roomId, jdbcType=VARCHAR}",
    })
    public RoomMasterDTO selectOne(RoomMasterDTO dto);

    /**
     * 部屋マスタを登録する。
     * @param 登録レコード情報。
     * @return 登録レコード数。
     */
    @Insert({
        "INSERT INTO room_master",
        "(",
          "room_id,",
          "building_id,",
          "room_name,",
          "delete_flg",
        ")",
        "VALUES",
        "(",
          "#{roomId, jdbcType=VARCHAR},",
          "#{buildingId, jdbcType=VARCHAR},",
          "#{roomName, jdbcType=VARCHAR},",
          "#{deleteFlg, jdbcType=CHAR}",
        ")",
    })
    public int insert(RoomMasterDTO dto);

    /**
     * 部屋マスタを更新する。
     * @param dto 更新対象(PKのフィールドを使用)。
     * @return 更新レコード数。
     */
    @Update({
        "UPDATE room_master",
        "SET",
          "building_id = #{buildingId, jdbcType=VARCHAR},",
          "room_name = #{roomName, jdbcType=VARCHAR},",
          "delete_flg = #{deleteFlg, jdbcType=CHAR}",
        "WHERE",
        "(",
          "room_id = #{roomId, jdbcType=VARCHAR}",
        ")",
    })
    public int update(RoomMasterDTO dto);

    /**
     * 部屋マスタを削除する。
     * @param dto 削除対象(PKのフィールドを使用)。
     * @return 削除レコード数。     */
    @Delete({
        "DELETE FROM room_master",
        "WHERE",
          "room_id = #{roomId, jdbcType=VARCHAR}",
    })
    public int delete(RoomMasterDTO dto);

    /**
     * 建物IDで部屋マスタを検索する。
     * @param dto 検索条件(建物IDのフィールドを使用)。
     * @return 部屋マスタのレコード。存在しない場合はnull。
     */
    @Select({
        "SELECT",
          "room_id,",
          "building_id,",
          "room_name,",
          "delete_flg",
        "FROM",
          "room_master",
        "WHERE",
          "building_id = #{buildingId, jdbcType=VARCHAR} AND",
          "delete_flg = '0'"
    })
    public List<RoomMasterDTO> selectByBuildingId(String buildingId);

}
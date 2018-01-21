package jp.co.humane.rio.orm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import jp.co.humane.rio.orm.dto.CameraMasterDTO;

/**
 * カメラマスタDAO
 */
public interface CameraMasterDAO {

    /**
     * カメラマスタを全レコードを取得する。
     * @return カメラマスタの全レコード。
     */
    @Select({
        "SELECT",
          "camera_id,",
          "io_type,",
          "room_id,",
          "delete_flg",
        "FROM",
          "camera_master"
    })
    public List<CameraMasterDTO> selectAll();

    /**
     * カメラマスタを1レコードを取得する。
     * @param dto 検索条件(PKのフィールドを使用)。
     * @return カメラマスタの1レコード。存在しない場合はnull。
     */
    @Select({
        "SELECT",
          "camera_id,",
          "io_type,",
          "room_id,",
          "delete_flg",
        "FROM",
          "camera_master",
        "WHERE",
          "camera_id = #{cameraId, jdbcType=VARCHAR}",
    })
    public CameraMasterDTO selectOne(CameraMasterDTO dto);

    /**
     * カメラマスタを登録する。
     * @param 登録レコード情報。
     * @return 登録レコード数。
     */
    @Insert({
        "INSERT INTO camera_master",
        "(",
          "camera_id,",
          "io_type,",
          "room_id,",
          "delete_flg",
        ")",
        "VALUES",
        "(",
          "#{cameraId, jdbcType=VARCHAR},",
          "#{ioType, jdbcType=CHAR},",
          "#{roomId, jdbcType=VARCHAR},",
          "#{deleteFlg, jdbcType=CHAR}",
        ")",
    })
    public int insert(CameraMasterDTO dto);

    /**
     * カメラマスタを更新する。
     * @param dto 更新対象(PKのフィールドを使用)。
     * @return 更新レコード数。
     */
    @Update({
        "UPDATE camera_master",
        "SET",
          "io_type = #{ioType, jdbcType=CHAR},",
          "room_id = #{roomId, jdbcType=VARCHAR},",
          "delete_flg = #{deleteFlg, jdbcType=CHAR}",
        "WHERE",
        "(",
          "camera_id = #{cameraId, jdbcType=VARCHAR}",
        ")",
    })
    public int update(CameraMasterDTO dto);

    /**
     * カメラマスタを削除する。
     * @param dto 削除対象(PKのフィールドを使用)。
     * @return 削除レコード数。     */
    @Delete({
        "DELETE FROM camera_master",
        "WHERE",
          "camera_id = #{cameraId, jdbcType=VARCHAR}",
    })
    public int delete(CameraMasterDTO dto);

}
package jp.co.humane.rio.orm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import jp.co.humane.rio.orm.dto.AuthMasterDTO;

/**
 * 権限マスタDAO
 */
public interface AuthMasterDAO {

    /**
     * 権限マスタを全レコードを取得する。
     * @return 権限マスタの全レコード。
     */
    @Select({
        "SELECT",
          "auth_id,",
          "room_id,",
          "delete_flg",
        "FROM",
          "auth_master"
    })
    public List<AuthMasterDTO> selectAll();

    /**
     * 権限マスタを1レコードを取得する。
     * @param dto 検索条件(PKのフィールドを使用)。
     * @return 権限マスタの1レコード。存在しない場合はnull。
     */
    @Select({
        "SELECT",
          "auth_id,",
          "room_id,",
          "delete_flg",
        "FROM",
          "auth_master",
        "WHERE",
          "auth_id = #{authId, jdbcType=VARCHAR} AND",
          "room_id = #{roomId, jdbcType=VARCHAR}",
    })
    public AuthMasterDTO selectOne(AuthMasterDTO dto);

    /**
     * 権限マスタを登録する。
     * @param 登録レコード情報。
     * @return 登録レコード数。
     */
    @Insert({
        "INSERT INTO auth_master",
        "(",
          "auth_id,",
          "room_id,",
          "delete_flg",
        ")",
        "VALUES",
        "(",
          "#{authId, jdbcType=VARCHAR},",
          "#{roomId, jdbcType=VARCHAR},",
          "#{deleteFlg, jdbcType=CHAR}",
        ")",
    })
    public int insert(AuthMasterDTO dto);

    /**
     * 権限マスタを更新する。
     * @param dto 更新対象(PKのフィールドを使用)。
     * @return 更新レコード数。
     */
    @Update({
        "UPDATE auth_master",
        "SET",
          "delete_flg = #{deleteFlg, jdbcType=CHAR}",
        "WHERE",
        "(",
          "auth_id = #{authId, jdbcType=VARCHAR} AND",
          "room_id = #{roomId, jdbcType=VARCHAR}",
        ")",
    })
    public int update(AuthMasterDTO dto);

    /**
     * 権限マスタを削除する。
     * @param dto 削除対象(PKのフィールドを使用)。
     * @return 削除レコード数。     */
    @Delete({
        "DELETE FROM auth_master",
        "WHERE",
          "auth_id = #{authId, jdbcType=VARCHAR} AND",
          "room_id = #{roomId, jdbcType=VARCHAR}",
    })
    public int delete(AuthMasterDTO dto);


}

package jp.co.humane.rio.orm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import jp.co.humane.rio.orm.dto.BuildingMasterDTO;

/**
 * 建物マスタDAO
 */
public interface BuildingMasterDAO {

    /**
     * 建物マスタを全レコードを取得する。
     * @return 建物マスタの全レコード。
     */
    @Select({
        "SELECT",
          "building_id,",
          "building_name,",
          "delete_flg",
        "FROM",
          "building_master"
    })
    public List<BuildingMasterDTO> selectAll();

    /**
     * 建物マスタを1レコードを取得する。
     * @param dto 検索条件(PKのフィールドを使用)。
     * @return 建物マスタの1レコード。存在しない場合はnull。
     */
    @Select({
        "SELECT",
          "building_id,",
          "building_name,",
          "delete_flg",
        "FROM",
          "building_master",
        "WHERE",
          "building_id = #{buildingId, jdbcType=VARCHAR}",
    })
    public BuildingMasterDTO selectOne(BuildingMasterDTO dto);

    /**
     * 建物マスタを登録する。
     * @param 登録レコード情報。
     * @return 登録レコード数。
     */
    @Insert({
        "INSERT INTO building_master",
        "(",
          "building_id,",
          "building_name,",
          "delete_flg",
        ")",
        "VALUES",
        "(",
          "#{buildingId, jdbcType=VARCHAR},",
          "#{buildingName, jdbcType=VARCHAR},",
          "#{deleteFlg, jdbcType=CHAR}",
        ")",
    })
    public int insert(BuildingMasterDTO dto);

    /**
     * 建物マスタを更新する。
     * @param dto 更新対象(PKのフィールドを使用)。
     * @return 更新レコード数。
     */
    @Update({
        "UPDATE building_master",
        "SET",
          "building_name = #{buildingName, jdbcType=VARCHAR},",
          "delete_flg = #{deleteFlg, jdbcType=CHAR}",
        "WHERE",
        "(",
          "building_id = #{buildingId, jdbcType=VARCHAR}",
        ")",
    })
    public int update(BuildingMasterDTO dto);

    /**
     * 建物マスタを削除する。
     * @param dto 削除対象(PKのフィールドを使用)。
     * @return 削除レコード数。     */
    @Delete({
        "DELETE FROM building_master",
        "WHERE",
          "building_id = #{buildingId, jdbcType=VARCHAR}",
    })
    public int delete(BuildingMasterDTO dto);

    /**
     * 建物マスタを全レコードを取得する。
     * @return 建物マスタの全レコード。
     */
    @Select({
        "SELECT",
          "building_id,",
          "building_name,",
          "delete_flg",
        "FROM",
          "building_master",
        "WHERE",
          "delete_flg = '0'"
    })
    public List<BuildingMasterDTO> selectAllAvailable();

}

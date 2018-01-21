package jp.co.humane.rio.orm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import jp.co.humane.rio.orm.dto.SectionMasterDTO;

/**
 * 部署マスタDAO
 */
public interface SectionMasterDAO {

    /**
     * 部署マスタを全レコードを取得する。
     * @return 部署マスタの全レコード。
     */
    @Select({
        "SELECT",
          "section_id,",
          "section_name,",
          "delete_flg",
        "FROM",
          "section_master"
    })
    public List<SectionMasterDTO> selectAll();

    /**
     * 部署マスタを1レコードを取得する。
     * @param dto 検索条件(PKのフィールドを使用)。
     * @return 部署マスタの1レコード。存在しない場合はnull。
     */
    @Select({
        "SELECT",
          "section_id,",
          "section_name,",
          "delete_flg",
        "FROM",
          "section_master",
        "WHERE",
          "section_id = #{sectionId, jdbcType=VARCHAR}",
    })
    public SectionMasterDTO selectOne(SectionMasterDTO dto);

    /**
     * 部署マスタを登録する。
     * @param 登録レコード情報。
     * @return 登録レコード数。
     */
    @Insert({
        "INSERT INTO section_master",
        "(",
          "section_id,",
          "section_name,",
          "delete_flg",
        ")",
        "VALUES",
        "(",
          "#{sectionId, jdbcType=VARCHAR},",
          "#{sectionName, jdbcType=VARCHAR},",
          "#{deleteFlg, jdbcType=CHAR}",
        ")",
    })
    public int insert(SectionMasterDTO dto);

    /**
     * 部署マスタを更新する。
     * @param dto 更新対象(PKのフィールドを使用)。
     * @return 更新レコード数。
     */
    @Update({
        "UPDATE section_master",
        "SET",
          "section_name = #{sectionName, jdbcType=VARCHAR},",
          "delete_flg = #{deleteFlg, jdbcType=CHAR}",
        "WHERE",
        "(",
          "section_id = #{sectionId, jdbcType=VARCHAR}",
        ")",
    })
    public int update(SectionMasterDTO dto);

    /**
     * 部署マスタを削除する。
     * @param dto 削除対象(PKのフィールドを使用)。
     * @return 削除レコード数。     */
    @Delete({
        "DELETE FROM section_master",
        "WHERE",
          "section_id = #{sectionId, jdbcType=VARCHAR}",
    })
    public int delete(SectionMasterDTO dto);

    /**
     * 使用可能な部署データを全て取得する。
     * @return 使用可能な部署マスタレコード。
     */
    @Select({
        "SELECT",
          "section_id,",
          "section_name",
        "FROM",
          "section_master",
        "WHERE",
          "delete_flg = '0'"
    })
    public List<SectionMasterDTO> selectAllAvailable();

}
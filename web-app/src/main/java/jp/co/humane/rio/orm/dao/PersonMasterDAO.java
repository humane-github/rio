package jp.co.humane.rio.orm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import jp.co.humane.rio.orm.dto.PersonMasterDTO;

/**
 * 個人マスタDAO
 */
public interface PersonMasterDAO {

    /**
     * 個人マスタを全レコードを取得する。
     * @return 個人マスタの全レコード。
     */
    @Select({
        "SELECT",
          "person_id,",
          "person_name,",
          "auth_id,",
          "section_id,",
          "lastest_certify_camera_id,",
          "delete_flg",
        "FROM",
          "person_master"
    })
    public List<PersonMasterDTO> selectAll();

    /**
     * 個人マスタを1レコードを取得する。
     * @param dto 検索条件(PKのフィールドを使用)。
     * @return 個人マスタの1レコード。存在しない場合はnull。
     */
    @Select({
        "SELECT",
          "person_id,",
          "person_name,",
          "auth_id,",
          "section_id,",
          "lastest_certify_camera_id,",
          "delete_flg",
        "FROM",
          "person_master",
        "WHERE",
          "person_id = #{personId, jdbcType=VARCHAR}",
    })
    public PersonMasterDTO selectOne(PersonMasterDTO dto);

    /**
     * 使用可能な個人マスタを1レコード取得する。
     * @param dto 検索条件(PKのフィールドを使用)。
     * @return 個人マスタの1レコード。存在しない場合はnull。
     */
    @Select({
        "SELECT",
          "person_id,",
          "person_name,",
          "auth_id,",
          "section_id,",
          "lastest_certify_camera_id,",
          "delete_flg",
        "FROM",
          "person_master",
        "WHERE",
          "person_id = #{personId, jdbcType=VARCHAR} AND",
          "delete_flg = '0'"
    })
    public PersonMasterDTO selectOneAvailable(PersonMasterDTO dto);

    /**
     * 個人マスタを登録する。
     * @param 登録レコード情報。
     * @return 登録レコード数。
     */
    @Insert({
        "INSERT INTO person_master",
        "(",
          "person_id,",
          "person_name,",
          "auth_id,",
          "section_id,",
          "lastest_certify_camera_id,",
          "delete_flg",
        ")",
        "VALUES",
        "(",
          "#{personId, jdbcType=VARCHAR},",
          "#{personName, jdbcType=VARCHAR},",
          "#{authId, jdbcType=VARCHAR},",
          "#{sectionId, jdbcType=VARCHAR},",
          "#{lastestCertifyCameraId, jdbcType=VARCHAR},",
          "#{deleteFlg, jdbcType=CHAR}",
        ")",
    })
    public int insert(PersonMasterDTO dto);

    /**
     * 個人マスタを更新する。
     * @param dto 更新対象(PKのフィールドを使用)。
     * @return 更新レコード数。
     */
    @Update({
        "UPDATE person_master",
        "SET",
          "person_name = #{personName, jdbcType=VARCHAR},",
          "auth_id = #{authId, jdbcType=VARCHAR},",
          "section_id = #{sectionId, jdbcType=VARCHAR},",
          "lastest_certify_camera_id = #{lastestCertifyCameraId, jdbcType=VARCHAR},",
          "delete_flg = #{deleteFlg, jdbcType=CHAR}",
        "WHERE",
        "(",
          "person_id = #{personId, jdbcType=VARCHAR}",
        ")",
    })
    public int update(PersonMasterDTO dto);

    /**
     * 個人マスタを削除する。
     * @param dto 削除対象(PKのフィールドを使用)。
     * @return 削除レコード数。
     */
    @Delete({
        "DELETE FROM person_master",
        "WHERE",
          "person_id = #{personId, jdbcType=VARCHAR}"
    })
    public int delete(PersonMasterDTO dto);

    /**
     * 認証の有無を取得する。
     * @param personId 個人ID。
     * @param cameraId カメラID。
     * @return 認証の有無。
     */
    @Select({
        "SELECT",
          "CASE ",
            "WHEN count(0) > 0 THEN TRUE",
          "ELSE FALSE END",
        "FROM",
          "person_master p",
            "LEFT JOIN auth_master a ON (p.auth_id = a.auth_id)",
            "LEFT JOIN camera_master c ON (a.room_id = c.room_id)",
        "WHERE",
          "p.delete_flg = '0'",
          "AND a.delete_flg = '0'",
          "AND c.delete_flg = '0'",
          "AND p.person_id = #{personId, jdbcType=VARCHAR}",
          "AND c.camera_id = #{cameraId, jdbcType=VARCHAR}"
    })
    public Boolean hasAuthentication(@Param("personId") String personId,
                                     @Param("cameraId") String cameraId);
}
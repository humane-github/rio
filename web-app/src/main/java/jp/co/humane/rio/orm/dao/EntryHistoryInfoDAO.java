package jp.co.humane.rio.orm.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import jp.co.humane.rio.orm.dto.EntryHistoryDetailInfoDTO;
import jp.co.humane.rio.orm.dto.EntryHistoryDetailInfoParamDTO;
import jp.co.humane.rio.orm.dto.EntryHistoryInfoDTO;

/**
 * 入退出履歴情報DAO
 */
public interface EntryHistoryInfoDAO {

    /**
     * 入退出履歴情報を全レコードを取得する。
     * @return 入退出履歴情報の全レコード。
     */
    @Select({
        "SELECT",
          "person_id,",
          "section_id,",
          "certify_result,",
          "camera_id,",
          "certify_date,",
          "image_file_name",
        "FROM",
          "entry_history_info"
    })
    public List<EntryHistoryInfoDTO> selectAll();

    /**
     * 入退出履歴情報を1レコードを取得する。
     * @param dto 検索条件(PKのフィールドを使用)。
     * @return 入退出履歴情報の1レコード。存在しない場合はnull。
     */
    @Select({
        "SELECT",
          "person_id,",
          "section_id,",
          "certify_result,",
          "camera_id,",
          "certify_date,",
          "image_file_name",
        "FROM",
          "entry_history_info",
        "WHERE",
          "person_id = #{personId, jdbcType=VARCHAR} AND",
          "section_id = #{sectionId, jdbcType=VARCHAR} AND",
          "certify_result = #{certifyResult, jdbcType=BOOLEAN} AND",
          "camera_id = #{cameraId, jdbcType=VARCHAR} AND",
          "certify_date = #{certifyDate, jdbcType=TIMESTAMP} AND",
          "image_file_name = #{imageFileName, jdbcType=VARCHAR}",
    })
    public EntryHistoryInfoDTO selectOne(EntryHistoryInfoDTO dto);

    /**
     * 入退出履歴情報を登録する。
     * @param 登録レコード情報。
     * @return 登録レコード数。
     */
    @Insert({
        "INSERT INTO entry_history_info",
        "(",
          "person_id,",
          "section_id,",
          "certify_result,",
          "camera_id,",
          "certify_date,",
          "image_file_name",
        ")",
        "VALUES",
        "(",
          "#{personId, jdbcType=VARCHAR},",
          "#{sectionId, jdbcType=VARCHAR},",
          "#{certifyResult, jdbcType=BOOLEAN},",
          "#{cameraId, jdbcType=VARCHAR},",
          "#{certifyDate, jdbcType=TIMESTAMP},",
          "#{imageFileName, jdbcType=VARCHAR}",
        ")",
    })
    public int insert(EntryHistoryInfoDTO dto);

    /**
     * 入退出履歴情報を更新する。
     * @param dto 更新対象(PKのフィールドを使用)。
     * @return 更新レコード数。
     */
    @Update({
        "UPDATE entry_history_info",
        "SET",
          "person_id = #{personId, jdbcType=VARCHAR},",
          "section_id = #{sectionId, jdbcType=VARCHAR},",
          "certify_result = #{certifyResult, jdbcType=BOOLEAN},",
          "camera_id = #{cameraId, jdbcType=VARCHAR},",
          "certify_date = #{certifyDate, jdbcType=TIMESTAMP},",
          "image_file_name = #{imageFileName, jdbcType=VARCHAR}",
        "WHERE",
        "(",
          "person_id = #{personId, jdbcType=VARCHAR} AND",
          "section_id = #{sectionId, jdbcType=VARCHAR} AND",
          "certify_result = #{certifyResult, jdbcType=BOOLEAN} AND",
          "camera_id = #{cameraId, jdbcType=VARCHAR} AND",
          "certify_date = #{certifyDate, jdbcType=TIMESTAMP} AND",
          "image_file_name = #{imageFileName, jdbcType=VARCHAR}",
        ")",
    })
    public int update(EntryHistoryInfoDTO dto);

    /**
     * 入退出履歴情報を削除する。
     * @param dto 削除対象(PKのフィールドを使用)。
     * @return 削除レコード数。     */
    @Delete({
        "DELETE FROM entry_history_info",
        "WHERE",
          "person_id = #{personId, jdbcType=VARCHAR} AND",
          "section_id = #{sectionId, jdbcType=VARCHAR} AND",
          "certify_result = #{certifyResult, jdbcType=BOOLEAN} AND",
          "camera_id = #{cameraId, jdbcType=VARCHAR} AND",
          "certify_date = #{certifyDate, jdbcType=TIMESTAMP} AND",
          "image_file_name = #{imageFileName, jdbcType=VARCHAR}",
    })
    public int delete(EntryHistoryInfoDTO dto);

    /**
     * 入退室履歴情報一覧情報を取得する。
     * @param dto 検索条件。
     * @return 入退室履歴情報一覧情報。
     */
    @Select({
        "<script>",
          "SELECT",
            "r.room_id,",
            "b.building_name,",
            "r.room_name,",
            "c.io_type,",
            "h.certify_date,",
            "h.certify_result,",
            "s.section_name,",
            "p.person_id,",
            "p.person_name,",
            "h.image_file_name",
          "FROM",
            "entry_history_info h",
              "left join camera_master c on h.camera_id = c.camera_id",
              "left join room_master r on c.room_id = r.room_id",
              "left join building_master b on r.building_id = b.building_id",
              "left join section_master s on h.section_id = s.section_id",
              "left join person_master p on h.person_id = p.person_id",
          "<where>",
            "<if test='buildingId != null'>",
              "b.building_id = #{buildingId, jdbcType=VARCHAR}",
            "</if>",
            "<if test='roomId != null'>",
              "AND r.room_id = #{roomId, jdbcType=VARCHAR}",
            "</if>",
            "<if test='ioType != null'>",
              "AND c.io_type = #{ioType, jdbcType=VARCHAR}",
            "</if>",
            "<if test='certifyDateFrom != null'>",
              "AND #{certifyDateFrom, jdbcType=TIMESTAMP} &lt;= h.certify_date",
            "</if>",
            "<if test='certifyDateTo != null'>",
              "AND h.certify_date &lt;= #{certifyDateTo, jdbcType=TIMESTAMP}",
            "</if>",
            "<if test='sectionId != null'>",
              "AND s.section_id = #{sectionId, jdbcType=VARCHAR}",
            "</if>",
            "<if test='personId != null'>",
              "AND p.person_id = #{personId, jdbcType=VARCHAR}",
            "</if>",
            "<if test='personName != null'>",
              "AND p.person_name = #{personName, jdbcType=VARCHAR}",
            "</if>",
          "</where>",
        "</script>"
    })
    public List<EntryHistoryDetailInfoDTO> selectDetail(EntryHistoryDetailInfoParamDTO dto);

    /**
     * 入退室履歴を登録する。
     * @param personId       個人ID。
     * @param certifyResult  認証結果。
     * @param cameraId       カメラID。
     * @param certifyDate    認証日時。
     * @param imageFileName  画像ファイル名。
     * @return レコード数。
     */
    @Insert({
        "INSERT INTO entry_history_info",
          "(",
            "person_id,",
            "section_id,",
            "certify_result,",
            "camera_id,",
            "certify_date,",
            "image_file_name",
          ")",
        "SELECT",
          "#{personId, jdbcType=VARCHAR},",
          "p.section_id,",
          "#{certifyResult, jdbcType=BOOLEAN},",
          "#{cameraId, jdbcType=VARCHAR},",
          "#{certifyDate, jdbcType=TIMESTAMP},",
          "#{imageFileName, jdbcType=VARCHAR}",
        "FROM",
          "person_master p",
        "WHERE",
          "p.person_id = 'p1'"
    })
    public Integer insertHistory(@Param("personId")     String personId,
                                 @Param("certifyResult") Boolean certifyResult,
                                 @Param("cameraId")      String cameraId,
                                 @Param("certifyDate")   Date certifyDate,
                                 @Param("imageFileName") String imageFileName);

}
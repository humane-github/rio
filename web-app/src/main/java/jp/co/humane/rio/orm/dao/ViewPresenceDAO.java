package jp.co.humane.rio.orm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import jp.co.humane.rio.orm.dto.PresenceDTO;
import jp.co.humane.rio.orm.dto.ViewPresenceDTO;

/**
 * 在室状況ビューDAO
 */
public interface ViewPresenceDAO {

    /**
     * 在室状況ビューを全レコードを取得する。
     * @return 在室状況ビューの全レコード。
     */
    @Select({
        "SELECT",
          "building_id,",
          "building_name,",
          "room_id,",
          "room_name,",
          "person_id,",
          "person_name,",
          "section_id,",
          "section_name,",
          "certify_date_in,",
          "certify_date_out",
        "FROM",
          "view_presence"
    })
    public List<ViewPresenceDTO> selectAll();

    /**
     * 入室者詳細情報一覧を取得する。
     * @return 入室者詳細情報一覧。
     */
    @Select({
      "<script>",
        "SELECT",
          "building_id,",
          "building_name,",
          "room_id,",
          "room_name,",
          "person_id,",
          "person_name,",
          "section_id,",
          "section_name,",
          "certify_date_in,",
          "certify_date_out",
        "FROM",
          "view_presence",
        "<where>",
          "<if test='buildingId != null'>",
            "building_id = #{buildingId, jdbcType=VARCHAR}",
          "</if>",
          "<if test='roomId != null'>",
            "AND room_id = #{roomId, jdbcType=VARCHAR}",
          "</if>",
          "<if test='personId != null'>",
            "AND person_id = #{personId, jdbcType=VARCHAR}",
          "</if>",
          "<if test='personName != null'>",
            "AND person_name = #{personName, jdbcType=VARCHAR}",
          "</if>",
        "</where>",
      "</script>"
    })
    public List<ViewPresenceDTO> selectDetail(ViewPresenceDTO dto);

    /**
     * 入室者情報一覧を取得する。
     * @param dto 検索条件。
     * @return 入室者情報一覧。
     */
    @Select({
        "<script>",
          "SELECT",
            "all_br.building_id,",
            "all_br.room_id,",
            "all_br.building_name,",
            "all_br.room_name,",
            "CASE WHEN",
              "presence_br.cnt IS NULL THEN 0",
              "ELSE presence_br.cnt",
            "END AS entrance_person_num",
          "FROM",
          "(",
            "SELECT",
              "b.building_id,",
              "r.room_id,",
              "b.building_name,",
              "r.room_name",
            "FROM",
              "building_master b left join room_master r on (b.building_id = r.building_id)",
            "WHERE",
              "b.delete_flg = '0' AND",
              "r.delete_flg = '0'",
          ") all_br left outer join ",
            "(",
              "SELECT",
                "building_id,",
                "room_id,",
                "count(0) as cnt",
              "FROM",
                "view_presence",
              "WHERE",
                "certify_date_out IS NULL",
              "GROUP BY",
                "building_id,",
                "room_id",
            ") presence_br",
            "on (all_br.building_id = presence_br.building_id AND all_br.room_id = presence_br.room_id)",
          "<where>",
            "<if test='buildingId != null'>",
              "all_br.building_id = #{buildingId, jdbcType=VARCHAR}",
            "</if>",
            "<if test='roomId != null'>",
              "AND all_br.room_id = #{roomId, jdbcType=VARCHAR}",
            "</if>",
          "</where>",
        "</script>"
    })
    public List<PresenceDTO> selectPresence(PresenceDTO dto);
}
package jp.co.humane.rio.api.consts;

/**
 * URLのパスの定数定義クラス。
 * @author terada
 *
 */
public class URL {

    /** 認証IF */
    public static final String AUTH = "/v1.0/user/certify";

    /** 入退室履歴情報一覧取得IF */
    public static final String ENTRY_HISTORY_SELECT = "/v1.0/entryHistoryInfo/selectList";

    /** 入室者情報一覧取得IF */
    public static final String ENTRANCE_PERSON_SELECT = "/v1.0/entrancePersonInfo/selectList";

    /** 入室者情報一覧取得IF */
    public static final String ENTRANCE_PERSON_DETAIL_SELECT = "/v1.0/entrancePersonDetailInfo/selectList";

    /** 建物情報一覧取得IF */
    public static final String BUILDING_SELECT = "/v1.0/buildingInfo/selectList";

    /** 部屋情報一覧取得IF */
    public static final String ROOM_SELECT = "/v1.0/roomInfo/selectList";

    /** 部署情報一覧取得IF */
    public static final String SECTION_SELECT = "/v1.0/sectionInfo/selectList";

    /** 個人情報取得IF */
    public static final String PERSON_SELECT = "/v1.0/personInfo/select";

    /** 個人情報更新IF */
    public static final String PERSON_UPDATE = "/v1.0/personInfo/update";

    /** 個人情報登録IF */
    public static final String PERSON_REGIST = "/v1.0/personInfo/regist";

    /** 個人マスタ画像URL */
    public static final String PERSON_IMG_PATH = "/rio/auth/";

    /** 正面画像 */
    public static final String FRONT_IMG = "front.png";

    /** 左側画像 */
    public static final String LEFT_IMG = "left.png";

    /** 右側画像 */
    public static final String RIGHT_IMG = "right.png";

    /**
     * デフォルトコンストラクタ。
     */
    private URL() {
        super();
    }

}

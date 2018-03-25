package jp.co.humane.rio.common.consts;

/**
 * 日時の書式定義クラス。
 * @author terada
 *
 */
public class DateFormat {

    // アンダースコアの右は以下
    //   S : スラッシュ区切り
    //   C : コロン区切り
    //   H : ハイフン区切り

    /** yyyy/MM/dd hh:mm:ss */
    public static final String YMDHMS_SC = "yyyy/MM/dd hh:mm:ss";

    /** yyyy/MM/dd hh:mm */
    public static final String YMDHM_SC = "yyyy/MM/dd hh:mm";

    /** yyyy/MM/dd */
    public static final String YMD_S = "yyyy/MM/dd";

    /** HHmmss */
    public static final String HMS = "HHmmss";

}

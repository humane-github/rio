package jp.co.humane.rio.common.utils;

import java.text.ParseException;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

import jp.co.humane.rio.common.exception.ApplicationException;

/**
 * 日時変換処理のユーティリティクラス。
 * DateUtils、FormatUtilsと同じ機能だがApplicationExceptionを投げる。
 *
 * @author terada
 *
 */
public class DateUtils {

    /**
     * 現在日時を取得する。
     * @return 現在日時。
     */
    public static Date now() {
        Instant instant = ZonedDateTime.now().toInstant();
        return Date.from(instant);
    }

    /**
     * 文字列をDate型に変換する。
     *
     * @param date   日付を表す文字列。
     * @param format 書式。
     * @return Dateインスタンス。
     */
    public static Date parse(String date, String format) {
        Date ret = null;
        try {
            ret = org.apache.commons.lang3.time.DateUtils.parseDate(date, format);
        } catch (ParseException ex) {
            String msg = "日付の変換処理に失敗しました。値=" + date + "、書式=" + format;
            throw new ApplicationException(msg, ex);
        }
        return ret;
    }

    /**
     * Date型を文字列に変換する。
     *
     * @param date   Dateインスタンス。
     * @param format 書式。
     * @return 文字列。
     */
    public static String format(Date date, String format) {
        return DateFormatUtils.format(date, format);
    }


}

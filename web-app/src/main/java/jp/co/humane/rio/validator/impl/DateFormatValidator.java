package jp.co.humane.rio.validator.impl;

import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import jp.co.humane.rio.validator.DateFormat;

/**
 * 日付書式チェック用バリデータ。
 * @author terada
 *
 */
public class DateFormatValidator implements ConstraintValidator<DateFormat, String> {

    /** 書式 */
    private String format = null;

    /**
     * デフォルトコンストラクタ。
     */
    public DateFormatValidator() {
        super();
    }

    /**
     * 初期化処理。
     */
    @Override
    public void initialize(DateFormat constraintAnnotation) {
        this.format = constraintAnnotation.format();
    }

    /**
     * バリデーションチェック処理。
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        // nullチェックは別のバリデータで行うので、nullはここではチェックOKとする。
        if (StringUtils.isEmpty(value)) {
            return true;
        }

        // 文字列が書式に従っていればチェックOK。それ以外はチェックNG
        try {

            Date date = DateUtils.parseDateStrictly(value, format);
            String dateString = DateFormatUtils.format(date, format);
            if (!StringUtils.equals(dateString, value)) {
                return false;
            }

        } catch (Exception ex) {
            return false;
        }

        return true;
    }

}

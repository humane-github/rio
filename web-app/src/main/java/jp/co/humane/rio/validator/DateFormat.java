package jp.co.humane.rio.validator;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import jp.co.humane.rio.validator.impl.DateFormatValidator;

/**
 * 日付書式チェック用アノテーション。
 * @author terada
 *
 */
@Documented
@Constraint(validatedBy = { DateFormatValidator.class })
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
public @interface DateFormat {

    /**
     * メッセージ。
     * @return メッセージ。
     */
    String message() default "{format}の書式で入力してください。";

    /**
     * 書式。
     * @return 書式。
     */
    String format();

    /**
     * グループ。
     * @return グループ。
     */
    Class<?>[] groups() default {};

    /**
     * ペイロード。
     * @return ペイロード。
     */
    Class<? extends Payload>[] payload() default {};

    /**
     * valueアノテーション。
     * @author terada
     *
     */
    @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
    @Retention(RUNTIME)
    @Documented
    public @interface List {
        DateFormat[] value();
    }
}

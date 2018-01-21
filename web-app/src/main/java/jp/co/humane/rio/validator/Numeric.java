package jp.co.humane.rio.validator;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import jp.co.humane.rio.validator.impl.NumericValidator;

/**
 * 文字列の書式が数値であることをチェックするアノテーション。
 * @author terada
 *
 */
@Documented
@Constraint(validatedBy = { NumericValidator.class })
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
public @interface Numeric {

    /**
     * メッセージ。
     * @return メッセージ。
     */
    String message() default "数値で入力してください。";

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
        Numeric[] value();
    }
}

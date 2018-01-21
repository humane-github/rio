package jp.co.humane.rio.validator;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import jp.co.humane.rio.validator.impl.AlphaNumericValidator;

/**
 * 半角英数チェック用アノテーション。
 * @author terada
 *
 */
@Documented
@Constraint(validatedBy = { AlphaNumericValidator.class })
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
public @interface AlphaNumeric {

    /**
     * メッセージ。
     * @return メッセージ。
     */
    String message() default "半角英数字で入力してください。";

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
        AlphaNumeric[] value();
    }
}

package jp.co.humane.rio.validator;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import jp.co.humane.rio.validator.impl.WideValidator;

/**
 * 文字列の書式が全角であることをチェックするアノテーション。
 * @author terada
 *
 */
@Documented
@Constraint(validatedBy = { WideValidator.class })
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
public @interface Wide {

    /**
     * メッセージ。
     * @return メッセージ。
     */
    String message() default "半角英数字または記号が記載されています。";

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

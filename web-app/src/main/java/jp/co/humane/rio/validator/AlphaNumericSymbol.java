package jp.co.humane.rio.validator;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import jp.co.humane.rio.validator.impl.AlphaNumericSymbolValidator;

/**
 * 半角英数記号チェック用アノテーション。
 * @author terada
 *
 */
@Documented
@Constraint(validatedBy = { AlphaNumericSymbolValidator.class })
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
public @interface AlphaNumericSymbol {

    /**
     * メッセージ。
     * @return メッセージ。
     */
    String message() default "半角英数字及び記号で入力してください。";

    /**
     * 許容する記号。
     * @return 許容する記号。
     * 必要になったら実装。
     */
    // String symbols = " !\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~";

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
        AlphaNumericSymbol[] value();
    }
}

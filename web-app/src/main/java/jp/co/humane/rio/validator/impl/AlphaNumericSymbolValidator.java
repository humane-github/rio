package jp.co.humane.rio.validator.impl;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

import jp.co.humane.rio.validator.AlphaNumericSymbol;

/**
 * 半角英数＋記号チェック用バリデータ。
 * @author terada
 *
 */
public class AlphaNumericSymbolValidator implements ConstraintValidator<AlphaNumericSymbol, String> {

    /** 半角英数＋記号の正規表現 */
    private static Pattern PATTERN = Pattern.compile("^\\p{Print}*$");

    /**
     * デフォルトコンストラクタ。
     */
    public AlphaNumericSymbolValidator() {
        super();
    }

    /**
     * 初期化処理。
     */
    @Override
    public void initialize(AlphaNumericSymbol constraintAnnotation) {
        return;
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

        // 文字列が数値のみであればチェックOK。それ以外はチェックNG
        return PATTERN.matcher(value).find();
    }
}

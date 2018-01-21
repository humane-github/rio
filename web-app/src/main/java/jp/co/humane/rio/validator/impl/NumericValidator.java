package jp.co.humane.rio.validator.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

import jp.co.humane.rio.validator.Numeric;

/**
 * 数値書式チェック用バリデータ。
 * @author terada
 *
 */
public class NumericValidator implements ConstraintValidator<Numeric, String> {

    /**
     * デフォルトコンストラクタ。
     */
    public NumericValidator() {
        super();
    }

    /**
     * 初期化処理。
     */
    @Override
    public void initialize(Numeric constraintAnnotation) {
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
        return StringUtils.isNumeric(value);
    }
}

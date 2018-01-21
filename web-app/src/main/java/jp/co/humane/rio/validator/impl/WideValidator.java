package jp.co.humane.rio.validator.impl;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

import jp.co.humane.rio.validator.Wide;

/**
 * 全角書式チェック用バリデータ。
 * @author terada
 *
 */
public class WideValidator implements ConstraintValidator<Wide, String> {

    /** 全角の正規表現 */
    private static final Pattern PATTERN = Pattern.compile("^[^\\x01-\\x7E]*$");

    /**
     * デフォルトコンストラクタ。
     */
    public WideValidator() {
        super();
    }

    /**
     * 初期化処理。
     */
    @Override
    public void initialize(Wide constraintAnnotation) {
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

        // 文字列が全角のみであればチェックOK。それ以外はチェックNG
        return PATTERN.matcher(value).find();
    }}

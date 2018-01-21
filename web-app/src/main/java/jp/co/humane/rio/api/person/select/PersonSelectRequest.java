package jp.co.humane.rio.api.person.select;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import jp.co.humane.rio.common.dto.BaseDTO;
import jp.co.humane.rio.validator.AlphaNumeric;

/**
 * 個人情報取得IFリクエストDTO。
 * @author terada
 *
 */
public class PersonSelectRequest extends BaseDTO {

    /** 個人ID */
    @NotNull
    @Length(max = 5)
    @AlphaNumeric
    private String personId = null;

    /**
     * デフォルトコンストラクタ。
     */
    public PersonSelectRequest() {
        super();
    }

    /**
     * personIdを取得する。
     * @return personId personId。
     */
    public String getPersonId() {
        return personId;
    }

    /**
     * personIdを設定する。
     * @param personId personId。
     */
    public void setPersonId(String personId) {
        this.personId = personId;
    }
}

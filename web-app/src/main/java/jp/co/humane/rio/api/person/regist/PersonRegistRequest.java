package jp.co.humane.rio.api.person.regist;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import jp.co.humane.rio.common.dto.BaseDTO;
import jp.co.humane.rio.validator.AlphaNumeric;
import jp.co.humane.rio.validator.Wide;

/**
 * 個人情報登録IFのリクエストDTO。
 * @author terada
 *
 */
public class PersonRegistRequest extends BaseDTO {

    /** 個人ID */
    @NotNull
    @Length(max = 5)
    @AlphaNumeric
    private String personId = null;

    /** 氏名 */
    @NotNull
    @Length(max = 20)
    @Wide
    private String personName = null;

    /** 部署ID */
    @NotNull
    @Length(max = 5)
    @AlphaNumeric
    private String sectionId = null;

    /**
     * デフォルトコンストラクタ。
     */
    public PersonRegistRequest() {
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

    /**
     * personNameを取得する。
     * @return personName personName。
     */
    public String getPersonName() {
        return personName;
    }

    /**
     * personNameを設定する。
     * @param personName personName。
     */
    public void setPersonName(String personName) {
        this.personName = personName;
    }

    /**
     * sectionIdを取得する。
     * @return sectionId sectionId。
     */
    public String getSectionId() {
        return sectionId;
    }

    /**
     * sectionIdを設定する。
     * @param sectionId sectionId。
     */
    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

}

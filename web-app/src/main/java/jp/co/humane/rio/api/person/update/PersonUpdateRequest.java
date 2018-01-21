package jp.co.humane.rio.api.person.update;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import jp.co.humane.rio.common.dto.BaseDTO;
import jp.co.humane.rio.validator.AlphaNumeric;
import jp.co.humane.rio.validator.Wide;

/**
 * 個人情報更新IFリクエストDTO。
 * @author terada
 *
 */
public class PersonUpdateRequest extends BaseDTO {

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

    /** 正面画像ファイル */
    private MultipartFile frontImageFile = null;

    /** 左側画像ファイル */
    private MultipartFile leftImageFile = null;

    /** 右側画像ファイル */
    private MultipartFile rightImageFile = null;

    /**
     * デフォルトコンストラクタ。
     */
    public PersonUpdateRequest() {
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
     * frontImageFileを取得する。
     * @return frontImageFile frontImageFile。
     */
    public MultipartFile getFrontImageFile() {
        return frontImageFile;
    }

    /**
     * frontImageFileを設定する。
     * @param frontImageFile frontImageFile。
     */
    public void setFrontImageFile(MultipartFile frontImageFile) {
        this.frontImageFile = frontImageFile;
    }

    /**
     * leftImageFileを取得する。
     * @return leftImageFile leftImageFile。
     */
    public MultipartFile getLeftImageFile() {
        return leftImageFile;
    }

    /**
     * leftImageFileを設定する。
     * @param leftImageFile leftImageFile。
     */
    public void setLeftImageFile(MultipartFile leftImageFile) {
        this.leftImageFile = leftImageFile;
    }

    /**
     * rightImageFileを取得する。
     * @return rightImageFile rightImageFile。
     */
    public MultipartFile getRightImageFile() {
        return rightImageFile;
    }

    /**
     * rightImageFileを設定する。
     * @param rightImageFile rightImageFile。
     */
    public void setRightImageFile(MultipartFile rightImageFile) {
        this.rightImageFile = rightImageFile;
    }

    /*
     * @inheritDoc
     */
    @Override
    public String toString() {
        String front = (null == frontImageFile) ? "null" :
                       (frontImageFile.isEmpty()) ? "empty" : "(省略)";
        String left  = (null == leftImageFile) ? "null" :
                       (leftImageFile.isEmpty()) ? "empty" : "(省略)";
        String right = (null == rightImageFile) ? "null" :
                       (rightImageFile.isEmpty()) ? "empty" : "(省略)";
        return "{"
              + "\"personId\": \"" + personId + "\", "
              + "\"personName\": " + personName + "\", "
              + "\"frontImageFile\": " + front + "\", "
              + "\"leftImageFile\": " + left + "\", "
              + "\"rightImageFile\": " + right + "\", "
              + "}";
    }

}

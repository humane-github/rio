package jp.co.humane.rio.api.person.select;

import javax.validation.constraints.NotNull;

import jp.co.humane.rio.common.dto.BaseDTO;

/**
 * 個人情報取得IFレスポンスDTO。
 * @author terada
 *
 */
public class PersonSelectResponse extends BaseDTO {

    /** 個人ID */
    @NotNull
    private String personId = null;

    /** 氏名 */
    @NotNull
    private String personName = null;

    /** 正面画像ファイルパス */
    private String frontImageFilePath = null;

    /** 左側画像ファイルパス */
    private String leftImageFilePath = null;

    /** 右側画像ファイルパス */
    private String rightImageFilePath = null;

    /**
     * デフォルトコンストラクタ。
     */
    public PersonSelectResponse() {
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
     * frontImageFilePathを取得する。
     * @return frontImageFilePath frontImageFilePath。
     */
    public String getFrontImageFilePath() {
        return frontImageFilePath;
    }

    /**
     * frontImageFilePathを設定する。
     * @param frontImageFilePath frontImageFilePath。
     */
    public void setFrontImageFilePath(String frontImageFilePath) {
        this.frontImageFilePath = frontImageFilePath;
    }

    /**
     * leftImageFilePathを取得する。
     * @return leftImageFilePath leftImageFilePath。
     */
    public String getLeftImageFilePath() {
        return leftImageFilePath;
    }

    /**
     * leftImageFilePathを設定する。
     * @param leftImageFilePath leftImageFilePath。
     */
    public void setLeftImageFilePath(String leftImageFilePath) {
        this.leftImageFilePath = leftImageFilePath;
    }

    /**
     * rightImageFilePathを取得する。
     * @return rightImageFilePath rightImageFilePath。
     */
    public String getRightImageFilePath() {
        return rightImageFilePath;
    }

    /**
     * rightImageFilePathを設定する。
     * @param rightImageFilePath rightImageFilePath。
     */
    public void setRightImageFilePath(String rightImageFilePath) {
        this.rightImageFilePath = rightImageFilePath;
    }
}

package jp.co.humane.rio.api.section;

import javax.validation.constraints.NotNull;

import jp.co.humane.rio.common.dto.BaseDTO;

/**
 * 部署情報一覧取得IFレスポンスDTO。
 * @author terada
 *
 */
public class SectionResponse extends BaseDTO {

    /** 部署ID */
    @NotNull
    private String sectionId = null;

    /** 部署名 */
    @NotNull
    private String sectionName = null;

    /**
     * デフォルトコンストラクタ。
     */
    public SectionResponse() {
        super();
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

    /**
     * sectionNameを取得する。
     * @return sectionName sectionName。
     */
    public String getSectionName() {
        return sectionName;
    }

    /**
     * sectionNameを設定する。
     * @param sectionName sectionName。
     */
    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

}

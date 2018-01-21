package jp.co.humane.rio.api.auth;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import jp.co.humane.rio.common.dto.BaseDTO;

/**
 * 認証IFのリクエストDTO。
 * @author terada
 *
 */
public class AuthenticationRequest extends BaseDTO {

    /** カメラID */
    @NotNull
    @Length(max = 5)
    private String cameraId = null;

    /** イメージ */
    @NotNull
    private MultipartFile image = null;

    /**
     * デフォルトコンストラクタ。
     */
    public AuthenticationRequest() {
        super();
    }

    /**
     * cameraIdを取得する。
     * @return cameraId cameraId。
     */
    public String getCameraId() {
        return cameraId;
    }

    /**
     * cameraIdを設定する。
     * @param cameraId cameraId。
     */
    public void setCameraId(String cameraId) {
        this.cameraId = cameraId;
    }

    /**
     * imageを取得する。
     * @return image image。
     */
    public MultipartFile getImage() {
        return image;
    }

    /**
     * imageを設定する。
     * @param image image。
     */
    public void setImage(MultipartFile image) {
        this.image = image;
    }

    /*
     * @inheritDoc
     */
    @Override
    public String toString() {
        String img = (null == image) ? "null" :
                     (image.isEmpty()) ? "empty" : "(省略)";
        return "{\"cameraId\": \"" + cameraId + "\", \"image\": " + img + "}";
    }

}

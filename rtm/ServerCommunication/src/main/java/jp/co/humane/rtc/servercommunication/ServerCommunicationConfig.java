package jp.co.humane.rtc.servercommunication;

import jp.co.humane.rtc.common.starter.bean.ConfigBase;

/**
 * ServerCommunicationの設定情報。
 * @author kawano
 *
 */
public class ServerCommunicationConfig extends ConfigBase {

    /** サーバーのURI */
    private String serverUri = "http://rio-server/rio/mng/room-watch/v1.0/user/certify";

    /** カメラID */
    private String cameraId = System.getProperty("camera.id");

    /** ビューアの有効状態 */
    private Boolean enableViewer = false;

	/**
     * デフォルトコンストラクタ。
     */
    public ServerCommunicationConfig() {
        // 実行周期のデフォルト値を3秒に設定
        this.interval = 3000000L;
    }

    /**
     * serverUriを取得する。
     * @return serverUri serverUri。
     */
    public String getServerUri() {
        return serverUri;
    }

    /**
     * serverUriを設定する。
     * @param serverUri serverUri。
     */
    public void setServerUri(String serverUri) {
        this.serverUri = serverUri;
    }

    /**
     * enableViewerを取得する。
     * @return enableViewer enableViewer。
     */
    public Boolean getEnableViewer() {
        return enableViewer;
    }

    /**
     * enableViewerを設定する。
     * @param enableViewer enableViewer。
     */
    public void setEnableViewer(Boolean enableViewer) {
        this.enableViewer = enableViewer;
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
}

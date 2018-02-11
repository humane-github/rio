package jp.co.humane.rtc.servercommunication;

import jp.co.humane.rtc.common.starter.bean.ConfigBase;

/**
 * ServerCommunicationの設定情報。
 * @author kawano
 *
 */
public class ServerCommunicationConfig extends ConfigBase {

	/** カメラのID */
    private String cameraid = "0";

    /** サーバーのURI */
    private String serveruri = "";

	public String getCameraid() {
		return cameraid;
	}

	/**
     * デフォルトコンストラクタ。
     */
    public ServerCommunicationConfig() {
        // 実行周期のデフォルト値を100ミリ秒に設定
        this.interval = 100000L;
    }

	public void setCameraid(String cameraid) {
		this.cameraid = cameraid;
	}

	public String getServeruri() {
		return serveruri;
	}

	public void setServeruri(String serveruri) {
		this.serveruri = serveruri;
	}

}

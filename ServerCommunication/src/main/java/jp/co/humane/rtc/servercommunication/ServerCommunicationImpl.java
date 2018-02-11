package jp.co.humane.rtc.servercommunication;

import RTC.CameraImage;
import RTC.ReturnCode_t;
import RTC.TimedBoolean;
import jp.co.humane.rtc.common.component.DataFlowComponent;
import jp.co.humane.rtc.common.port.RtcInPort;
import jp.co.humane.rtc.common.port.RtcOutPort;
import jp.co.humane.rtc.common.starter.RtcStarter;
import jp.co.humane.rtc.common.util.CorbaObj;
import jp.go.aist.rtm.RTC.Manager;

/**
 * カメラ画像をサーバーに送信し、サーバーから認証結果を受け取る。
 * @author kawano.
 */
public class ServerCommunicationImpl extends DataFlowComponent<ServerCommunicationConfig> {

	/** カメラ映像の入力ポート */
    private RtcInPort<CameraImage> cameraImageIn = new RtcInPort<>("CameraImage", CorbaObj.newCameraImage());

    /** 認証結果の出力ポート */
    private RtcOutPort<TimedBoolean> resultIn  = new RtcOutPort<>("result", CorbaObj.newTimedBoolean());

    /**
     * コンストラクタ。
     * @param manager RTCマネージャ。
     *
     */
	public ServerCommunicationImpl(Manager manager) {
		super(manager);

	}

	/**
     * 初期化処理。
     *
     * @return リターンコード。
     */
	@Override
	protected ReturnCode_t onRtcInitialize() {

		return super.onRtcInitialize();
	}

	/**
     * 周期的な処理。
     */
	@Override
	protected ReturnCode_t onRtcExecute(int ec_id) {

		// 画像が入力ポートにある場合はサーバーに画像を渡す
        if (!cameraImageIn.isNew() || cameraImageIn.isEmpty()) {
        	CameraImage image = cameraImageIn.readData();
        	
        	// サーバー通信
        	   // TO DO
        	
        	
        	//
        	
        	
        	// 結果を出力ポートにセット
        	
        }

		return super.onRtcExecute(ec_id);
	}

	/**
     * メイン処理。
     * @param args 起動引数。
     */
    public static void main(String[] args) {

        RtcStarter.init(args)
                  .setConfig(new ServerCommunicationConfig())
                  .start(ServerCommunicationImpl.class);
    }

}

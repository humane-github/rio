package jp.co.humane.rtc.facedetector;

import RTC.CameraImage;
import RTC.ReturnCode_t;
import RTC.TimedLong;
import jp.co.humane.opencvlib.OpenCVLib;
import jp.co.humane.rtc.common.component.DataFlowStatefullComponent;
import jp.co.humane.rtc.common.port.RtcInPort;
import jp.co.humane.rtc.common.port.RtcOutPort;
import jp.co.humane.rtc.common.starter.RtcStarter;
import jp.co.humane.rtc.common.util.CorbaObj;
import jp.co.humane.rtc.facedetector.processor.DetectingProcesssor;
import jp.co.humane.rtc.facedetector.processor.WaitWakeupProcessor;
import jp.go.aist.rtm.RTC.Manager;

/**
 * カメラ映像を元に人の顔を検出し、検出数を出力する。
 * @author terada.
 */
public class FaceDetectorImpl extends DataFlowStatefullComponent<FaceDetectorConfig> {

    /** 状態を表すenum */
    public enum State {

        /** 待機中 */
        WAIT_WAKEUP,

        /** 検出中 */
        DETECTING;
    };

    /** カメラ映像の入力ポート */
    private RtcInPort<CameraImage> cameraImageIn = new RtcInPort<>("CameraImage", CorbaObj.newCameraImage());

    /** 顔検出の開始指示入力ポート */
    private RtcInPort<TimedLong> wakeupIn = new RtcInPort<>("Wakeup", CorbaObj.newTimedLong());

    /** カメラ映像の送信ポート */
    protected RtcOutPort<CameraImage> cameraImageOut = new RtcOutPort<>("CameraImage", CorbaObj.newCameraImage());

    /** OpenCVの読み込み */
    static {
        OpenCVLib.LoadDLL();
    }

    /**
     * コンストラクタ。
     * @param manager RTCマネージャ。
     */
	public FaceDetectorImpl(Manager manager) {
        super(manager);
    }

    /**
     * 初期化処理。
     * 各ポートの追加を行う。
     *
     * @return リターンコード。
     */
    @Override
    protected ReturnCode_t onRtcInitialize() {

        // 状態の関連情報を設定
        setStateRelation();

        return super.onRtcInitialize();
    }

    /**
     * 状態と処理、状態と処理結果と遷移先の関連を設定する。
     */
    private void setStateRelation() {

        // 状態とそれに対応する処理を登録
    	if (config.getWakeupFlg()){
    		// wakeupを使用する場合
    		stateProcMap.put(State.WAIT_WAKEUP, new WaitWakeupProcessor(wakeupIn));
    	}
        stateProcMap.put(State.DETECTING, new DetectingProcesssor(cameraImageIn, cameraImageOut, config));

        // 現在状態、処理結果、遷移先状態の組み合わせを登録
        addStateMoveMap(State.WAIT_WAKEUP, WaitWakeupProcessor.Result.NOT_RECEIVE, State.WAIT_WAKEUP);
        addStateMoveMap(State.WAIT_WAKEUP, WaitWakeupProcessor.Result.RECEIVE,     State.DETECTING);
        addStateMoveMap(State.DETECTING,   DetectingProcesssor.Result.DETECT,       State.WAIT_WAKEUP);
        addStateMoveMap(State.DETECTING,   DetectingProcesssor.Result.NOT_DETECT,  State.DETECTING);
        addStateMoveMap(State.DETECTING,   DetectingProcesssor.Result.TIMEOUT,      State.WAIT_WAKEUP);

        // 初期状態を待機中に設定
        this.state = State.WAIT_WAKEUP;

    }

    /**
     * メイン処理。
     * @param args 起動引数。
     */
    public static void main(String[] args) {

        RtcStarter.init(args)
                  .setConfig(new FaceDetectorConfig())
                  .start(FaceDetectorImpl.class);
    }
}


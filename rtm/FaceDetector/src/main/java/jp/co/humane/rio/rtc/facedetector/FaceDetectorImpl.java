package jp.co.humane.rio.rtc.facedetector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.Objdetect;

import RTC.CameraImage;
import RTC.ReturnCode_t;
import jp.co.humane.opencvlib.MatViewer;
import jp.co.humane.opencvlib.OpenCVLib;
import jp.co.humane.rtc.common.component.DataFlowComponent;
import jp.co.humane.rtc.common.port.RtcInPort;
import jp.co.humane.rtc.common.port.RtcOutPort;
import jp.co.humane.rtc.common.starter.RtcStarter;
import jp.co.humane.rtc.common.util.CorbaObj;
import jp.go.aist.rtm.RTC.Manager;

/**
 * カメラ映像を元に人の顔を検出し、検出数を出力する。
 * @author terada.
 */
public class FaceDetectorImpl extends DataFlowComponent<FaceDetectorConfig> {

    /** カメラ映像の入力ポート */
    private RtcInPort<CameraImage> cameraImageIn = new RtcInPort<>("CameraImageIn", CorbaObj.newCameraImage());

    /** カメラ映像の送信ポート */
    protected RtcOutPort<CameraImage> cameraImageOut = new RtcOutPort<>("CameraImageOut", CorbaObj.newCameraImage());

    /** カスケード分類器リスト */
    private List<CascadeClassifier> cascadeClassifierList = new ArrayList<>();

    /** イメージ確認用ビューア */
    private MatViewer matViewer = new MatViewer("FaceDetector");

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
     * アクティブ時の処理。
     * カスケードファイルの読み込みを行う。
     *
     * @param ec_id ExecutionContext ID.
     * @return リターンコード。
     */
    @Override
    protected ReturnCode_t onRtcActivated(int ec_id) {

        // Cascadeファイルのロード
        boolean isSuccess = loadCascadeConfigFile();
        if (!isSuccess) {
            logger.error("カスケードファイルの読み込みに失敗しました。");
            return ReturnCode_t.RTC_ERROR;
        }
        return ReturnCode_t.RTC_OK;
    }

    /**
     * 顔の特徴量を定義したカスケードファイルを読み込む。
     * @return 処理結果。
     */
    private boolean loadCascadeConfigFile() {

        // 各ファイルに対して読み込み処理を行う
        List<String> paths = Arrays.asList(config.getCascadePath1(), config.getCascadePath2(), config.getCascadePath3());
        for (String path : paths) {

            // パスが指定されていない場合は次のファイルに移る
            if (StringUtils.isEmpty(path)) {
                continue;
            }

            // 読み込みに失敗した場合は次のファイルに移る
            CascadeClassifier cc = new CascadeClassifier();
            boolean canLoaded = cc.load(path);
            if (!canLoaded) {
                logger.warn("指定されたカスケードファイルの読み込みに失敗しました。パス=[" + path + "]");
                continue;
            }

            // 読み込んだ情報をリストに格納
            cascadeClassifierList.add(cc);
        }

        // 読み込めているかどうかを返却する
        return (0 < cascadeClassifierList.size());
    }

    /**
     * 周期的な処理。
     */
    @Override
    protected ReturnCode_t onRtcExecute(int ec_id) {

        // 画像が入力ポートにない場合は処理終了
        if (!cameraImageIn.isNew() || cameraImageIn.isEmpty()) {
            return ReturnCode_t.RTC_OK;
        }

        // 画像データがnullの場合は処理終了
        CameraImage image = cameraImageIn.readData();
        if (null == image) {
            return ReturnCode_t.RTC_OK;
        }

        // 顔の検出処理を行い検出しなかった場合は処理終了
        boolean isDetected = doDetectFace(image);
        if (!isDetected) {
            return ReturnCode_t.RTC_OK;
        }

        // 検出した場合は出力ポートにimage画像を送信
        cameraImageOut.write(image);
        logger.info("顔を検出し、出力ポートにカメラ画像を書き込みました。");
        return ReturnCode_t.RTC_OK;
    }

    /**
     * 画像情報をもとに顔の検出処理を行う
     * @param image 画像情報。
     * @return 検出結果。true:検出、false:非検出
     */
    private boolean doDetectFace(CameraImage image) {

        // 縮小画像のサイズを取得
        int smallWidth = (int) (image.width / config.getZoomOutRatio());
        int smallHeight = (int) (image.height / config.getZoomOutRatio());

        // 画像を格納するMatを作成
        // CV_8UC1：8ビット + unsigned(0～255)、チャネル数1(RGBは3チャネル) ⇒ 白黒表示
        Mat cameraMat = new Mat(image.height, image.width, image.bpp);
        Mat grayMat   = new Mat(image.height, image.width, CvType.CV_8UC1);
        Mat smallMat  = new Mat(smallHeight, smallWidth, CvType.CV_8UC1);

        // カメラの映像をMatに格納後、グレースケールに変換
        cameraMat.put(0, 0, image.pixels);
        Imgproc.cvtColor(cameraMat, grayMat, Imgproc.COLOR_BGR2GRAY);

        // 画像を縮小化する
        Imgproc.resize(grayMat, smallMat, smallMat.size(), 0, 0, Imgproc.INTER_LINEAR);

        // 各特徴量を使って顔の検出処理を行う
        int detectedCount = 0;
        int faceAreaMax = 0;
        List<MatOfRect> facesList = new ArrayList<>();
        for (CascadeClassifier cc : cascadeClassifierList) {

            // 検出処理を実行
            MatOfRect faces = new MatOfRect();
            cc.detectMultiScale(smallMat, faces, 1.1, 2, Objdetect.CASCADE_SCALE_IMAGE , new Size(30,30), new Size());

            // 検出できた場合は検出数を増やし、検出範囲をリストに格納
            if (0 < faces.toArray().length) {
                detectedCount++;
                facesList.add(faces);

                for (Rect rect :faces.toArray()){
                    int faceArea = rect.width * rect.height;

                    if (faceAreaMax < faceArea){
                        faceAreaMax = faceArea;
                    }
                }
            }
        }

        // ビューアに画像を表示する
        if (config.getEnableViewer()) {
            updateViewer(smallMat, facesList);
        }

        // 検出数が指定回数未満の場合は未検出とする
        if (detectedCount < config.getDetectThreshold()) {
            return false;
        }

        // 一番面積の大きい顔が画像全体に占める割合が一定数を超えていない場合は非検出とする
        int imageArea = smallMat.width() * smallMat.height();
        if (faceAreaMax < imageArea * config.getDetectFaceRatio()){
          return false;
        }

        return true;
    }

    /**
     * 検出結果をビューアに表示する。
     * @param smallMat  画像データ。
     * @param facesList 検出範囲データ。
     */
    private void updateViewer(Mat smallMat, List<MatOfRect> facesList) {

        // 暫定的に最大8個まで表示
        Iterator<Scalar> it =
                Arrays.asList(new Scalar(255,   0,   0), new Scalar(  0, 255,   0), new Scalar(  0,   0, 255),
                               new Scalar(255, 255,   0), new Scalar(255,   0, 255), new Scalar(  0, 255, 255),
                               new Scalar(  0,   0,   0), new Scalar(255, 255, 255)).iterator();

        if (8 < facesList.size()) {
            facesList = facesList.subList(0, 7);
        }

        for (MatOfRect faces : facesList) {
            Scalar scalar = it.next();
            for (Rect face : faces.toList()) {

                Core.rectangle(
                        smallMat,
                        new Point(face.x, face.y),
                        new Point(face.x + face.width, face.y + face.height),
                        scalar);
            }
        }

        matViewer.updateImage(smallMat);
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


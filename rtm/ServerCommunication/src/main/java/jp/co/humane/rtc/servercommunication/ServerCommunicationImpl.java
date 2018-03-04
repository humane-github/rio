package jp.co.humane.rtc.servercommunication;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.opencv.core.Mat;

import com.fasterxml.jackson.databind.ObjectMapper;

import RTC.CameraImage;
import RTC.ReturnCode_t;
import RTC.Time;
import RTC.TimedBoolean;
import jp.co.humane.opencvlib.MatViewer;
import jp.co.humane.opencvlib.OpenCVLib;
import jp.co.humane.rtc.common.component.DataFlowComponent;
import jp.co.humane.rtc.common.port.RtcInPort;
import jp.co.humane.rtc.common.port.RtcOutPort;
import jp.co.humane.rtc.common.starter.RtcStarter;
import jp.co.humane.rtc.common.util.CorbaObj;
import jp.co.humane.rtc.util.ImageUtils;
import jp.go.aist.rtm.RTC.Manager;

/**
 * カメラ画像をサーバーに送信し、サーバーから認証結果を受け取る。
 * @author kawano.
 */
public class ServerCommunicationImpl extends DataFlowComponent<ServerCommunicationConfig> {

    /** ステータスコード：正常終了 */
    private static final int STATUS_CODE_SUCCESS = 200;

    /** 処理結果コードのキー */
    private static final String KEY_RESULT_CODE = "resultCode";

    /** 処理結果コード：正常 */
    private static final String RESULT_CODE_SUCCESS = "001";

    /** ObjectMapper */
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /** カメラ映像の入力ポート */
    private RtcInPort<CameraImage> cameraImageIn = new RtcInPort<>("CameraImage", CorbaObj.newCameraImage());

    /** 認証結果の出力ポート */
    private RtcOutPort<TimedBoolean> resultOut = new RtcOutPort<>("result", CorbaObj.newTimedBoolean());

    /** イメージ確認用ビューア */
    private MatViewer matViewer = new MatViewer("ServerCommunication");

    /** OpenCVの読み込み */
    static {
        OpenCVLib.LoadDLL();
    }

    /**
     * コンストラクタ。
     * @param manager RTCマネージャ。
     *
     */
    public ServerCommunicationImpl(Manager manager) {
        super(manager);
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

        // ビューアにイメージを表示
        Mat mat = ImageUtils.cameraImage2Mat(image);
        if (config.getEnableViewer()) {
            matViewer.updateImage(mat);
        }

        // 顔画像の認証処理を行う
        boolean isAuthednticated = doAuthenticate(mat);
        if (isAuthednticated) {
            logger.info("認証OKとなったため開錠します。");
            resultOut.write(new TimedBoolean(new Time(), true));
        } else {
            logger.info("認証NGでした。");
        }
        return ReturnCode_t.RTC_OK;
    }

    /**
     * カメラの画像を使って認証を行いその結果を返す。
     * @param mat カメラの画像。
     * @return 認証結果。
     */
    private boolean doAuthenticate(Mat mat) {

        // Mat画像をInputStreamに変換
        InputStream is = ImageUtils.mat2InputStream(mat, "png");

        // 認証用のHTTPリクエストデータを作成
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(config.getServerUri());
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addTextBody("cameraId", config.getCameraId(), ContentType.TEXT_PLAIN );
        builder.addBinaryBody("image", is, ContentType.APPLICATION_OCTET_STREAM, "tryAuth.png");
        post.setEntity(builder.build());

        // 認証用HTTPリクエストを送信
        CloseableHttpResponse response = null;
        try {
            response = client.execute(post);
        } catch (IOException ex) {
            throw new RuntimeException("認証用リクエストに失敗しました。", ex);
        }

        // 正常終了していない場合は認証NGとする
        int sc = response.getStatusLine().getStatusCode();
        if (sc != STATUS_CODE_SUCCESS) {
            logger.error("認証リクエストに失敗しました。ステータスコード：" + sc);
            return false;
        }

        // 処理結果コードが正常ではない場合は認証NGとする
        HttpEntity entity = response.getEntity();
        // String resString = EntityUtils.toString(entity, HTTP_CHARSET);
        Map<String, String> map = null;
        try {
            map = MAPPER.readValue(entity.getContent(), Map.class);
        } catch (IOException ex) {
            throw new RuntimeException("認証結果の読み取りに失敗しました。", ex);
        }
        if (!StringUtils.equals(map.get(KEY_RESULT_CODE), RESULT_CODE_SUCCESS)) {
            logger.error("認証リクエストに失敗しました。処理結果コード：" + map.get(KEY_RESULT_CODE));
            return false;
        }

        // 認証成功を返す
        return true;
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

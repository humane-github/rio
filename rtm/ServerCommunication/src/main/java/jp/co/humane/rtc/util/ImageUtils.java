package jp.co.humane.rtc.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import javax.imageio.ImageIO;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.highgui.Highgui;

import RTC.CameraImage;

/**
 * 画像処理のユーティリティクラス。
 * @author terada
 *
 */
public class ImageUtils {

    /**
     * CameraImageをMatに変換する。
     * @param image CameraImageインスタンス。
     * @return Matインスタンス。
     */
    public static Mat cameraImage2Mat(CameraImage image) {

        // 画像を格納するMatを作成
        // CV_8UC1：8ビット + unsigned(0～255)、チャネル数1(RGBは3チャネル) ⇒ 白黒表示
        Mat cameraMat = new Mat(image.height, image.width, image.bpp);

        // カメラの映像をMatに格納
        cameraMat.put(0, 0, image.pixels);

        return cameraMat;
    }

    /**
     * Mat画像をInputStreamに変換する。
     * @param mat Matインスタンス。
     * @param ext 変換後の画像フォーマットの拡張子。("bmp"、"jpg"、"png"など)
     * @return InputStreamインスタンス。
     */
    public static InputStream mat2InputStream(Mat mat, String ext) {

        // Matの画像をバイト形式に変換
        MatOfByte mem = new MatOfByte();
        Highgui.imencode("." + ext, mat, mem);

        // バイトデータをイメージに変換し、メモリ上に出力させる
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            Image image = ImageIO.read(new ByteArrayInputStream(mem.toArray()));
            ImageIO.write((BufferedImage)image, ext, os);
        } catch (IOException ex) {
            throw new RuntimeException("カメライメージの読み取りに失敗しました。", ex);
        }

        // 出力された情報から入力ストリームを取り出す
        InputStream is = new ByteArrayInputStream(os.toByteArray());

        return is;
    }

    // 確認用
    public static void write(InputStream is) {
        File file = new File("aaa.png");
        try {
            Files.copy(is, file.toPath());
        } catch (IOException ex) {
            ;
        }
    }

}

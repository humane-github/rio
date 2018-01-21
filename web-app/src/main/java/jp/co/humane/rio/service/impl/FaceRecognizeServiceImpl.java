package jp.co.humane.rio.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jp.co.humane.rio.common.consts.LogId;
import jp.co.humane.rio.common.exception.ApplicationException;
import jp.co.humane.rio.service.FaceRecognizeService;

/**
 * 顔認識を行うサービス。
 * @author terada
 *
 */
@Service
public class FaceRecognizeServiceImpl implements FaceRecognizeService {

    /** ロガー */
    private static final Logger LOGGER = LoggerFactory.getLogger(FaceRecognizeServiceImpl.class);

    /** カウンタ */
    private static final AtomicLong counter = new AtomicLong(0);

    /** 終了コード：正常終了 */
    private static final int EXIT_CODE_SUCCESS = 0;

    /** openbrスクリプトの実行ファイル */
    @Value("${rio.openbr.exe-path}")
    private String exePath = null;

    /** openbr実行スクリプト */
    @Value("${rio.openbr.script-path}")
    private String scriptPath = null;

    /** 認識対象の画像ファイルの配置ディレクトリ */
    @Value("${rio.openbr.in-img-dir}")
    private String inImgDir = null;

    /** 認識結果ファイルの配置ディレクトリ */
    @Value("${rio.openbr.out-csv-dir}")
    private String outCsvDir = null;

    /** OpenBR実施のタイムアウト */
    @Value("${rio.openbr.timeout}")
    private long timeout = 0;

    /**
     * イメージデータに対して顔認識を行う。
     *
     * @param image イメージデータ。
     * @return 「key=ファイルパス、value=マッチ率」のマップ。
     */
    @Override
    public Map<String, Double> recognize(MultipartFile image) {

        Map<String, Double> recogResultMap = null;

        // 画像ファイル、CSVファイルのパスを取得
        String name = getUniqueName();
        Path imgPath = Paths.get(inImgDir, name + ".png");
        Path csvPath = Paths.get(outCsvDir, name + ".csv");

        try {
            // 画像ファイルを保存
            try {
                Files.write(imgPath, image.getBytes());
            } catch (IOException ex) {
                LOGGER.error(LogId.E_AUTH_IMG_FILE_SAVE_ERR_BR, imgPath.toAbsolutePath().toString());
                throw new ApplicationException(ex);
            }

            // openbrを実行
            runOpenBrScript(imgPath, csvPath);

            // openbrが出力したCSVファイルをマップ形式に変換
            recogResultMap = convertCsv(csvPath);

        } finally {

            // 作成したファイルがあれば削除する
            File img = imgPath.toFile();
            File csv = csvPath.toFile();
            if (img.isFile()) {
                img.delete();
            }
            if (csv.isFile()) {
                csv.delete();
            }
        }

        return recogResultMap;
    }

    /**
     * 異なるリクエストでファイルが被らないような名前を取得。
     *
     * @return 一意となる名前。
     */
    private String getUniqueName() {

        // インクリメントした値を取得
        long cnt = counter.incrementAndGet();

        // 36進数(数値＋アルファベット)でカウンタを文字列に変換
        return Long.toString(cnt, 36);
    }

    /**
     * OpenBR実行用スクリプトを実行する。
     *
     * @param imgPath 画像ファイルパス。
     * @param csvPath CSVファイルパス。
     */
    private void runOpenBrScript(Path imgPath, Path csvPath) {

        // プロセス起動コマンドを設定
        ProcessBuilder pb = new ProcessBuilder(exePath,
                                               Paths.get(scriptPath).toAbsolutePath().toString(),
                                               imgPath.getFileName().toString(),
                                               csvPath.getFileName().toString());

        // 途中で止まらないように出力先をまとめる
        pb.redirectErrorStream();

//        // test
//        try {
//            Process proc = pb.start();
//            proc.waitFor();
//            String s = "";
//            String str;
//            BufferedReader brstd = new BufferedReader(new InputStreamReader(proc.getInputStream()));
//            while((str = brstd.readLine()) != null) {
//                s += str + "\n";
//            }
//            brstd.close();
//        } catch (IOException | InterruptedException e1) {
//            // TODO 自動生成された catch ブロック
//            e1.printStackTrace();
//        }

        // プロセスを実行し完了まで待機
        Process p = null;
        try {
            p = pb.start();
            p.waitFor(timeout, TimeUnit.SECONDS);

            // 正常終了でない場合はエラーとする
            if (EXIT_CODE_SUCCESS != p.exitValue()) {
                LOGGER.error(LogId.E_AUTH_ERROR_EXIT, p.exitValue(), getOutputText(p));
                throw new ApplicationException("openbr異常終了");
            }

        } catch (InterruptedException e) {
            LOGGER.error(LogId.E_AUTH_BR_TIMEOUT, imgPath.toAbsolutePath().toString(), timeout, getOutputText(p));
            throw new ApplicationException("openbrタイムアウトエラー", e);
        } catch (IOException e) {
            throw new ApplicationException("openbr処理失敗", e);
        }
    }

    /**
     * プロセスが出力したテキストを取得する。
     * @param p プロセス。
     * @return プロセスが出力したテキスト。
     */
    private String getOutputText(Process p) {

        // プロセスがない場合は空文字を返す
        if (null == p) {
            return "";
        }

        // 入力ストリームから文字を取得
        StringBuilder sb = new StringBuilder();
        InputStreamReader isr = new InputStreamReader(p.getInputStream());
        try (BufferedReader br = new BufferedReader(isr)) {
            String s = null;
            while((s = br.readLine()) != null) {
                sb.append(s + "\n");
            }
        } catch (IOException e) {
            throw new ApplicationException("プロセス情報の読み取りに失敗", e);
        }

        return sb.toString();
    }

    /**
     * openbrが出力したCSVファイルをマップ形式に変換する。
     *
     * @param csvPath CSVファイルのパス。
     * @return 「key=ファイルパス、value=マッチ率」のマップ。
     */
    private Map<String, Double> convertCsv(Path csvPath) {

        // ファイルを読み込む
        List<String> lines = null;
        try {
            lines = Files.readAllLines(csvPath);
        } catch (IOException e) {
            LOGGER.error(LogId.E_AUTH_BR_CSV_NOT_READ, csvPath.toAbsolutePath().toString());
            throw new ApplicationException("認証結果CSVファイル読み取りエラー", e);
        }

        // 1行目を認証用全イメージ、2行目をマッチ率として取得
        List<String> allImgList = Stream.of(StringUtils.split(lines.get(0), ","))
                                         .collect(Collectors.toCollection(LinkedList::new));
        List<String> matchRateStrList = Stream.of(StringUtils.split(lines.get(1), ","))
                                                .collect(Collectors.toCollection(LinkedList::new));

        // 最初の項目はマッチ率ではないので削除
        allImgList.remove(0);
        matchRateStrList.remove(0);

        // マッチ率をdouble型に変換
        List<Double> matchRateList = matchRateStrList.stream().map(Double::valueOf).collect(Collectors.toList());

        // 順番にマップに格納する
        Map<String, Double> matchRateMap = new HashMap<>();
        int size = allImgList.size();
        for (int i = 0; i < size; i++) {
            matchRateMap.put(allImgList.get(i), matchRateList.get(i));
        }

        return matchRateMap;
    }

}

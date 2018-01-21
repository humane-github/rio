package jp.co.humane.rio.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

/**
 * 顔認識を行うサービス。
 * @author terada
 *
 */
public interface FaceRecognizeService {

    /**
     * イメージデータに対して顔認識を行う。
     *
     * @param image    イメージデータ。
     * @return 「key=ファイルパス、value=マッチ率」のマップ。
     */
    Map<String, Double> recognize(MultipartFile image);

}

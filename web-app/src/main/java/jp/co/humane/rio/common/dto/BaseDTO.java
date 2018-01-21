package jp.co.humane.rio.common.dto;

import java.text.SimpleDateFormat;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * DTOのスーパークラス。
 * @author terada
 *
 */
public class BaseDTO {

    /** JSON形式へのマッパー */
    private static ObjectMapper mapper = new ObjectMapper();

    // 日付は「yyyy/MM/dd HH:mm:ss」とする
    static {
        mapper.setDateFormat(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"));
    }

    /*
     * 文字列形式に変換する。
     */
    @Override
    public String toString() {
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return ReflectionToStringBuilder.toString(this);
        }
    }

}

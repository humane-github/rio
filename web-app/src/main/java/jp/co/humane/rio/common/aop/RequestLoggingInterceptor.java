package jp.co.humane.rio.common.aop;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jp.co.humane.rio.common.consts.LogId;
import jp.co.humane.rio.common.dto.BaseDTO;
import jp.co.humane.rio.common.exception.ApplicationException;

/**
 * リクエスト情報をログに出力する。
 * @author terada
 *
 */
@Aspect
@Component
public class RequestLoggingInterceptor {

    /** ロガー */
    private static final Logger log = LoggerFactory.getLogger(RequestLoggingInterceptor.class);

    /** JSON変換 */
    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * ResponseBodyアノテーションが付与されているメソッドに対してログ出力処理を追加する。
     * @param joinPoint joinPoint.
     */
    @Before("within(jp.co.humane.rio.api..*) && @annotation(org.springframework.web.bind.annotation.ResponseBody)")
    public void before(JoinPoint joinPoint) {

        // ログ出力はデバッグレベルの場合に実行
        if (!log.isDebugEnabled()) {
            return;
        }

        // ログを出力
        log.debug(LogId.D_CMN_REQ, getRequestMessage(joinPoint));
    }

    /**
     * ResponseBodyアノテーションが付与されているメソッドに対してログ出力処理を追加する。
     * 例外発生時にログを出力する。
     *
     * @param joinPoint joinPoint.
     */
    @AfterThrowing("within(jp.co.humane.rio.api..*) && @annotation(org.springframework.web.bind.annotation.ResponseBody)")
    public void afterThrowing(JoinPoint joinPoint) {
        // ログを出力
        log.info(LogId.I_CMN_ERR_REQ, getRequestMessage(joinPoint));
    }

    /**
     * JoinPointからログに出力するリクエストメッセージを生成する。
     *
     * @param joinPoint joinPoint.
     * @return リクエストメッセージ。
     */
    private String getRequestMessage(JoinPoint joinPoint) {

        // 呼び出されたクラス名とメソッド名を取得
        String className = joinPoint.getTarget().getClass().getCanonicalName();
        String methodName = joinPoint.getSignature().getName();

        // 引数情報をJSON形式で取得
        List<String> args = Stream.of(joinPoint.getArgs()).filter(obj -> {
            return !(obj instanceof BindingResult);
        }).map(obj -> {
            if (obj instanceof BaseDTO) {
                return obj.toString();
            }
            try {
                return mapper.writeValueAsString(obj);
            } catch (JsonProcessingException e) {
                throw new ApplicationException(e);
            }
        }).collect(Collectors.toList());

        // 引数が複数存在する場合は配列形式で出力する
        String argString = null;
        if (1 == args.size()) {
            argString = args.get(0);
        } else {
            argString = args.stream().collect(Collectors.joining(",", "[", "]"));
        }

        // 『class#method() : {リクエストデータ}』の形式を返す
        return className + "#" + methodName + "() : " + argString;
    }

}

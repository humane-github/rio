package jp.co.humane.rio.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import jp.co.humane.rio.common.consts.LogId;
import jp.co.humane.rio.common.exception.ApplicationException;

/**
 * 例外処理をハンドリングする。
 * @author terada
 *
 */
@ControllerAdvice
public class ExceptionResolver {

    /** ロガー */
    private static final Logger log = LoggerFactory.getLogger(ExceptionResolver.class);

    /**
     * ApplicationException例外が発生した場合は500(サーバエラー)を返す。
     * @param ex 例外。
     */
    @ExceptionHandler(ApplicationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void onServerError(ApplicationException ex) {
        log.error(LogId.E_CMN_APP_ERR, ex);
    }

    /**
     * 上記以外の例外が発生した場合は500(サーバエラー)を返す。
     * @param ex 例外。
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void onError(Exception ex) {
        log.error(LogId.E_CMN_OTHER_ERR, ex);
    }
}

package jp.co.humane.rio.common.logger;

import java.util.concurrent.atomic.AtomicInteger;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

/**
 * loggerにスレッドIDを表示するための変換クラス。
 * @see https://stackoverflow.com/questions/7000678/logging-parallel-threads-in-logback
 * @author terada
 *
 */
public class ThreadIdConverter extends ClassicConverter {

    /** スレッドID代わりのID値 */
    private static AtomicInteger id = new AtomicInteger(0);

    /** スレッドごとにスレッドIDを保持 */
    private static final ThreadLocal<String> threadId = new ThreadLocal<String>() {

        /**
         * スレッド生成時にスレッドIDを生成。
         */
        @Override
        protected String initialValue() {
            int nextId = id.incrementAndGet();
            return String.format("%06d", nextId);
        }
    };

    /**
     * 指定記号に対応してスレッドIDの文字列を生成する
     * @param event イベント。
     * @return スレッドID。
     */
    @Override
    public String convert(ILoggingEvent event) {
        return threadId.get();
    }
}

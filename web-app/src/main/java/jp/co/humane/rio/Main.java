package jp.co.humane.rio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.ConfigurableEnvironment;

import jp.co.humane.rio.common.consts.LogId;

/**
 * Spring Boot起動クラス。
 * @author terada
 *
 */
@EnableAutoConfiguration
@ComponentScan("jp.co.humane.rio")
public class Main {

    /** ロガー */
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    /** バージョンのキー値 */
    private static final String VERSION_KEY = "rio.version";

    /** プロファイルのキー値 */
    private static final String PRIFILE_KEY = "spring.profiles";

    /**
     * Spring Bootを起動する。
     * @param args 起動引数。
     */
    public static void main(String[] args) {

        // アプリケーションを起動
        ConfigurableApplicationContext context = null;
        context = SpringApplication.run(Main.class, args);

        // 起動ログを出力
        ConfigurableEnvironment env = context.getEnvironment();
        String version = env.getProperty(VERSION_KEY);
        String profile = env.getProperty(PRIFILE_KEY);
        log.info(LogId.I_CMN_START, version, profile);
    }
}

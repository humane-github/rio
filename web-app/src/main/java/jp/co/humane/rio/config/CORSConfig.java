package jp.co.humane.rio.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * CORS設定を行う。
 * @author terada
 *
 */
@Configuration
public class CORSConfig {

    /** 開発用プロファイル名 */
    private static final String DEVELOP_PROFILE = "dev";

    /** プロファイル */
    @Value("${spring.profiles}")
    private String profile = null;

    /**
     * CORS設定を行う。
     * @return CORS許可情報。
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {

        // CORSを常に許可
        return new AlwaysPermitCORS();
    }

    /**
     * 常にCORS設定を許可にする。
     * @author terada
     *
     */
    @SuppressWarnings("unused")
    private class AlwaysPermitCORS extends WebMvcConfigurerAdapter {

        /**
         * デフォルトコンストラクタ。
         */
        public AlwaysPermitCORS() {
            super();
        }

        /**
         * CORS設定
         */
        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**");
        }
    }

    /**
     * 開発環境だけCORSを許可する。
     * @author terada
     *
     */
    @SuppressWarnings("unused")
    private class DevPermitCORS extends WebMvcConfigurerAdapter {

        /**
         * デフォルトコンストラクタ。
         */
        private DevPermitCORS() {
            super();
        }

        /**
         * CORS設定
         */
        @Override
        public void addCorsMappings(CorsRegistry registry) {

            // 開発環境かどうかを取得
            boolean isDev = StringUtils.equals(DEVELOP_PROFILE, profile);

            // 開発環境の場合はCORSを許可
            if (isDev) {
                registry.addMapping("/**");
            }
        }
    }
}

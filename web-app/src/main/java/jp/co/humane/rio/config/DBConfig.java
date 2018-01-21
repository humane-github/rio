package jp.co.humane.rio.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * DB接続設定。
 * @author terada
 *
 */
@Configuration
@MapperScan(basePackages = "jp.co.humane.rio.orm.dao")
public class DBConfig {

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {

        // データソースを設定
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setFailFast(true);

        // アンダースコアをCamelCaseに変換
        SqlSessionFactory factory = factoryBean.getObject();
        factory.getConfiguration().setMapUnderscoreToCamelCase(true);

        return factory;
    }
}

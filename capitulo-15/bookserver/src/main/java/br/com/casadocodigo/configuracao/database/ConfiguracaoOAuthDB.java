package br.com.casadocodigo.configuracao.database;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class ConfiguracaoOAuthDB {

    @Bean(name = "dsOauth")
    @ConfigurationProperties(prefix = "spring.ds-oauth")
    public DataSource oauthDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "dsBookserver")
    @ConfigurationProperties(prefix = "spring.ds-bookserver")
    public DataSource bookserverDataSource() {
        return DataSourceBuilder.create().build();
    }

}

package ua.bondar.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Value("${datasource.driver-class-name}")
    private String DRIVER_CLASS_NAME;

    @Value("${datasource.database.url}")
    private String URL_DATABASE;

    @Value("${datasource.credential.username}")
    private String USERNAME;

    @Value("${datasource.credential.password}")
    private String PASSWORD;


    @Bean
    public DataSource getDataSource() {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(DRIVER_CLASS_NAME);
        dataSourceBuilder.url(URL_DATABASE);
        dataSourceBuilder.username(USERNAME);
        dataSourceBuilder.password(PASSWORD);
        return dataSourceBuilder.build();
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(getDataSource());
    }

}

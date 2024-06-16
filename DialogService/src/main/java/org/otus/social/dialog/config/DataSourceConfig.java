package org.otus.social.dialog.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Value("${spring.datasource.master.url}")
    private String masterDbUrl;

    @Value("${spring.datasource.master.username}")
    private String masterDbUserName;

    @Value("${spring.datasource.master.password}")
    private String masterDbPassword;

    @Value("${spring.datasource.slave.url}")
    private String slaveDbUrl;

    @Value("${spring.datasource.slave.username}")
    private String slaveDbUserName;

    @Value("${spring.datasource.slave.password}")
    private String slaveDbPassword;


    @Primary
    @Bean(name = "masterDataSource")
    public DataSource masterDataSource() {
        HikariConfig masterConfig = new HikariConfig();
        masterConfig.setPoolName(DataSourceConfig.class.getName() + "_master");
        masterConfig.setJdbcUrl(masterDbUrl);
        masterConfig.setUsername(masterDbUserName);
        masterConfig.setPassword(masterDbPassword);
        return new HikariDataSource(masterConfig);
    }

    @Bean(name = "slaveDataSource")
    public DataSource slaveDataSource() {
        HikariConfig slaveConfig = new HikariConfig();
        slaveConfig.setPoolName(DataSourceConfig.class.getName() + "_slave");
        slaveConfig.setJdbcUrl(slaveDbUrl);
        slaveConfig.setUsername(slaveDbUserName);
        slaveConfig.setPassword(slaveDbPassword);
        return new HikariDataSource(slaveConfig);
    }
}
package otus.social.config;

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

    @Primary
    @Bean(name = "masterDataSource")
    public DataSource masterDataSource() {
        final HikariConfig masterConfig = new HikariConfig();
        masterConfig.setPoolName(DataSourceConfig.class.getName() + "_master");
        masterConfig.setJdbcUrl(masterDbUrl);
        masterConfig.setUsername(masterDbUserName);
        masterConfig.setPassword(masterDbPassword);
        return new HikariDataSource(masterConfig);
    }

}
package com.sphereex.jmh.sursen.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.SneakyThrows;
import org.apache.shardingsphere.driver.api.yaml.YamlShardingSphereDataSourceFactory;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileReader;
import java.util.Properties;

public class DatasourceUtil {

    @SneakyThrows
    public static DataSource createDataSource(String configurationFile) {
        String datasourceType = System.getProperty("datasourceType");
        if ("mysql".equals(datasourceType)) {
            Properties dataSourceProperties = new Properties();
            dataSourceProperties.load(new FileReader(configurationFile));
            HikariConfig hikariConfig = new HikariConfig();
            hikariConfig.setJdbcUrl(dataSourceProperties.getProperty("jdbcUrl"));
            hikariConfig.setUsername(dataSourceProperties.getProperty("username"));
            hikariConfig.setPassword(dataSourceProperties.getProperty("password"));
            return new HikariDataSource(hikariConfig);
        }
        return YamlShardingSphereDataSourceFactory.createDataSource(new File(configurationFile));
    }
}

package com.sphereex.jmh.shardingsphere5;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.apache.shardingsphere.driver.api.yaml.YamlShardingSphereDataSourceFactory;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileReader;
import java.util.Properties;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ShardingSpheres {
    
    @SneakyThrows
    public static DataSource createDataSource() {
        String configurationFile = System.getProperty("shardingsphere.configurationFile");
        String datasourceType = System.getProperty("datasourceType");
        if ("mysql".equals(datasourceType)) {
            Properties dataSourceProperties = new Properties();
            dataSourceProperties.load(new FileReader(configurationFile));
            HikariConfig hikariConfig = new HikariConfig();
            hikariConfig.setJdbcUrl(dataSourceProperties.getProperty("jdbcUrl"));
            hikariConfig.setUsername(dataSourceProperties.getProperty("username"));
            hikariConfig.setPassword(dataSourceProperties.getProperty("password"));
            hikariConfig.setMaximumPoolSize(512);
            hikariConfig.setMinimumIdle(1);
            hikariConfig.setIdleTimeout(60000);
            hikariConfig.setMaxLifetime(1800000);
            hikariConfig.setConnectionTimeout(30000);
            return new HikariDataSource(hikariConfig);
        }
        return YamlShardingSphereDataSourceFactory.createDataSource(new File(configurationFile));
    }
    
    public static String getTableSize() {
        return System.getProperty("tableSize");
    }
    
    public static String getKeyId() {
        return System.getProperty("tableSize");
    }
}

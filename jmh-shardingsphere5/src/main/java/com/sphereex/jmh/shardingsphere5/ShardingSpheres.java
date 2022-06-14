package com.sphereex.jmh.shardingsphere5;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.apache.shardingsphere.driver.api.yaml.YamlShardingSphereDataSourceFactory;

import javax.sql.DataSource;
import java.io.File;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ShardingSpheres {
    
    @SneakyThrows
    public static DataSource createDataSource() {
        String configurationFile = System.getProperty("shardingsphere.configurationFile");
        return YamlShardingSphereDataSourceFactory.createDataSource(new File(configurationFile));
    }
    
    public static String getTableName() {
        return System.getProperty("tableName");
    }
}

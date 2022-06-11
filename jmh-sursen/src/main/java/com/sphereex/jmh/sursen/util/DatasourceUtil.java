package com.sphereex.jmh.sursen.util;

import lombok.SneakyThrows;
import org.apache.shardingsphere.driver.api.yaml.YamlShardingSphereDataSourceFactory;

import javax.sql.DataSource;
import java.io.File;

public class DatasourceUtil {

    @SneakyThrows
    public static DataSource createDataSource() {
        String configurationFile = System.getProperty("configFile");
        return YamlShardingSphereDataSourceFactory.createDataSource(new File(configurationFile));
    }
}

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
//        if ("sursen".equals(datasourceType)) {
//            String tableName = System.getProperty("tableName");
//            String realTableName = tableName.substring(0, tableName.lastIndexOf("1"));
//            if ("tb_f_user".equals(realTableName)) {
//                KeyStores.initTable(tableName, 100, "id", "name", "birthday", "gender", "nationality", "contact_person",
//                        "create_time", "update_time", "version", "updator", "disable");
//                KeyStores.authTable(tableName, 101, "id", "name", "birthday", "gender", "nationality", "contact_person",
//                        "create_time", "update_time", "version", "updator", "disable");
//            }
//            if ("tb_f_user_cert".equals(realTableName)) {
//                KeyStores.initTable(tableName, 102, "id", "user_id", "cert_type", "cert_no", "cert_key", "issuing_unit",
//                        "effective_date", "expire_date", "master", "create_time", "update_time", "version", "updator", "disable");
//                KeyStores.authTable(tableName, 103, "id", "user_id", "cert_type", "cert_no", "cert_key", "issuing_unit",
//                        "effective_date", "expire_date", "master", "create_time", "update_time", "version", "updator", "disable");
//            }
//            if ("tb_f_user_contact".equals(realTableName)) {
//                KeyStores.initTable(tableName, 104, "id", "user_id", "area_code", "phone", "email", "master",
//                        "create_time", "update_time", "version", "updator", "disable");
//                KeyStores.authTable(tableName, 105, "id", "user_id", "area_code", "phone", "email", "master",
//                        "create_time", "update_time", "version", "updator", "disable");
//            }
//        }
        return YamlShardingSphereDataSourceFactory.createDataSource(new File(configurationFile));
    }
}

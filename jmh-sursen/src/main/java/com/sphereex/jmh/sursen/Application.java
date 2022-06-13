package com.sphereex.jmh.sursen;

import org.apache.shardingsphere.driver.api.yaml.YamlShardingSphereDataSourceFactory;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

public class Application {
    
    public static void main(String... args) throws SQLException, IOException {
//        HikariConfig configuration = new HikariConfig();
//        configuration.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/sursen_encrypt?serverTimezone=UTC&useSSL=false");
//        configuration.setUsername("root");
//        configuration.setPassword("root");
//        DataSource dataSource = new HikariDataSource(configuration);
        DataSource dataSource = YamlShardingSphereDataSourceFactory.createDataSource(new File("/Users/nianjun/Work" +
                "/Workspace/database-jmh/jmh-sursen/src/main/resources/config-encrypt.yaml"));
        //dataSource.getConnection().createStatement().execute(SQLClause.USER_CERT_10_CREATION);
//        CreateTableCommand createTableCommand = new CreateTableCommand();
//        DropTableCommand dropTableCommand = new DropTableCommand();
//        InsertTableCommand insertTableCommand = new InsertTableCommand();
//        dropTableCommand.execute(dataSource);
//        createTableCommand.execute(dataSource);
//        insertTableCommand.execute(dataSource,"tb_f_user_contact10");
        Properties properties = new Properties();
        properties.load(new FileReader("/Users/nianjun/Work/Workspace/database-jmh/jmh-sursen/src/main/resources/sql_sample.properties"));
        System.out.println("---------------------------------");
        System.out.println(properties.getProperty("SELECT_FROM_USER_WHERE_NAME"));
    }
}
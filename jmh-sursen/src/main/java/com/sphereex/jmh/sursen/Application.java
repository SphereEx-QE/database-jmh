package com.sphereex.jmh.sursen;

import com.sphereex.jmh.sursen.command.data.impl.CreateTableCommand;
import com.sphereex.jmh.sursen.command.data.impl.DropTableCommand;
import com.sphereex.jmh.sursen.command.data.impl.InsertTableCommand;
import com.sphereex.jmh.sursen.constants.SursenParamConfig;
import com.sphereex.jmh.sursen.util.DatasourceUtil;
import org.apache.shardingsphere.driver.api.yaml.YamlShardingSphereDataSourceFactory;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

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
//        Properties properties = new Properties();
//        properties.load(new FileReader("/Users/nianjun/Work/Workspace/database-jmh/jmh-sursen/src/main/resources/sql_sample.properties"));
//        System.out.println("---------------------------------");
//        System.out.println(properties.getProperty("SELECT_FROM_USER_WHERE_NAME"));
        executeCommand();
    }

    private static void executeCommand() throws SQLException {
        SursenParamConfig sursenParamConfig = new SursenParamConfig(System.getProperty("configFile"),
                System.getProperty("tableName"), System.getProperty("command"));
        DataSource dataSource = DatasourceUtil.createDataSource(sursenParamConfig.getConfigFile());
        CreateTableCommand createTableCommand = new CreateTableCommand();
        DropTableCommand dropTableCommand = new DropTableCommand();
        InsertTableCommand insertTableCommand = new InsertTableCommand();
        if ("init".equals(sursenParamConfig.getCommand())) {
            if (sursenParamConfig.getTableName() == null) {
                dropTableCommand.execute(dataSource);
                createTableCommand.execute(dataSource);
                insertTableCommand.execute(dataSource);
            } else {
                dropTableCommand.execute(dataSource, sursenParamConfig.getTableName());
                createTableCommand.execute(dataSource, sursenParamConfig.getTableName());
                insertTableCommand.execute(dataSource, sursenParamConfig.getTableName());
            }
        }
        if ("drop".equals(sursenParamConfig.getCommand())) {
            if (sursenParamConfig.getTableName() == null) {
                dropTableCommand.execute(dataSource);
            } else {
                dropTableCommand.execute(dataSource, sursenParamConfig.getTableName());
            }
        }
        if ("createTable".equals(sursenParamConfig.getCommand())) {
            if (sursenParamConfig.getTableName() == null) {
                createTableCommand.execute(dataSource);
            } else {
                createTableCommand.execute(dataSource, sursenParamConfig.getTableName());
            }
        }
        if ("insert".equals(sursenParamConfig.getCommand())) {
            if (sursenParamConfig.getTableName() == null) {
                insertTableCommand.execute(dataSource);
            } else {
                insertTableCommand.execute(dataSource, sursenParamConfig.getTableName());
            }
        }
    }
}

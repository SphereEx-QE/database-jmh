package com.sphereex.jmh.sursen;

import com.sphereex.encrypt.sdk.Error;
import com.sphereex.jmh.sursen.command.data.impl.CreateTableCommand;
import com.sphereex.jmh.sursen.command.data.impl.DropTableCommand;
import com.sphereex.jmh.sursen.command.data.impl.InsertTableCommand;
import com.sphereex.jmh.sursen.constants.SursenParamConfig;
import com.sphereex.jmh.sursen.util.DatasourceUtil;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

public class Application {

    public static void main(String... args) throws SQLException, IOException, Error.LoadKeyFailException, Error.InitFailException, Error.AuthFailException {
//        String command = System.getProperty("command");
//        if ("prepare".equals(command)) {
//            executePrepareCommand();
//        } else {
//            executeDataCommand();
//        }

        System.setProperty("KeyStoreService", "http://150.138.84.46:10083/api/encryption/key/v1/");
        System.setProperty("ZookeepConnect", "150.138.84.30:2182");
        System.setProperty("KeyApiPort", "8030");
        System.setProperty("KeyExpiredTime", "60");


        DataSource dataSource = DatasourceUtil.createDataSource(System.getProperty("configFile"));
        CreateTableCommand createTableCommand = new CreateTableCommand();
        DropTableCommand dropTableCommand = new DropTableCommand();
        InsertTableCommand insertTableCommand = new InsertTableCommand();

        dropTableCommand.execute(dataSource, "tb_f_user_cert10");
        createTableCommand.execute(dataSource, "tb_f_user_cert10");
        insertTableCommand.execute(dataSource, "tb_f_user_cert10");
    }

    private static void executeDataCommand() throws SQLException {
        SursenParamConfig sursenParamConfig = new SursenParamConfig(System.getProperty("configFile"),
                System.getProperty("tableName"), System.getProperty("command"));
        DataSource dataSource = DatasourceUtil.createDataSource(sursenParamConfig.getConfigFile());
        CreateTableCommand createTableCommand = new CreateTableCommand();
        DropTableCommand dropTableCommand = new DropTableCommand();
        InsertTableCommand insertTableCommand = new InsertTableCommand();
        if ("init".equals(sursenParamConfig.getCommand())) {
            if (sursenParamConfig.getTableName() == null || "".equals(sursenParamConfig.getTableName().trim())) {
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
            if (sursenParamConfig.getTableName() == null || "".equals(sursenParamConfig.getTableName().trim())) {
                dropTableCommand.execute(dataSource);
            } else {
                dropTableCommand.execute(dataSource, sursenParamConfig.getTableName());
            }
        }
        if ("create".equals(sursenParamConfig.getCommand())) {
            if (sursenParamConfig.getTableName() == null || "".equals(sursenParamConfig.getTableName().trim())) {
                createTableCommand.execute(dataSource);
            } else {
                createTableCommand.execute(dataSource, sursenParamConfig.getTableName());
            }
        }
        if ("insert".equals(sursenParamConfig.getCommand())) {
            if (sursenParamConfig.getTableName() == null || "".equals(sursenParamConfig.getTableName().trim())) {
                insertTableCommand.execute(dataSource);
            } else {
                insertTableCommand.execute(dataSource, sursenParamConfig.getTableName());
            }
        }
    }

    private static void executePrepareCommand() throws IOException, SQLException {
        FileReader fileReader = new FileReader(System.getProperty("prepareSQL"));
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        DataSource dataSource = DatasourceUtil.createDataSource(System.getProperty("configFile"));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            dataSource.getConnection().createStatement().execute(line);
        }
    }
}

package com.sphereex.jmh.sursen;

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

    public static void main(String... args) throws SQLException, IOException {
        executeCommand();
        prepareData();
    }

    private static void executeCommand() throws SQLException {
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
        if ("createTable".equals(sursenParamConfig.getCommand())) {
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

    private static void prepareData() throws IOException, SQLException {
        FileReader fileReader = new FileReader(System.getProperty("prepareSQL"));
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        DataSource dataSource = DatasourceUtil.createDataSource(System.getProperty("configFile"));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            System.out.println(line);
            dataSource.getConnection().createStatement().execute(line);
        }
    }
}

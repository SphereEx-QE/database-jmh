package com.sphereex.jmh.sursen.command.data.impl;

import com.sphereex.jmh.sursen.command.data.DataCommand;
import com.sphereex.jmh.sursen.command.SpecificDataCommand;

import javax.sql.DataSource;
import java.sql.SQLException;

public class InitDataCommand implements DataCommand, SpecificDataCommand {

    private final CreateTableCommand createTableCommand = new CreateTableCommand();

    private final DropTableCommand dropTableCommand = new DropTableCommand();

    private final InsertTableCommand insertTableCommand = new InsertTableCommand();

    @Override
    public void execute(DataSource dataSource) throws SQLException {
        dropTableCommand.execute(dataSource);
        createTableCommand.execute(dataSource);
        insertTableCommand.execute(dataSource);
    }

    @Override
    public void execute(DataSource dataSource, int tableSize) throws SQLException {
        dropTableCommand.execute(dataSource, tableSize);
        createTableCommand.execute(dataSource, tableSize);
        insertTableCommand.execute(dataSource, tableSize);
    }

    @Override
    public void execute(DataSource dataSource, String tableName) throws SQLException {
        dropTableCommand.execute(dataSource, tableName);
        createTableCommand.execute(dataSource, tableName);
        insertTableCommand.execute(dataSource, tableName);
    }
}

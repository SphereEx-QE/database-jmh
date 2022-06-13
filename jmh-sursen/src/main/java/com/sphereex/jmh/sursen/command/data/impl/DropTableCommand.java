package com.sphereex.jmh.sursen.command.data.impl;

import com.sphereex.jmh.sursen.command.data.DataCommand;
import com.sphereex.jmh.sursen.command.SpecificDataCommand;
import com.sphereex.jmh.sursen.constants.CommandConstant;
import com.sphereex.jmh.sursen.receiver.impl.EmptyCommand;

import javax.sql.DataSource;
import java.sql.SQLException;

public class DropTableCommand implements DataCommand, SpecificDataCommand {

    @Override
    public void execute(DataSource dataSource) throws SQLException {
        dropAllTable(dataSource);
    }

    @Override
    public void execute(DataSource dataSource, int tableSize) throws SQLException {
        dropTableBySize(dataSource, tableSize);
    }

    @Override
    public void execute(DataSource dataSource, String tableName) throws SQLException {
        dropTablesByName(dataSource, tableName);
    }

    private void dropAllTable(DataSource dataSource) throws SQLException {
        EmptyCommand emptyCommand = new EmptyCommand();
        for (String tableName : CommandConstant.ALL_TABLE_NAMES) {
            emptyCommand.dropTable(dataSource, tableName);
        }
    }

    private void dropTableBySize(DataSource dataSource, int tableSize) throws SQLException {
        EmptyCommand emptyCommand = new EmptyCommand();
        for (String tableName : CommandConstant.getTableListBySize(tableSize)) {
            emptyCommand.dropTable(dataSource, tableName);
        }
    }

    private void dropTablesByName(DataSource dataSource, String name) throws SQLException {
        EmptyCommand emptyCommand = new EmptyCommand();
        emptyCommand.dropTable(dataSource, name);
    }
}

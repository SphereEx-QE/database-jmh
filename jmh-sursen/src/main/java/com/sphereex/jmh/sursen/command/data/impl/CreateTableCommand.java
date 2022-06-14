package com.sphereex.jmh.sursen.command.data.impl;

import com.sphereex.jmh.sursen.command.SpecificDataCommand;
import com.sphereex.jmh.sursen.command.data.DataCommand;
import com.sphereex.jmh.sursen.constants.CommandConstant;
import com.sphereex.jmh.sursen.constants.SQLClause;

import javax.sql.DataSource;
import java.sql.SQLException;

public class CreateTableCommand implements DataCommand, SpecificDataCommand {

    @Override
    public void execute(DataSource dataSource) throws SQLException {
        createAllTable(dataSource);
    }

    @Override
    public void execute(DataSource dataSource, int tableSize) throws SQLException {
        createTableBySize(dataSource, tableSize);
    }

    @Override
    public void execute(DataSource dataSource, String tableName) throws SQLException {
        EmptyCommand emptyCommand = new EmptyCommand();
        String realTableName = tableName.substring(0, tableName.lastIndexOf("1"));
        String createTableSQL = "";
        if ("tb_f_user".equals(realTableName)) {
            createTableSQL = SQLClause.USER_CREATION.replace("TABLE_NAME", tableName);
        }
        if ("tb_f_user_cert".equals(realTableName)) {
            createTableSQL = SQLClause.USER_CERT_CREATION.replace("TABLE_NAME", tableName);
        }
        if ("tb_f_user_contact".equals(realTableName)) {
            createTableSQL = SQLClause.USER_CONTACT_CREATION.replace("TABLE_NAME", tableName);
        }
        emptyCommand.createTable(dataSource, createTableSQL);
    }

    private void createAllTable(DataSource dataSource) throws SQLException {
        EmptyCommand emptyCommand = new EmptyCommand();
        for (String tableSizeName : CommandConstant.TRANSFER_TABLE_SIZE_LIST) {
            emptyCommand.createTable(dataSource, SQLClause.USER_CREATION.replace("TABLE_NAME",
                    "tb_f_user" + tableSizeName));
            emptyCommand.createTable(dataSource, SQLClause.USER_CERT_CREATION.replace("TABLE_NAME",
                    "tb_f_user_cert" + tableSizeName));
            emptyCommand.createTable(dataSource, SQLClause.USER_CONTACT_CREATION.replace("TABLE_NAME",
                    "tb_f_user_contact" + tableSizeName));
        }
    }

    private void createTableBySize(DataSource dataSource, int tableSize) throws SQLException {
        EmptyCommand emptyCommand = new EmptyCommand();
        emptyCommand.createTable(dataSource, SQLClause.USER_CREATION.replace("TABLE_NAME",
                "tb_f_user" + CommandConstant.getTransferTableSizeNameBySize(tableSize)));
        emptyCommand.createTable(dataSource, SQLClause.USER_CERT_CREATION.replace("TABLE_NAME",
                "tb_f_user_cert" + CommandConstant.getTransferTableSizeNameBySize(tableSize)));
        emptyCommand.createTable(dataSource, SQLClause.USER_CONTACT_CREATION.replace("TABLE_NAME",
                "tb_f_user_contact" + CommandConstant.getTransferTableSizeNameBySize(tableSize)));
    }

    private void createTableByName(DataSource dataSource, String tableName) throws SQLException {
        EmptyCommand emptyCommand = new EmptyCommand();
        emptyCommand.createTable(dataSource, SQLClause.USER_CREATION.replace("TABLE_NAME", tableName));
    }
}

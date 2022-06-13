package com.sphereex.jmh.sursen.command.data.impl;

import com.sphereex.jmh.sursen.command.data.DataCommand;
import com.sphereex.jmh.sursen.command.SpecificDataCommand;
import com.sphereex.jmh.sursen.constants.CommandConstant;
import com.sphereex.jmh.sursen.receiver.data.impl.User;
import com.sphereex.jmh.sursen.receiver.data.impl.UserCert;
import com.sphereex.jmh.sursen.receiver.data.impl.UserContact;

import javax.sql.DataSource;
import java.sql.SQLException;

public class InsertTableCommand implements DataCommand, SpecificDataCommand {

    @Override
    public void execute(DataSource dataSource) throws SQLException {
        insertAllTable(dataSource);
    }

    @Override
    public void execute(DataSource dataSource, int tableSize) throws SQLException {
        insertDataBySize(dataSource, tableSize);
    }

    @Override
    public void execute(DataSource dataSource, String tableName) throws SQLException {
        int tableSize = Integer.parseInt(tableName.substring(tableName.lastIndexOf("1"))) * 10_000;
        String realTableName = tableName.substring(0, tableName.lastIndexOf("1"));
        if ("tb_f_user".equals(realTableName)) {
            User user = new User();
            user.insertData(dataSource, tableSize);
        }
        if ("tb_f_user_cert".equals(realTableName)) {
            UserCert userCert = new UserCert();
            userCert.insertData(dataSource, tableSize);
        }
        if ("tb_f_user_contact".equals(realTableName)) {
            UserContact userContact = new UserContact();
            userContact.insertData(dataSource, tableSize);
        }
    }

    private void insertAllTable(DataSource dataSource) throws SQLException {
        User user = new User();
        UserCert userCert = new UserCert();
        UserContact userContact = new UserContact();

        user.insertData(dataSource, CommandConstant.SIZE_10W);
        userCert.insertData(dataSource, CommandConstant.SIZE_10W);
        userContact.insertData(dataSource, CommandConstant.SIZE_10W);

        user.insertData(dataSource, CommandConstant.SIZE_100W);
        userCert.insertData(dataSource, CommandConstant.SIZE_100W);
        userContact.insertData(dataSource, CommandConstant.SIZE_100W);

        user.insertData(dataSource, CommandConstant.SIZE_1000W);
        userCert.insertData(dataSource, CommandConstant.SIZE_1000W);
        userContact.insertData(dataSource, CommandConstant.SIZE_1000W);
    }

    private void insertDataBySize(DataSource dataSource, int tableSize) throws SQLException {
        User user = new User();
        UserCert userCert = new UserCert();
        UserContact userContact = new UserContact();

        if (tableSize == CommandConstant.SIZE_10W) {
            user.insertData(dataSource, CommandConstant.SIZE_10W);
            userCert.insertData(dataSource, CommandConstant.SIZE_10W);
            userContact.insertData(dataSource, CommandConstant.SIZE_10W);
        }
        if (tableSize == CommandConstant.SIZE_100W) {
            user.insertData(dataSource, CommandConstant.SIZE_100W);
            userCert.insertData(dataSource, CommandConstant.SIZE_100W);
            userContact.insertData(dataSource, CommandConstant.SIZE_100W);
        }
        if (tableSize == CommandConstant.SIZE_1000W) {
            user.insertData(dataSource, CommandConstant.SIZE_1000W);
            userCert.insertData(dataSource, CommandConstant.SIZE_1000W);
            userContact.insertData(dataSource, CommandConstant.SIZE_1000W);
        }
    }
}

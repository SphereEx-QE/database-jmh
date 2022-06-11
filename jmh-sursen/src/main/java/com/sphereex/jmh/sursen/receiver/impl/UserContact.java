package com.sphereex.jmh.sursen.receiver.impl;

import com.sphereex.jmh.sursen.receiver.CommandReceiver;
import com.sphereex.jmh.sursen.util.RandomDataUtil;
import com.sphereex.jmh.sursen.util.TableNameUtil;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Calendar;
import java.util.UUID;

public class UserContact implements CommandReceiver {

    @Override
    public void insertData(DataSource dataSource, int tableSize) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = null;
        String tableName = "";
        String INSERT_CLAUSE = "insert into TABLE_NAME values (?,?,?,?,?,?,?,?,?,?,?)";
        tableName = TableNameUtil.getTableNameThroughSize("tb_f_user_contact", tableSize);
        preparedStatement = connection.prepareStatement(INSERT_CLAUSE.replace("TABLE_NAME", tableName));
        for (int i = 0; i < tableSize; i++) {
            setRandomDataForStatement(preparedStatement);
            preparedStatement.execute();
        }
    }

    private void setRandomDataForStatement(PreparedStatement preparedStatement) throws SQLException {
        Timestamp now = new Timestamp(Calendar.getInstance().getTimeInMillis());
        // id
        preparedStatement.setString(1, UUID.randomUUID().toString());
        // user_id
        preparedStatement.setString(2, UUID.randomUUID().toString());
        // area_code
        preparedStatement.setString(3, "+86");
        // phone
        preparedStatement.setString(4, RandomDataUtil.getRandomMobileNumber());
        // email
        preparedStatement.setString(5, RandomDataUtil.getRandomEmailAddress());
        // master
        preparedStatement.setInt(6, 1);
        // create_time
        preparedStatement.setTimestamp(7, now);
        // update_time
        preparedStatement.setTimestamp(8, now);
        // version
        preparedStatement.setInt(9, 1);
        // updator
        preparedStatement.setString(10, RandomDataUtil.getRandomUserName());
        // disable
        preparedStatement.setInt(11, 0);
    }
}

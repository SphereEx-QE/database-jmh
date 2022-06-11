package com.sphereex.jmh.sursen.receiver.impl;

import com.sphereex.jmh.sursen.receiver.CommandReceiver;
import com.sphereex.jmh.sursen.util.RandomDataUtil;
import com.sphereex.jmh.sursen.util.TableNameUtil;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.UUID;

public class User implements CommandReceiver {

    @Override
    public void insertData(DataSource dataSource, int tableSize) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = null;
        String tableName = "";
        String INSERT_CLAUSE = "insert into TABLE_NAME values (?,?,?,?,?,?,?,?,?,?,?)";
        tableName = TableNameUtil.getTableNameThroughSize("tb_f_user", tableSize);
        preparedStatement = connection.prepareStatement(INSERT_CLAUSE.replace("TABLE_NAME", tableName));
        for (int i = 0; i < tableSize; i++) {
            setRandomDataForStatement(preparedStatement);
            preparedStatement.execute();
        }
    }

    private void setRandomDataForStatement(PreparedStatement preparedStatement) throws SQLException {
        Timestamp now = new Timestamp(Calendar.getInstance().getTimeInMillis());
        preparedStatement.setString(1, UUID.randomUUID().toString());
        preparedStatement.setString(2, RandomDataUtil.getRandomName());
        preparedStatement.setDate(3, RandomDataUtil.getRandomBirthDay());
        preparedStatement.setInt(4, RandomDataUtil.getRandomGender());
        preparedStatement.setString(5, RandomDataUtil.getRandomNationality());
        preparedStatement.setString(6, RandomDataUtil.getRandomName());
        preparedStatement.setTimestamp(7, now);
        preparedStatement.setTimestamp(8, now);
        preparedStatement.setInt(9, 1);
        preparedStatement.setString(10, RandomDataUtil.getRandomName());
        preparedStatement.setInt(11, 0);
    }
}

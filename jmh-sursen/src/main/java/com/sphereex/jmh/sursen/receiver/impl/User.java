package com.sphereex.jmh.sursen.receiver.impl;

import com.sphereex.jmh.sursen.receiver.CommandReceiver;
import com.sphereex.jmh.sursen.util.RandomDataUtil;
import com.sphereex.jmh.sursen.util.TableNameUtil;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.UUID;

public class User implements CommandReceiver {
    
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

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
        // id
        preparedStatement.setString(1, UUID.randomUUID().toString());
        // name
        preparedStatement.setString(2, RandomDataUtil.getRandomName());
        // birthday
        preparedStatement.setString(3, RandomDataUtil.getRandomBirthDayStr());
        // gender
        preparedStatement.setInt(4, RandomDataUtil.getRandomGender());
        // nationality
        preparedStatement.setString(5, RandomDataUtil.getRandomNationality());
        // contact_person
        preparedStatement.setString(6, RandomDataUtil.getRandomName());
        // create_time
        preparedStatement.setTimestamp(7, now);
        // update_time
        preparedStatement.setTimestamp(8, now);
        // version
        preparedStatement.setInt(9, 1);
        // updator
        preparedStatement.setString(10, RandomDataUtil.getRandomName());
        // disable
        preparedStatement.setInt(11, 0);
    }
}

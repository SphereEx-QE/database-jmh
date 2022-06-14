package com.sphereex.jmh.sursen.receiver.data.impl;

import com.sphereex.jmh.sursen.receiver.data.DataCommandReceiver;
import com.sphereex.jmh.sursen.util.CardNumberUtil;
import com.sphereex.jmh.sursen.util.RandomDataUtil;
import com.sphereex.jmh.sursen.util.TableNameUtil;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Calendar;
import java.util.UUID;

public class UserCert implements DataCommandReceiver {

    @Override
    public void insertData(DataSource dataSource, int tableSize) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = null;
        String tableName = "";
        String INSERT_CLAUSE = "insert into TABLE_NAME values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        tableName = TableNameUtil.getTableNameThroughSize("tb_f_user_cert", tableSize);
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
        // cert_type
        preparedStatement.setString(3, "身份证");
        // cert_no
        preparedStatement.setString(4, CardNumberUtil.getRandomID());
        // cert_key
        preparedStatement.setString(5, "身份证");
        // issuing_unit
        preparedStatement.setString(6, "公安机关");
        // effective_date
        preparedStatement.setDate(7, new Date(System.currentTimeMillis()));
        // expire_date
        preparedStatement.setDate(8, new Date(System.currentTimeMillis() + 315360000000L));
        // master
        preparedStatement.setInt(9, 1);
        // create_time
        preparedStatement.setTimestamp(10, now);
        // update_time
        preparedStatement.setTimestamp(11, now);
        // version
        preparedStatement.setInt(12, 1);
        // updator
        preparedStatement.setString(13, RandomDataUtil.getRandomUserName());
        // disable
        preparedStatement.setInt(14, 0);
    }
}

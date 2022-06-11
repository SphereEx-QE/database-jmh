package com.sphereex.jmh.sursen.receiver;

import javax.sql.DataSource;
import java.sql.SQLException;

public interface CommandReceiver {

    void insertData(DataSource dataSource, int tableSize) throws SQLException;

    default void createTable(DataSource dataSource,String tableCreationSQL) throws SQLException {
        dataSource.getConnection().createStatement().execute(tableCreationSQL);
    }

    default void dropTable(DataSource dataSource, String tableName) throws SQLException {
        dataSource.getConnection().createStatement().execute("drop table if exists " + tableName);
    }
}

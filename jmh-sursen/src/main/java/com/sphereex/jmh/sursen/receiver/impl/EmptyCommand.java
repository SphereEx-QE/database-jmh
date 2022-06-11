package com.sphereex.jmh.sursen.receiver.impl;

import com.sphereex.jmh.sursen.receiver.CommandReceiver;

import javax.sql.DataSource;
import java.sql.SQLException;

public class EmptyCommand implements CommandReceiver {
    @Override
    public void insertData(DataSource dataSource, int tableSize) throws SQLException {
    }
}

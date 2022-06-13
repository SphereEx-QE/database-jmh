package com.sphereex.jmh.sursen.command.data.impl;

import com.sphereex.jmh.sursen.receiver.data.DataCommandReceiver;

import javax.sql.DataSource;
import java.sql.SQLException;

public class EmptyCommand implements DataCommandReceiver {
    
    @Override
    public void insertData(DataSource dataSource, int tableSize) throws SQLException {
    }
}

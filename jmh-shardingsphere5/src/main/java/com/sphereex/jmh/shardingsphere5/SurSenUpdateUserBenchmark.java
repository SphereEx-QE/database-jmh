package com.sphereex.jmh.shardingsphere5;

import com.sphereex.jmh.jdbc.SurSenUpdateUser;
import lombok.SneakyThrows;

import javax.sql.DataSource;
import java.sql.Connection;

public class SurSenUpdateUserBenchmark extends SurSenUpdateUser {
    
    private static final DataSource DATA_SOURCE;
    
    private static final String tableSize;

    static {
        DATA_SOURCE = ShardingSpheres.createDataSource();
        tableSize = ShardingSpheres.getTableSize();
    }

    @SneakyThrows
    @Override
    public Connection getConnection() {
        return DATA_SOURCE.getConnection();
    }

    @Override
    public String getTableSize() {
        return tableSize;
    }
}

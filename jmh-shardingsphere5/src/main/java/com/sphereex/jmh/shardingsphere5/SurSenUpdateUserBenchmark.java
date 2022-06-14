package com.sphereex.jmh.shardingsphere5;

import com.sphereex.jmh.jdbc.SurSenUpdateUser;
import lombok.SneakyThrows;

import javax.sql.DataSource;
import java.sql.Connection;

public class SurSenUpdateUserBenchmark extends SurSenUpdateUser {
    
    private static final DataSource DATA_SOURCE;

    static {
        DATA_SOURCE = ShardingSpheres.createDataSource();
    }

    @SneakyThrows
    @Override
    public Connection getConnection() {
        return DATA_SOURCE.getConnection();
    }

    @Override
    public String getTableName() {
        return ShardingSpheres.getTableName();
    }
}
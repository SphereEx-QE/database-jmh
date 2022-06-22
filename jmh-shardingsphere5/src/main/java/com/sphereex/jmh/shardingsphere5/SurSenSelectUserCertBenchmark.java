package com.sphereex.jmh.shardingsphere5;

import com.sphereex.jmh.jdbc.SurSenSelectUserCert;
import lombok.SneakyThrows;

import javax.sql.DataSource;
import java.sql.Connection;

public class SurSenSelectUserCertBenchmark extends SurSenSelectUserCert {
    
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
    public String getTableSize() {
        return ShardingSpheres.getTableSize();
    }

    @Override
    public String getKeyId() {
        return ShardingSpheres.getKeyId();
    }
}

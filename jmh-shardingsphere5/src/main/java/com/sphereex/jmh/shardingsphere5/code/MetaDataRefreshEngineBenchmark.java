package com.sphereex.jmh.shardingsphere5.code;

import org.apache.shardingsphere.infra.binder.engine.SQLBindEngine;
import org.apache.shardingsphere.infra.connection.refresher.MetaDataRefreshEngine;
import org.apache.shardingsphere.sql.parser.sql.dialect.statement.postgresql.dml.PostgreSQLSelectStatement;
import org.openjdk.jmh.Main;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Warmup;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;

@Fork(1)
@Warmup(iterations = 3, time = 5)
@Measurement(iterations = 3, time = 5)
public class MetaDataRefreshEngineBenchmark {
    
    @Benchmark
    public void benchMetaDataRefreshEngine() throws SQLException {
        new MetaDataRefreshEngine(null, null, null).refresh(null, null);
    }
    
    public static void main(String[] args) throws IOException {
        Main.main(new String[]{MetaDataRefreshEngineBenchmark.class.getName()});
    }
}

package com.sphereex.jmh.sursen.receiver.benchmark;

import com.sphereex.jmh.sursen.pojo.BenchmarkParam;
import com.sphereex.jmh.sursen.util.RandomDataUtil;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
public class SelectBenchmark {
    
    private PreparedStatement preparedStatement;
    
    @Setup
    public void setupStatement(BenchmarkParam benchmarkParam) {
        this.preparedStatement = benchmarkParam.getPreparedStatement();
    }
    
    @Benchmark
    public void selectFromUserWhereName(Blackhole blackhole) throws SQLException {
        preparedStatement.setString(1, RandomDataUtil.getRandomUserName());
        preparedStatement.execute();
    }
}

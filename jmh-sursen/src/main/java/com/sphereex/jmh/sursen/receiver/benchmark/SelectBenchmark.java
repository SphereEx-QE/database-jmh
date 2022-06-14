package com.sphereex.jmh.sursen.receiver.benchmark;

import com.sphereex.jmh.sursen.pojo.BenchmarkParam;
import com.sphereex.jmh.sursen.util.RandomDataUtil;
import org.openjdk.jmh.annotations.*;

import java.sql.SQLException;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
public class SelectBenchmark {
    
    @Benchmark
    public void selectFromUserWhereName(BenchmarkParam benchmarkParam) throws SQLException {
        benchmarkParam.getPreparedStatement().setString(1, RandomDataUtil.getRandomUserName());
        benchmarkParam.getPreparedStatement().execute();
    }
}

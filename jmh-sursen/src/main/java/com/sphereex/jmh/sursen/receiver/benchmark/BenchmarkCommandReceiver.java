package com.sphereex.jmh.sursen.receiver.benchmark;

import java.sql.PreparedStatement;

public interface BenchmarkCommandReceiver {
    
    void initBenchmark(PreparedStatement preparedStatement);
}

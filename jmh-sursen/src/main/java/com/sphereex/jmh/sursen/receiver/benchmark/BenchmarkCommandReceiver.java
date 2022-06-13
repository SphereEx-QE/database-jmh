package com.sphereex.jmh.sursen.receiver.benchmark;

import org.apache.calcite.prepare.Prepare;

public interface BenchmarkCommandReceiver {
    
    void initBenchmark(Prepare statement);
}

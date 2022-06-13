package com.sphereex.jmh.sursen.command.benchmark.impl;

import com.sphereex.jmh.sursen.command.benchmark.BenchmarkCommand;
import com.sphereex.jmh.sursen.pojo.BenchmarkParam;

import javax.sql.DataSource;

public class BenchmarkBySQLCommand implements BenchmarkCommand {

    @Override
    public void execute(DataSource dataSource, String SQL, BenchmarkParam benchmarkParam) {
        
    }
}

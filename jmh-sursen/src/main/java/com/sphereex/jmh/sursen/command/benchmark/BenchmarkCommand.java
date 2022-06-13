package com.sphereex.jmh.sursen.command.benchmark;

import com.sphereex.jmh.sursen.pojo.BenchmarkParam;

import javax.sql.DataSource;

public interface BenchmarkCommand {

    void execute(DataSource dataSource, String sql, BenchmarkParam benchmarkParam);
}

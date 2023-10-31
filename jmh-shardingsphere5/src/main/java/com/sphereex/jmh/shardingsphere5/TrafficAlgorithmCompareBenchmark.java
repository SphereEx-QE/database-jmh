package com.sphereex.jmh.shardingsphere5;

import org.apache.shardingsphere.traffic.algorithm.traffic.hint.SQLHintTrafficAlgorithm;
import org.apache.shardingsphere.traffic.algorithm.traffic.segment.SQLMatchTrafficAlgorithm;
import org.apache.shardingsphere.traffic.algorithm.traffic.segment.SQLRegexTrafficAlgorithm;
import org.apache.shardingsphere.traffic.api.traffic.hint.HintTrafficValue;
import org.apache.shardingsphere.traffic.api.traffic.segment.SegmentTrafficValue;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(1)
@Threads(5)
@Warmup(iterations = 5, time = 5)
@Measurement(iterations = 5, time = 10)
@State(Scope.Benchmark)
public class TrafficAlgorithmCompareBenchmark {
    
    private SQLHintTrafficAlgorithm sqlHintAlgorithm;
    
    private SQLMatchTrafficAlgorithm sqlMatchAlgorithm;
    
    private SQLRegexTrafficAlgorithm sqlRegexAlgorithm;
    
    @Setup(Level.Trial)
    public void setUp() {
        initSQLHintAlgorithm();
        initSQLMatchAlgorithm();
        initSQLRegexAlgorithm();
    }
    
    private void initSQLRegexAlgorithm() {
        sqlRegexAlgorithm = new SQLRegexTrafficAlgorithm();
        Properties properties = new Properties();
        properties.put("regex", "(?i)^(UPDATE|SELECT).*WHERE user_id.*");
        sqlRegexAlgorithm.init(properties);
    }
    
    private void initSQLMatchAlgorithm() {
        sqlMatchAlgorithm = new SQLMatchTrafficAlgorithm();
        Properties properties = new Properties();
        properties.put("sql", "SELECT * FROM t_order WHERE content IN (?, ?); UPDATE t_order SET creation_date = NOW() WHERE user_id = 1;");
        sqlMatchAlgorithm.init(properties);
    }
    
    private void initSQLHintAlgorithm() {
        sqlHintAlgorithm = new SQLHintTrafficAlgorithm();
        Properties properties = new Properties();
        properties.put("use_traffic", "true");
        sqlHintAlgorithm.init(properties);
    }
    
    @Benchmark
    public void testSQLMatchAlgorithmMatch() {
        sqlMatchAlgorithm.match(new SegmentTrafficValue(null, "SELECT * FROM t_order WHERE content IN (?, ?);"));
    }
    
    @Benchmark
    public void testSQLRegexAlgorithmMatch() {
        sqlRegexAlgorithm.match(new SegmentTrafficValue(null, "SELECT * FROM t_order WHERE user_id IN (?, ?);"));
    }
}
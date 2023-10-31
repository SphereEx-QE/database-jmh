package com.sphereex.jmh.shardingsphere5.code.protocol;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.apache.shardingsphere.db.protocol.postgresql.packet.command.query.extended.bind.protocol.text.impl.PostgreSQLTimestampValueParser;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(3)
@Warmup(time = 2, iterations = 5)
@Measurement(time = 2, iterations = 5)
public class PostgreSQLTextTimestampUtilsBenchmark {
    
    private static final String[] TEXT_VALUES = {
            "20211012 2323",
            "20211012 23:23",
            "20211012 232323",
            "2021-10-12 23:23:23",
            "2021-10-12 23:23:23.1",
            "2021-10-12 23:23:23.12",
            "2021-10-12 23:23:23.123",
            "2021-10-12 23:23:23.123+08",
            "2021-10-12T23:23:23.123+08",
            "2021-10-12 23:23:23.12345",
            "2021-10-12 23:23:23.12345+0800",
            "20211012 23:23:23.12345+0800",
            "2021-10-12 23:23:23.12345+08:00:00",
            "211012 23:23:23.12345+08:00:00",
            "10/12/21 23:23:23.12345+08:00:00",
            "2021-10-12 23:23:23.123456",
            "2021-10-12 23:23:23.123456 +08:00",
            "2021-10-12 23:23:23.123456 -08:00",
            "2021-3-3 23:23:23.123456"
    };
    
    private final ThreadLocalRandom random = ThreadLocalRandom.current();
    
    @Benchmark
    public void benchParse() {
        new PostgreSQLTimestampValueParser().parse(TEXT_VALUES[random.nextInt(TEXT_VALUES.length)]);
    }
    
    public static void main(String[] args) throws RunnerException {
        new Runner(new OptionsBuilder().include(PostgreSQLTextTimestampUtilsBenchmark.class.getName()).build()).run();
    }   
}

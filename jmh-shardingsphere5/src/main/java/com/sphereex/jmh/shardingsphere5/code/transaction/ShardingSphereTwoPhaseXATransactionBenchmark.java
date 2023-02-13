package com.sphereex.jmh.shardingsphere5.code.transaction;

import com.sphereex.jmh.config.BenchmarkParameters;
import com.sphereex.jmh.jdbc.JDBCConnectionProvider;
import com.sphereex.jmh.shardingsphere5.ShardingSpheres;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Two phase xa transaction benchmark.
 */
@Slf4j
@State(Scope.Thread)
public class ShardingSphereTwoPhaseXATransactionBenchmark implements JDBCConnectionProvider {
    
    private final ThreadLocalRandom random = ThreadLocalRandom.current();
    
    private static final DataSource DATA_SOURCE;
    
    static {
        DATA_SOURCE = ShardingSpheres.createDataSource();
    }
    
    private final ThreadLocal<AtomicInteger> threadLocal = ThreadLocal.withInitial(() -> new AtomicInteger(0));
    
    @SneakyThrows
    @Override
    public Connection getConnection() {
        return DATA_SOURCE.getConnection();
    }
    
    @Setup(Level.Trial)
    public void setup() throws Exception {
    }
    
    @Benchmark
    public void oltpReadWrite() throws Exception {
        Connection connection = null;
        try {
            connection = DATA_SOURCE.getConnection();
            connection.setAutoCommit(false);
    
            log.debug(Thread.currentThread().getName() + "统计-开始, " + threadLocal.get().incrementAndGet());
            for (int i = 0; i < 10; i++) {
                try (Statement each = connection.createStatement()) {
                    each.execute(String.format("select c from sbtest1 where id=%s", random.nextInt(BenchmarkParameters.TABLE_SIZE)));
                }
            }
            
            try (Statement each = connection.createStatement()) {
                each.execute(String.format("update sbtest1 set k=k+1 where id=%s", random.nextInt(BenchmarkParameters.TABLE_SIZE)));
            }
            
            try (Statement each = connection.createStatement()) {
                each.execute(String.format("update sbtest1 set c='%s' where id=%s", randomString(120), random.nextInt(BenchmarkParameters.TABLE_SIZE)));
            }
            
            int id = random.nextInt(BenchmarkParameters.TABLE_SIZE);
            try (Statement each = connection.createStatement()) {
                each.execute(String.format("delete from sbtest1 where id=%s", id));
            }
            
//            try (Statement each = connection.createStatement()) {
//                each.execute(String.format("insert into sbtest1 (id,k,c,pad) values (%s,%s,'%s','%s')", id, random.nextInt(Integer.MAX_VALUE), Strings.randomString(120), Strings.randomString(60)));
//            }
            log.debug(Thread.currentThread().getName() + "统计-结束, " + threadLocal.get().get());
            connection.commit();
            log.debug(Thread.currentThread().getName() + "统计-commit, " + threadLocal.get().get());
        } catch (Exception e) {
            if (null != connection) {
                connection.rollback();
            }
            log.debug(Thread.currentThread().getName() + "统计-异常, " + threadLocal.get().get());
            e.printStackTrace();
        } finally {
            if (null != connection) {
                connection.close();
            }
        }
    }
    
    public static String randomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        while (sb.length() < length) {
            sb.append(ThreadLocalRandom.current().nextLong());
        }
        sb.setLength(length);
        return sb.toString();
    }
    
    @TearDown(Level.Trial)
    public void tearDown() throws Exception {
        
    }
    
    public static void main(String[] args) throws RunnerException {
        new Runner(new OptionsBuilder()
                .include(ShardingSphereTwoPhaseXATransactionBenchmark.class.getName())
                .threads(20)
                .forks(0)
                .build()).run();
    }
}

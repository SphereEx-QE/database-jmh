package com.sphereex.jmh.shardingsphere5.code.transaction;

import com.sphereex.jmh.config.BenchmarkParameters;
import com.sphereex.jmh.jdbc.JDBCConnectionProvider;
import com.sphereex.jmh.shardingsphere5.ShardingSpheres;
import lombok.SneakyThrows;
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


/**
 * One phase xa transaction benchmark, only support one sharding table `sbtest1`.
 */
@State(Scope.Thread)
public class ShardingSphereOnePhaseXATransactionBenchmark implements JDBCConnectionProvider {
    
    private final ThreadLocalRandom random = ThreadLocalRandom.current();
    
    private static final DataSource DATA_SOURCE;
    
    static {
        DATA_SOURCE = ShardingSpheres.createDataSource();
    }
    
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
            int randomIdNum = random.nextInt(BenchmarkParameters.TABLE_SIZE - 20);
            for (int i = 0; i < 10; i++) {
                try (Statement each = connection.createStatement()) {
                    each.execute(String.format("select c from sbtest1 where id=%s", randomIdNum));
                }
            }
            
            try (Statement each = connection.createStatement()) {
                each.execute(String.format("update sbtest1 set k=k+1 where id=%s", randomIdNum + 4));
            }
            
            try (Statement each = connection.createStatement()) {
                each.execute(String.format("update sbtest1 set c='%s' where id=%s", randomString(120), randomIdNum + 4 * 2));
            }
            
            try (Statement each = connection.createStatement()) {
                each.execute(String.format("delete from sbtest1 where id=%s", randomIdNum + 4 * 3));
            }
            connection.commit();
        } catch (Exception e) {
            if (null != connection) {
                connection.rollback();
            }
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
                .include(ShardingSphereOnePhaseXATransactionBenchmark.class.getName())
                .threads(50)
                .forks(1)
                .build()).run();
    }
}

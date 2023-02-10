package com.sphereex.jmh.shardingsphere5.code.transaction;

import com.sphereex.jmh.config.BenchmarkParameters;
import com.sphereex.jmh.jdbc.JDBCConnectionProvider;
import com.sphereex.jmh.shardingsphere5.ShardingSpheres;
import com.sphereex.jmh.util.Strings;
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
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.ThreadLocalRandom;


/**
 * One phase xa transaction benchmark, only support one sharding table `sbtest1`.
 */
@State(Scope.Thread)
public class ShardingSphereOnePhasePreparedStatementXATransactionBenchmark implements JDBCConnectionProvider {
    
    private final ThreadLocalRandom random = ThreadLocalRandom.current();
    
    private static final DataSource DATA_SOURCE;
    
    static {
        DATA_SOURCE = ShardingSpheres.createDataSource();
    }
    
    private final PreparedStatement[] reads = new PreparedStatement[BenchmarkParameters.TABLES];
    
    private final PreparedStatement[] indexUpdates = new PreparedStatement[BenchmarkParameters.TABLES];
    
    private final PreparedStatement[] nonIndexUpdates = new PreparedStatement[BenchmarkParameters.TABLES];
    
    private final PreparedStatement[] deletes = new PreparedStatement[BenchmarkParameters.TABLES];
    
    private final PreparedStatement[] inserts = new PreparedStatement[BenchmarkParameters.TABLES];
    
    private Connection connection;
    
    @Setup(Level.Trial)
    public void setup() throws Exception {
        connection = getConnection();
        connection.setAutoCommit(false);
        for (int i = 0; i < reads.length; i++) {
            reads[i] = connection.prepareStatement(String.format("select c from sbtest%d where id=?", i + 1));
        }
        for (int i = 0; i < indexUpdates.length; i++) {
            indexUpdates[i] = connection.prepareStatement(String.format("update sbtest%d set k=k+1 where id=?", i + 1));
        }
        for (int i = 0; i < nonIndexUpdates.length; i++) {
            nonIndexUpdates[i] = connection.prepareStatement(String.format("update sbtest%d set c=? where id=?", i + 1));
        }
        for (int i = 0; i < deletes.length; i++) {
            deletes[i] = connection.prepareStatement(String.format("delete from sbtest%d where id=?", i + 1));
        }
        for (int i = 0; i < inserts.length; i++) {
            inserts[i] = connection.prepareStatement(String.format("insert into sbtest%d (id,k,c,pad) values (?,?,?,?)", i + 1));
        }
    }
    
    @Benchmark
    public void oltpReadWrite() throws Exception {
        try {
            connection.setAutoCommit(false);
            int randomIdNum = random.nextInt(BenchmarkParameters.TABLE_SIZE - 20);
            for (PreparedStatement each : reads) {
                each.setInt(1, randomIdNum);
            }
            
            PreparedStatement indexUpdate = indexUpdates[random.nextInt(BenchmarkParameters.TABLES)];
            indexUpdate.setInt(1, randomIdNum + 4);
            indexUpdate.execute();
            
            PreparedStatement nonIndexUpdate = nonIndexUpdates[random.nextInt(BenchmarkParameters.TABLES)];
            nonIndexUpdate.setString(1, Strings.randomString(120));
            nonIndexUpdate.setInt(2, randomIdNum + 4 * 2);
            nonIndexUpdate.execute();
            
            int table = random.nextInt(BenchmarkParameters.TABLES);
            int deleteAndInsertId = randomIdNum + 4 * 3;
            PreparedStatement delete = deletes[table];
            delete.setInt(1, deleteAndInsertId);
            delete.execute();
            
            PreparedStatement insert = inserts[table];
            insert.setInt(1, deleteAndInsertId);
            insert.setInt(2, deleteAndInsertId);
            insert.setString(3, Strings.randomString(120));
            insert.setString(4, Strings.randomString(60));
            insert.execute();
            
            connection.commit();
        } catch (Exception e) {
            if (null != connection) {
                connection.rollback();
            }
            e.printStackTrace();
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
        for (PreparedStatement each : reads) {
            each.close();
        }
        for (PreparedStatement each : indexUpdates) {
            each.close();
        }
        for (PreparedStatement each : nonIndexUpdates) {
            each.close();
        }
        for (PreparedStatement each : deletes) {
            each.close();
        }
        for (PreparedStatement each : inserts) {
            each.close();
        }
        connection.close();
    }
    
    
    public static void main(String[] args) throws RunnerException {
        new Runner(new OptionsBuilder()
                .include(ShardingSphereOnePhasePreparedStatementXATransactionBenchmark.class.getName())
                .threads(20)
                .forks(1)
                .build()).run();
    }
    
    @Override
    public Connection getConnection() {
        try {
            return DATA_SOURCE.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}

package com.sphereex.jmh.shardingsphere5;

import com.sphereex.jmh.config.BenchmarkParameters;
import com.sphereex.jmh.jdbc.JDBCConnectionProvider;
import lombok.SneakyThrows;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.concurrent.ThreadLocalRandom;


/**
 * One phase xa transaction benchmark, only support one sharding table `sbtest_1`.
 */
@State(Scope.Thread)
public class ShardingSphereOnePhaseXATransactionBenchmark implements JDBCConnectionProvider {
    
    private final ThreadLocalRandom random = ThreadLocalRandom.current();
    
    private final PreparedStatement[] reads = new PreparedStatement[BenchmarkParameters.TABLES];
    
    private final PreparedStatement[] indexUpdates = new PreparedStatement[BenchmarkParameters.TABLES];
    
    private final PreparedStatement[] nonIndexUpdates = new PreparedStatement[BenchmarkParameters.TABLES];
    
    private final PreparedStatement[] deletes = new PreparedStatement[BenchmarkParameters.TABLES];
    
    private final PreparedStatement[] inserts = new PreparedStatement[BenchmarkParameters.TABLES];
    
    private static final DataSource DATA_SOURCE;
    
    static {
        DATA_SOURCE = ShardingSpheres.createDataSource();
    }
    
    private Connection connection;
    
    @SneakyThrows
    @Override
    public Connection getConnection() {
        return DATA_SOURCE.getConnection();
    }
    
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
            long t1 = System.currentTimeMillis();
            int randomIdNum = random.nextInt(BenchmarkParameters.TABLES);
            for (PreparedStatement each : reads) {
                each.setInt(1, randomIdNum);
                each.execute();
            }
            long t2 = System.currentTimeMillis();
            System.out.println("read: " + (t2 - t1));
            
            PreparedStatement indexUpdate = indexUpdates[random.nextInt(BenchmarkParameters.TABLES)];
            indexUpdate.setInt(1, randomIdNum);
            indexUpdate.execute();
            long t3 = System.currentTimeMillis();
            System.out.println("indexUpdate: " + (t3 - t2));
            
            PreparedStatement nonIndexUpdate = nonIndexUpdates[random.nextInt(BenchmarkParameters.TABLES)];
            nonIndexUpdate.setString(1, randomString(120));
            nonIndexUpdate.setInt(2, randomIdNum);
            nonIndexUpdate.execute();
            long t4 = System.currentTimeMillis();
            System.out.println("nonIndexUpdate: " + (t4 - t3));
            
            int table = random.nextInt(BenchmarkParameters.TABLES);
            PreparedStatement delete = deletes[table];
            delete.setInt(1, randomIdNum);
            delete.execute();
            long t5 = System.currentTimeMillis();
            System.out.println("delete: " + (t5 - t4));
            
            PreparedStatement insert = inserts[table];
            insert.setInt(1, randomIdNum);
            insert.setInt(2, random.nextInt(Integer.MAX_VALUE));
            insert.setString(3, randomString(120));
            insert.setString(4, randomString(60));
            insert.execute();
            long t6 = System.currentTimeMillis();
            System.out.println("insert: " + (t6 - t5));
            
            connection.commit();
            long t7 = System.currentTimeMillis();
            System.out.println("commit: " + (t7 - t6));
        } catch (Exception e) {
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
}

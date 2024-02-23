package com.sphereex.jmh.jdbc;

import com.sphereex.jmh.config.BenchmarkParameters;
import com.sphereex.jmh.util.Strings;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.concurrent.ThreadLocalRandom;

@State(Scope.Thread)
public abstract class UnpooledWriteOnlyBenchmarkBase implements JDBCConnectionProvider {
    
    private final ThreadLocalRandom random = ThreadLocalRandom.current();
    
    private final PreparedStatement[] indexUpdates = new PreparedStatement[BenchmarkParameters.TABLES];
    
    private final PreparedStatement[] nonIndexUpdates = new PreparedStatement[BenchmarkParameters.TABLES];
    
    private final PreparedStatement[] deletes = new PreparedStatement[BenchmarkParameters.TABLES];
    
    private final PreparedStatement[] inserts = new PreparedStatement[BenchmarkParameters.TABLES];
    
    private Connection connection;
    
    @Setup(Level.Trial)
    public void setup() throws Exception {
        connection = getConnection();
        connection.setAutoCommit(false);
        for (int i = 0; i < indexUpdates.length; i++) {
            indexUpdates[i] = connection.prepareStatement(String.format("update sbtest%d set k=? where id=?", i + 1));
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
    public void oltpWriteOnly() throws Exception {
        int table = random.nextInt(BenchmarkParameters.TABLES);
        int id = random.nextInt(BenchmarkParameters.TABLE_SIZE);
        PreparedStatement indexUpdate = indexUpdates[table];
        indexUpdate.setInt(1, random.nextInt(Integer.MAX_VALUE));
        indexUpdate.setInt(2, id);
        indexUpdate.execute();
        PreparedStatement nonIndexUpdate = nonIndexUpdates[table];
        nonIndexUpdate.setString(1, Strings.randomString(120));
        nonIndexUpdate.setInt(2, id);
        nonIndexUpdate.execute();
        PreparedStatement delete = deletes[table];
        delete.setInt(1, id);
        delete.execute();
        PreparedStatement insert = inserts[table];
        insert.setInt(1, id);
        insert.setInt(2, random.nextInt(Integer.MAX_VALUE));
        insert.setString(3, Strings.randomString(120));
        insert.setString(4, Strings.randomString(60));
        insert.execute();
        connection.commit();
    }
    
    @TearDown(Level.Trial)
    public void tearDown() throws Exception {
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

package com.sphereex.jmh.jdbc;

import org.openjdk.jmh.annotations.*;

import java.sql.PreparedStatement;
import java.util.concurrent.ThreadLocalRandom;

@BenchmarkMode(Mode.SingleShotTime)
@State(Scope.Thread)
public abstract class SurSenSelectUser implements JDBCConnectionProvider, SurSenTableProvider {

    private final ThreadLocalRandom random = ThreadLocalRandom.current();

    private PreparedStatement selectStatement;

    @Setup(Level.Trial)
    public void setup() throws Exception {
        selectStatement =
                getConnection().prepareStatement(("select * from tb_f_user$TABLE_SIZE where name = ?;").replace("$TABLE_SIZE",
                        getTableSize()));
    }

    @Benchmark
    public void batchInserts() throws Exception {
        selectStatement.setString(1, "TEST_USER_10");
        selectStatement.execute();
    }

    @TearDown(Level.Trial)
    public void tearDown() throws Exception {
        selectStatement.close();
    }
}
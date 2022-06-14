package com.sphereex.jmh.jdbc;

import org.openjdk.jmh.annotations.*;

import java.sql.PreparedStatement;
import java.util.concurrent.ThreadLocalRandom;

@BenchmarkMode(Mode.SingleShotTime)
@State(Scope.Thread)
public abstract class SurSenUpdateUserContact implements JDBCConnectionProvider, SurSenTableProvider {

    private final ThreadLocalRandom random = ThreadLocalRandom.current();

    private PreparedStatement updateStatement;

    @Setup(Level.Trial)
    public void setup() throws Exception {
        updateStatement =
                getConnection().prepareStatement(("update TABLE_NAME set area_code = '86' where phone like " +
                        "'156%';").replace("TABLE_NAME", getTableName()));
    }

    @Benchmark
    public void batchInserts() throws Exception {
        updateStatement.setString(1, "TEST_USER_10");
        updateStatement.execute();
    }

    @TearDown(Level.Trial)
    public void tearDown() throws Exception {
        updateStatement.close();
    }
}

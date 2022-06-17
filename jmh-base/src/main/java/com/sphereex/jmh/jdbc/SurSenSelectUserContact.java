package com.sphereex.jmh.jdbc;

import org.openjdk.jmh.annotations.*;

import java.sql.PreparedStatement;
import java.util.concurrent.ThreadLocalRandom;

@BenchmarkMode(Mode.SingleShotTime)
@State(Scope.Thread)
public abstract class SurSenSelectUserContact implements JDBCConnectionProvider, SurSenTableProvider {

    private final ThreadLocalRandom random = ThreadLocalRandom.current();

    private PreparedStatement selectStatement;

    @Setup(Level.Trial)
    public void setup() throws Exception {
        String sql = ("select * from tb_f_user_contact$TABLE_SIZE where phone like '156%' order by phone limit 10;").replace("$TABLE_SIZE", getTableSize());
        selectStatement = getConnection().prepareStatement(sql);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public void selectUserContact() throws Exception {
        selectStatement.execute();
    }

    @TearDown(Level.Trial)
    public void tearDown() throws Exception {
        selectStatement.close();
    }
}

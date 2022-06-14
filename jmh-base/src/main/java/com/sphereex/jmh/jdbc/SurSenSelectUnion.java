package com.sphereex.jmh.jdbc;

import org.openjdk.jmh.annotations.*;

import java.sql.PreparedStatement;
import java.util.concurrent.ThreadLocalRandom;

@BenchmarkMode(Mode.SingleShotTime)
@State(Scope.Thread)
public abstract class SurSenSelectUnion implements JDBCConnectionProvider, SurSenTableProvider {

    private final ThreadLocalRandom random = ThreadLocalRandom.current();

    private PreparedStatement updateStatement;

    @Setup(Level.Trial)
    public void setup() throws Exception {
        updateStatement =
                getConnection().prepareStatement(("select a.name from tb_f_user$TABLE_SIZE a, tb_f_user_cert$TABLE_SIZE b, " +
                        "tb_f_user_contact$TABLE_SIZE c where a.id = b.user_id and a.id = c.user_id and b.cert_no = ? and a" +
                        ".name = ? and c.phone = ?;").replaceAll("$TABLE_SIZE",
                        getTableSize()));
    }

    @Benchmark
    public void batchInserts() throws Exception {
        updateStatement.execute();
    }

    @TearDown(Level.Trial)
    public void tearDown() throws Exception {
        updateStatement.close();
    }
}

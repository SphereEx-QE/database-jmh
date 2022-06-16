package com.sphereex.jmh.jdbc;

import org.openjdk.jmh.annotations.*;

import java.sql.PreparedStatement;
import java.util.concurrent.ThreadLocalRandom;

@BenchmarkMode(Mode.SingleShotTime)
@State(Scope.Thread)
public abstract class SurSenUpdateUserCert implements JDBCConnectionProvider, SurSenTableProvider {

    private final ThreadLocalRandom random = ThreadLocalRandom.current();

    private PreparedStatement updateStatement;

    @Setup(Level.Trial)
    public void setup() throws Exception {
        updateStatement =
                getConnection().prepareStatement(("update tb_f_user_cert$TABLE_SIZE set cert_type = 'IDENTITY_CARD' " +
                        "where expire_date < '2033-12-30' ;").replace("$TABLE_SIZE", getTableSize()));
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public void updateCertType() throws Exception {
        updateStatement.execute();
    }

    @TearDown(Level.Trial)
    public void tearDown() throws Exception {
        updateStatement.close();
    }
}

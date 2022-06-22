package com.sphereex.jmh.jdbc;

import org.openjdk.jmh.annotations.*;

import java.sql.PreparedStatement;

@BenchmarkMode(Mode.SingleShotTime)
@State(Scope.Thread)
public abstract class SurSenSelectUserCert implements JDBCConnectionProvider, SurSenTableProvider {
    
    private PreparedStatement selectStatement;

    @Setup(Level.Trial)
    public void setup() throws Exception {
        selectStatement =
                getConnection().prepareStatement(("select * from tb_f_user_cert$TABLE_SIZE where expire_date < " +
                        "'2033-12-30';").replace("TABLE_SIZE", getTableSize()));
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public void selectUserCert() throws Exception {
        selectStatement.execute();
    }

    @TearDown(Level.Trial)
    public void tearDown() throws Exception {
        selectStatement.close();
    }
}

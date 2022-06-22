package com.sphereex.jmh.jdbc;

import org.openjdk.jmh.annotations.*;

import java.sql.PreparedStatement;

@BenchmarkMode(Mode.SingleShotTime)
@State(Scope.Thread)
public abstract class SurSenSelectUnion implements JDBCConnectionProvider, SurSenTableProvider {

    private PreparedStatement selectStatement;

    @Setup(Level.Trial)
    public void setup() throws Throwable {
        selectStatement =
                getConnection().prepareStatement(("select a.name from tb_f_user$TABLE_SIZE a, tb_f_user_cert$TABLE_SIZE b, " +
                        "tb_f_user_contact$TABLE_SIZE c where a.id = b.user_id and a.id = c.user_id and b.cert_no = ? and a" +
                        ".name = ? and c.phone = ?;").replace("$TABLE_SIZE", getTableSize()));
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public void selectUnion() throws Exception {
        selectStatement.setString(1, "111111111111111111");
        selectStatement.setString(2, "TEST_UNION");
        selectStatement.setString(3, "19999999999");
        selectStatement.execute();
    }

    @TearDown(Level.Trial)
    public void tearDown() throws Exception {
        selectStatement.close();
    }
}

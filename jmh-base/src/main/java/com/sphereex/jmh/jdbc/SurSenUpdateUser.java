package com.sphereex.jmh.jdbc;

import com.sphereex.jmh.util.Strings;
import org.openjdk.jmh.annotations.*;

import java.sql.PreparedStatement;

@BenchmarkMode(Mode.SingleShotTime)
@State(Scope.Thread)
public abstract class SurSenUpdateUser implements JDBCConnectionProvider, SurSenTableProvider {
    
    private PreparedStatement updateStatement;

    @Setup(Level.Trial)
    public void setup() throws Exception {
        updateStatement =
                getConnection().prepareStatement(("update tb_f_user$TABLE_SIZE set name = '" + Strings.randomString(10) +
                        "'where name = ?;").replace("$TABLE_SIZE", getTableSize()));
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public void updateName() throws Exception {
        updateStatement.setString(1, "TEST_USER_10");
        updateStatement.execute();
    }

    @TearDown(Level.Trial)
    public void tearDown() throws Exception {
        updateStatement.close();
    }
}

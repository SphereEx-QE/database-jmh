package com.sphereex.jmh.sursen.command;

import javax.sql.DataSource;
import java.sql.SQLException;

public interface SpecificDataCommand {

    void execute(DataSource dataSource, int tableSize) throws SQLException;

    void execute(DataSource dataSource, String tableName) throws SQLException;
}

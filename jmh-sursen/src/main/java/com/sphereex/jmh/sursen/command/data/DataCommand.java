package com.sphereex.jmh.sursen.command.data;

import javax.sql.DataSource;
import java.sql.SQLException;

public interface DataCommand {
    
    void execute(DataSource dataSource) throws SQLException;
}

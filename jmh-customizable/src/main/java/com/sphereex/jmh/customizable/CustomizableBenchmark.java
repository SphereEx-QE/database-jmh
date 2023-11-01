package com.sphereex.jmh.customizable;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@State(Scope.Thread)
public class CustomizableBenchmark {
    
    private Connection connection;
    
    private Statement statement;
    
    private final List<String> sqlList = new ArrayList<>();
    
    @Setup(Level.Trial)
    public void setup() throws IOException, ClassNotFoundException, SQLException {
        File configFile = new File(System.getProperty("conf"));
        File sqlFile = new File(System.getProperty("script"));
        if (!configFile.exists()) {
            throw new FileNotFoundException("can not find the config file " + System.getProperty("conf"));
        }
        if (!sqlFile.exists()) {
            throw new FileNotFoundException("can not find the sql file " + System.getProperty("script"));
        }
        Properties configProperties = new Properties();
        try (InputStream inputStream = Files.newInputStream(configFile.toPath())) {
            configProperties.load(inputStream);
            Class.forName(configProperties.getProperty("driverClassName"));
            connection = DriverManager.getConnection(configProperties.getProperty("url"), configProperties.getProperty("username"), configProperties.getProperty("password"));
        }
        try (BufferedReader reader = Files.newBufferedReader(sqlFile.toPath())) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                sqlList.add(line);
            }
        }
        statement = connection.createStatement();
    }
    
    @Benchmark
    public void executeSQL() throws SQLException {
        for (String sql : sqlList) {
            statement.execute(sql);
        }
    }
    
    @TearDown(Level.Trial)
    public void tearDown() throws SQLException {
        statement.close();
        connection.close();
    }
}

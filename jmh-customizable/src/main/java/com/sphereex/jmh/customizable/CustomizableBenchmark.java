package com.sphereex.jmh.customizable;

import com.sphereex.jmh.customizable.util.CustomizableUtil;
import lombok.extern.slf4j.Slf4j;
import org.openjdk.jmh.Main;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.annotations.Timeout;
import org.openjdk.jmh.annotations.Warmup;

import java.io.BufferedReader;
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
import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
@Fork(1)
@Slf4j
public class CustomizableBenchmark {
    
    private Connection connection;
    
    private Statement statement;
    
    private List<String> sqlList;
    
    private int totalCount;
    
    private int intervalCount = 10000;
    
    @Setup(Level.Trial)
    public void setup() throws IOException, ClassNotFoundException, SQLException {
        Properties configProperties = new Properties();
        intervalCount = System.getProperty("intervalCount") == null ? intervalCount : Integer.parseInt(System.getProperty("intervalCount"));
        try (InputStream inputStream = Files.newInputStream(CustomizableUtil.getConfigFile(System.getProperty("conf")).toPath())) {
            configProperties.load(inputStream);
            Class.forName(configProperties.getProperty("driverClassName"));
            connection = DriverManager.getConnection(configProperties.getProperty("url"), configProperties.getProperty("username"), configProperties.getProperty("password"));
        }
        try (BufferedReader reader = Files.newBufferedReader(CustomizableUtil.getSQLFile(System.getProperty("script")).toPath())) {
            String line;
            sqlList = new ArrayList<>();
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
    @Warmup(iterations = 0)
    @Measurement(iterations = 1, batchSize = 1)
    @BenchmarkMode(Mode.SingleShotTime)
    @Timeout(time = 5, timeUnit = TimeUnit.HOURS)
    public void executeSQL() {
        for (String sql : sqlList) {
            try {
                statement.execute(sql);
            } catch (Exception ignore) {
                log.error("execute sql failed: " + sql);
            }
            totalCount++;
            if (totalCount % intervalCount == 0) {
                log.info("already executed " + totalCount + " sqls");
            }
        }
    }
    
    @TearDown(Level.Trial)
    public void tearDown() throws SQLException {
        statement.close();
        connection.close();
    }
    
    public static void main(String[] args) throws IOException {
        Main.main(new String[]{CustomizableBenchmark.class.getName()});
    }
}

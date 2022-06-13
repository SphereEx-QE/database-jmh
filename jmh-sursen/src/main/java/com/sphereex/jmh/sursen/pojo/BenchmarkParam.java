package com.sphereex.jmh.sursen.pojo;

import lombok.Data;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

import java.sql.PreparedStatement;

@Data
@State(Scope.Thread)
public class BenchmarkParam {
    
    private int threads;
    
    private PreparedStatement preparedStatement;
}

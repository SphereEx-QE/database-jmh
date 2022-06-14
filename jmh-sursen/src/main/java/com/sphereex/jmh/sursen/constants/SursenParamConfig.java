package com.sphereex.jmh.sursen.constants;

import lombok.Getter;

@Getter
public class SursenParamConfig {

    private final String configFile;

    private final String tableName;
    
    private final String command;

    public SursenParamConfig(String configFile, String tableName, String command) {
        this.configFile = configFile;
        this.tableName = tableName;
        if (command == null) {
            this.command = "init";
        } else {
            this.command = command;
        }
        if (configFile == null) {
            throw new IllegalArgumentException(" configFile is not set");
        }
        if (tableName == null) {
            throw new IllegalArgumentException(" tableName is not set");
        }
    }
}

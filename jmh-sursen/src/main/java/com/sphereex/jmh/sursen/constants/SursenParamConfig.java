package com.sphereex.jmh.sursen.constants;

public class SursenParamConfig {

    private final String TABLE_SIZE;

    private final String TABLE_NAME;

    private final String CONFIG_FILE;

    private static SursenParamConfig INSTANCE;

    private SursenParamConfig() {
        TABLE_SIZE = System.getProperty("table_size");
        TABLE_NAME = System.getProperty("table_name");
        CONFIG_FILE = System.getProperty("configFile");
        INSTANCE = new SursenParamConfig();
    }

    public static SursenParamConfig getInstance() {
        return INSTANCE;
    }
}

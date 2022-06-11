package com.sphereex.jmh.sursen.util;

public class TableNameUtil {

    public static String getTableNameThroughSize(String tableName, int tableSize) {
        if (100000 == tableSize) {
            return tableName + "10";
        }
        if (1000000 == tableSize) {
            return tableName + "100";
        }
        if (10000000 == tableSize) {
            return tableName + "1000";
        }
        throw new IllegalArgumentException("table size " + tableSize + " is not support");
    }
}

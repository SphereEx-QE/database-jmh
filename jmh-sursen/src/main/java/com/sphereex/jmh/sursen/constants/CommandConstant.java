package com.sphereex.jmh.sursen.constants;

import java.util.Arrays;
import java.util.List;

public class CommandConstant {

    public static int SIZE_10W = 100_000;

    public static int SIZE_100W = 1_000_000;

    public static int SIZE_1000W = 10_000_000;

    public static final List<String> TRANSFER_TABLE_SIZE_LIST = Arrays.asList("10", "100", "1000");

    public static final List<String> ALL_TABLE_NAMES = Arrays.asList("tb_f_user10", "tb_f_user_cert10",
            "tb_f_user_contact10",
            "tb_f_user100", "tb_f_user_cert100", "tb_f_user_contact100",
            "tb_f_user1000", "tb_f_user_cert1000", "tb_f_user_contact1000");

    public static final List<String> TABLE_NAMES_10w = Arrays.asList("tb_f_user10", "tb_f_user_cert10", 
            "tb_f_user_contact10");

    public static final List<String> TABLE_NAMES_100w = Arrays.asList("tb_f_user100", "tb_f_user_cert100",
            "tb_f_user_contact100");

    public static final List<String> TABLE_NAMES_1000w = Arrays.asList("tb_f_user1000", "tb_f_user_cert1000",
            "tb_f_user_contact1000");

    public static List<String> getTableListBySize(int tableSize) {
        if (100000 == tableSize) {
            return TABLE_NAMES_10w;
        }
        if (1000000 == tableSize) {
            return TABLE_NAMES_100w;
        }
        if (10000000 == tableSize) {
            return TABLE_NAMES_1000w;
        }
        throw new IllegalArgumentException("table size " + tableSize + " is not support");
    }

    public static String getTransferTableSizeNameBySize(int tableSize) {
        if (100000 == tableSize) {
            return TRANSFER_TABLE_SIZE_LIST.get(0);
        }
        if (1000000 == tableSize) {
            return TRANSFER_TABLE_SIZE_LIST.get(1);
        }
        if (10000000 == tableSize) {
            return TRANSFER_TABLE_SIZE_LIST.get(2);
        }
        throw new IllegalArgumentException("table size " + tableSize + " is not support");
    }
}

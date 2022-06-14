package com.sphereex.jmh.config;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class UserNameList {

    private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();

    private static final List<String> USER_NAME_LIST = Arrays.asList("test-1", "test-2", "test-3", "test-4", "test-5", "test-6",
            "test-7", "test-8", "test-9", "test-10");

    public static String getRandomUserName() {
        return USER_NAME_LIST.get(RANDOM.nextInt(10));
    }
}

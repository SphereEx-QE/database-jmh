package com.sphereex.jmh.sursen.util;

import com.sphereex.jmh.sursen.enums.NameEnum;
import com.sphereex.jmh.sursen.enums.NationalityEnum;

import java.sql.Date;
import java.util.Calendar;
import java.util.concurrent.ThreadLocalRandom;

public class RandomDataUtil {

    private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();
    
    public static Date getRandomBirthDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(RANDOM.nextInt(1950, 2020), RANDOM.nextInt(11), RANDOM.nextInt(28));
        return new Date(calendar.getTimeInMillis());
    }

    public static char getRandomGender() {
        return (char) RANDOM.nextInt(2);
    }

    public static String getRandomNationality() {
        return NationalityEnum.values()[RANDOM.nextInt(5)].name();
    }

    public static String getRandomName() {
        return NameEnum.values()[RANDOM.nextInt(12)].name();
    }

}

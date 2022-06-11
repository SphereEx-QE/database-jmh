package com.sphereex.jmh.sursen.util;

import com.sphereex.jmh.sursen.enums.NameEnum;
import com.sphereex.jmh.sursen.enums.NationalityEnum;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.concurrent.ThreadLocalRandom;

public class RandomDataUtil {

    private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

    public static Date getRandomBirthDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(RANDOM.nextInt(1950, 2020), RANDOM.nextInt(11), RANDOM.nextInt(28));
        return new Date(calendar.getTimeInMillis());
    }

    public static String getRandomBirthDayStr() {
        LocalDateTime localDateTime = LocalDateTime.of(RANDOM.nextInt(1950, 2020), RANDOM.nextInt(11) + 1,
                RANDOM.nextInt(28) + 1, RANDOM.nextInt(24), RANDOM.nextInt(60));
        return FORMATTER.format(localDateTime);
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

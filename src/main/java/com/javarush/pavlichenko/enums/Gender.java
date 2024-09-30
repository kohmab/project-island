package com.javarush.pavlichenko.enums;

import java.util.concurrent.ThreadLocalRandom;

public enum Gender {
    MALE, FEMALE;

    public static Gender getRandom() {
        {
            int choice = ThreadLocalRandom.current().nextInt(Gender.values().length);
            return Gender.values()[choice];
        }
    }
}

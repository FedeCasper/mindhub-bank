package com.mindhub.homebanking.utils;

public class AccountUtils {

    public AccountUtils() {}

    public static int getRandomAccountNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}

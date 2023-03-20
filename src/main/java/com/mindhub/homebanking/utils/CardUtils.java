package com.mindhub.homebanking.utils;

import static com.mindhub.homebanking.utils.AccountUtils.getRandomAccountNumber;

public final class CardUtils {

    private CardUtils() {}

    public static String getRandomCardNumber() {
        String cardNumber = (int)((Math.random() * (9999-1000)) + 1000)
                + "-" + (int)((Math.random() * (9999-1000)) + 1000)
                + "-" + (int)((Math.random() * (9999-1000)) + 1000)
                + "-" + (int)((Math.random() * (9999-1000)) + 1000);
        return cardNumber;
    }

    public static int getRandomCvvNumber(){
        return (int) ((Math.random() * (999 - 100)) + 100);
    }

}

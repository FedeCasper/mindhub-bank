package com.mindhub.homebanking.utils;

import com.mindhub.homebanking.errors.MiExcepcionVerificada;

public class AccountUtils {

    public AccountUtils() {}

    public static int getRandomAccountNumber(Integer min, Integer max) throws MiExcepcionVerificada {
        if(min == null || max == null){
            throw new MiExcepcionVerificada("Los parámetros no pueden ser nulos");
        }
        return (int) ((Math.random() * (max - min)) + min);
    }
}

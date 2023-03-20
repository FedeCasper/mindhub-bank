package com.mindhub.homebanking.anotations;

import com.mindhub.homebanking.enums.AnotationsType;
import com.mindhub.homebanking.enums.State;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomUnused {
    AnotationsType anotationsType();
    State state();

}

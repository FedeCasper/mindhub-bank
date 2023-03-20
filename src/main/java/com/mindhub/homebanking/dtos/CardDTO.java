package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class CardDTO {

    long id;
    private String cardHolder;
    private CardType type;
    private CardColor color;
    private String number;
    private LocalDate fromDate;
    private LocalDate truDate;
    private int cvv;
    private boolean isActive;

    public CardDTO (){}

    public CardDTO (Card card){
        this.id = card.getId();
        this.cardHolder = card.getCardHolder();
        this.type = card.getType();
        this.color = card.getColor();
        this.number = card.getNumber();
        this.fromDate = card.getFromDate();
        this.truDate = card.getTruDate();
        this.cvv = card.getCvv();
        //this.isActive = card.isActive();
    }

    public long getId() {return id;}

    public String getCardHolder() {return cardHolder;}

    public CardType getType() {return type;}

    public CardColor getColor() {return color;}

    public String getNumber() {return number;}

    public LocalDate getFromDate() {return fromDate;}

    public LocalDate getTruDate() {return truDate;}

    public int getCvv() {return cvv;}

    public boolean isActive() {return isActive;}
}

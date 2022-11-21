package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.context.annotation.Bean;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    long id;

    private String cardHolder;
    private CardType type;
    private CardColor color;
    private String number;
    private LocalDate fromDate;
    private LocalDate truDate;
    private int cvv;
    private boolean isActive;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;


    public Card() {}

    public Card(CardType type, CardColor color, String number, LocalDate fromDate, LocalDate truDate, int cvv, Client client, boolean isActive) {
        this.cardHolder = client.getFirst_name() + " " + client.getLast_name();
        this.type = type;
        this.color = color;
        this.number = number;
        this.fromDate = fromDate;
        this.truDate = truDate;
        this.cvv = cvv;
        this.client = client;
        this.isActive = isActive;
    }

    public long getId() {return id;}

    public String getCardHolder() {return cardHolder;}
    public void setCardHolder(String cardHolder) {this.cardHolder = cardHolder;}

    public CardType getType() {return type;}
    public void setType(CardType type) {this.type = type;}

    public CardColor getColor() {return color;}
    public void setColor(CardColor color) {this.color = color;}

    public String  getNumber() {return number;}
    public void setNumber(String number) {this.number = number;}

    public LocalDate getFromDate() {return fromDate;}
    public void setFromDate(LocalDate fromDate) {this.fromDate = fromDate;}

    public LocalDate getTruDate() {return truDate;}
    public void setTruDate(LocalDate truDate) {this.truDate = truDate;}

    public int getCvv() {return cvv;}
    public void setCvv(int cvv) {this.cvv = cvv;}

    public Client getClient() {return client;}
    public void setClient(Client client) {this.client = client;}

    public boolean isActive() {return isActive;}
    public void setActive(boolean active) {isActive = active;}
}

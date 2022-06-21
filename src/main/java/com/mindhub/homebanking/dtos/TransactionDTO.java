package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.models.Transaction;

import java.time.LocalDateTime;

public class TransactionDTO {

    // Propiedades -------------------------------------------------------------------------//
    private long id;
    private double amount;
    private String description;
    private TransactionType type;
    private LocalDateTime date;



    // Constructores -------------------------------------------------------------------------//
    public TransactionDTO(){}

    public TransactionDTO (Transaction transactions) {
        this.id = transactions.getId();
        this.amount = transactions.getAmount();
        this.description = transactions.getDescription();
        this.type = transactions.getType();
        this.date = transactions.getDate();
    }


    // Getter y Setters -------------------------------------------------------------------------//
    public long getId() {return id;}

    public double getAmount() {return amount;}
    public void setAmount(double amount) {this.amount = amount;}

    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}

    public TransactionType getType() {return type;}
    public void setType(TransactionType type) {this.type = type;}

    public LocalDateTime getDate() {return date;}
    public void setDate(LocalDateTime date) {this.date = date;}
}


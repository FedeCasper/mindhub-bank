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
    private TransactionType transactionType;
    private LocalDateTime creationDate;


    // Constructores -------------------------------------------------------------------------//
    public TransactionDTO(){}

    public TransactionDTO (Transaction transactions) {
        this.id = transactions.getId();
        this.amount = transactions.getAmount();
        this.description = transactions.getDescription();
        this.transactionType = transactions.getTransactionType();
        this.creationDate = transactions.getCreationDate();
    }


    // Getter y Setters -------------------------------------------------------------------------//
    public long getId() {return id;}

    public double getAmount() {return amount;}

    public String getDescription() {return description;}

    public TransactionType getTransactionType() {return transactionType;}

    public LocalDateTime getCreationDate() {return creationDate;}

}


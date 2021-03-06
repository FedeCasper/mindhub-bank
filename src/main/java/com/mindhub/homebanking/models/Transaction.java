package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
public class Transaction {

    // Propiedades -------------------------------------------------------------------------//
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private double amount;
    private String description;
    private TransactionType type;
    private LocalDateTime date;


    // Relaciones -------------------------------------------------------------------------//
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private Account account;


    // Constructores -------------------------------------------------------------------------//
    public Transaction(){}

    public Transaction(double amount, String description, TransactionType type, LocalDateTime date, Account account) {
        this.amount = amount;
        this.description = description;
        this.type = type;
        this.date = date;
        this.account = account;
    }


    // Getters & Setters -------------------------------------------------------------------------//
    public long getId() {return id;}

    public double getAmount() {return amount;}
    public void setAmount(double amount) {this.amount = amount;}

    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}

    public TransactionType getType() {return type;}
    public void setType(TransactionType type) {this.type = type;}

    public LocalDateTime getDate() {return date;}
    public void setDate(LocalDateTime date) {this.date = date;}

    public Account getAccount() {return account;}
    public void setAccount(Account account) {this.account = account;}

}

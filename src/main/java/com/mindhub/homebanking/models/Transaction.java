package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "transferencias")
public class Transaction {

    // Propiedades -------------------------------------------------------------------------//
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private long id;

    private double amount;
    @Column(length = 255)
    private String description;
    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", length = 255)
    private TransactionType transactionType;
    private LocalDateTime creationDate;


    // Relaciones -------------------------------------------------------------------------//
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private Account account;


    // Constructores -------------------------------------------------------------------------//
    public Transaction(){}

    public Transaction(double amount, String description, TransactionType transactionType, LocalDateTime creationDate, Account account) {
        this.amount = amount;
        this.description = description;
        this.transactionType = transactionType;
        this.creationDate = creationDate;
        this.account = account;
    }


    // Getters & Setters -------------------------------------------------------------------------//
    public long getId() {return id;}

    public double getAmount() {return amount;}
    public void setAmount(double amount) {this.amount = amount;}

    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}

    public TransactionType getTransactionType() {return transactionType;}
    public void setTransactionType(TransactionType transactionType) {this.transactionType = transactionType;}

    public LocalDateTime getCreationDate() {return creationDate;}
    public void setCreationDate(LocalDateTime date) {this.creationDate = date;}

    public Account getAccount() {return account;}
    public void setAccount(Account account) {this.account = account;}

}

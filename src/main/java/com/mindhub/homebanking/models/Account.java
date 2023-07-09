package com.mindhub.homebanking.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.ManyToOne;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Entity;
import javax.persistence.FetchType;

@Entity
@Table(name = "account")
public class Account {

    // Propiedades -------------------------------------------------------------------------//
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private long id;

    private String accountNumber;
    private LocalDateTime creationDate;
    private double balance;


    // Relaciones -------------------------------------------------------------------------//
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToMany(mappedBy="account", fetch=FetchType.EAGER)
    Set<Transaction> setOfTransactions = new HashSet<>();

    // Constructores -------------------------------------------------------------------------//
    public Account(){}

    public Account(String accountNumber, LocalDateTime creationDate, double balance, Client client) {
        this.accountNumber = accountNumber;
        this.creationDate = creationDate;
        this.balance = balance;
        this.client = client;
    }


    // Getters y Setters -------------------------------------------------------------------------//
    public long getId() {return id;}

    public String getAccountNumber() {return accountNumber;}
    public void setAcountNumber(String number) {this.accountNumber = number;}

    public LocalDateTime getCreationDate() {return creationDate;}
    public void setCreationDate(LocalDateTime creationDate) {this.creationDate = creationDate;}

    public double getBalance() {return balance;}
    public void setBalance(double balance) {this.balance = balance;} // Al ser void no se tiene que guardar, no retorna nada, solo hace algo

    @JsonIgnore
    public Client getClient() {return client;}
    public void setClient(Client client) {this.client = client;}

    public Set<Transaction> getTransactions() {return setOfTransactions;}
}

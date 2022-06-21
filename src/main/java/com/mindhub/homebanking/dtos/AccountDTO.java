package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Account;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


public class AccountDTO { // Nunca se usan las anotaciones (@) en un DTO


    // Propiedades -------------------------------------------------------------------------//
    private long id;
    private String number;
    private LocalDateTime creationDate;
    private double balance;
    private Set<TransactionDTO> transactions = new HashSet<>();


    // Constructores -------------------------------------------------------------------------//
    public AccountDTO(){}

    // En un DTO siempre se usa el objeto de tipo originario para capturar la info de la clase que vayamos a usar en un futuro
    public AccountDTO(Account account) {
        this.id = account.getId();
        this.number = account.getNumber();
        this.creationDate = account.getCreationDate();
        this.balance = account.getBalance();
        this.transactions = account.getTransactions().stream().map(transaction -> new TransactionDTO(transaction)).collect(Collectors.toSet());
    }


    // Getter y Setters -------------------------------------------------------------------------//

    public long getId() {return id;}

    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {this.number = number;}

    public LocalDateTime getCreationDate() {
        return creationDate;
    }
    public void setCreationDate(LocalDateTime creationDate) {this.creationDate = creationDate;}

    public double getBalance() {
        return balance;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Set<TransactionDTO> getTransactions() {return transactions;}
}

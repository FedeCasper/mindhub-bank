package com.mindhub.homebanking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;

@Entity
public class Client {

    // Propiedades -------------------------------------------------------------------------//
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private String first_name;
    private String last_name;
    private String email;
    private String password;


    // Relaciones ---------------------------------------------------------------------------//

    @OneToMany(mappedBy="client", fetch=FetchType.EAGER) //asocia a client que esta en account
    private Set<Account> accounts = new HashSet<>();

    @OneToMany(mappedBy="client", fetch=FetchType.EAGER)
    private Set<ClientLoan> clientLoans = new HashSet<>();

    @OneToMany(mappedBy="client", fetch=FetchType.EAGER)
    private Set<Card> cards = new HashSet<>();


    // Constructores -------------------------------------------------------------------------//

    public Client (){}

    public Client(String first_name, String last_name, String email, String password) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
    }


    // Getter y Setters -------------------------------------------------------------------------//

    public long getId() {return id;}

    public String getFirst_name (){return first_name;}
    public void setFirst_name(String first_name){this.first_name = first_name;}

    public String getLast_name (){return last_name;}
    public void setLast_name(String last_name){this.last_name = last_name;}

    public String getEmail (){return email;}
    public void setEmail(String email){this.email = email;}

    public Set<Account> getAccounts(){return accounts;}
    public void addAccount(Account account) {
        account.setClient(this);
        accounts.add(account);
    }

    public Set<ClientLoan> getClientLoans(){return clientLoans;}
    public void addClientLoan(ClientLoan clientLoan) {
        clientLoan.setClient(this);
        clientLoans.add(clientLoan);
    }
    @JsonIgnore
    public List<Loan> getLoans (){
        return clientLoans.stream().map(clientLoan -> clientLoan.getLoan()).collect(Collectors.toList());
    }

    public Set<Card> getCards() {return cards;}

    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}
}

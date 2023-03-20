package com.mindhub.homebanking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mindhub.homebanking.anotations.CustomUnused;
import com.mindhub.homebanking.enums.AnotationsType;
import com.mindhub.homebanking.enums.State;
import net.bytebuddy.asm.Advice;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;

@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "native")
    @SequenceGenerator(name = "native")

    private long id;
    private String first_name;
    private String last_name;
    private String email;
    private String password;
    private Role role;


    // Relaciones ---------------------------------------------------------------------------//

    @OneToMany(mappedBy="client", fetch=FetchType.EAGER) //asocia a client que esta en account
    private Set<Account> accounts = new HashSet<>();

    @OneToMany(mappedBy="client", fetch=FetchType.EAGER)
    private Set<ClientLoan> clientLoans = new HashSet<>();

    @OneToMany(mappedBy="client", fetch=FetchType.EAGER)
    private Set<Card> cards = new HashSet<>();


    // Constructores -------------------------------------------------------------------------//

    public Client (){}

    public Client(String first_name, String last_name, String email, String password, Role role) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
        this.role = role;
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

    @CustomUnused(anotationsType = AnotationsType.METHODS, state = State.UNUSED)
    /*public void addClientLoan(ClientLoan clientLoan) {
        clientLoan.setClient(this);
        clientLoans.add(clientLoan);
    }*/

    @JsonIgnore
    public List<Loan> getLoans (){
        return clientLoans.stream().map(clientLoan -> clientLoan.getLoan()).collect(Collectors.toList());
    }

    public Set<Card> getCards() {return cards;}

    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}

    public Role getRole() {return role;}
    public void setRole(Role role) {this.role = role;}

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", accounts=" + accounts +
                ", clientLoans=" + clientLoans +
                ", cards=" + cards +
                '}';
    }

}

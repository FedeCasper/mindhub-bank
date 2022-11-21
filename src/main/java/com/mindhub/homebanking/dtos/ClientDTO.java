package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.models.Role;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ClientDTO {

    // Propiedades -------------------------------------------------------------------------//

    private long id;
    private String first_name;
    private String last_name;
    private String email;
    private Set<AccountDTO> accounts = new HashSet<>();
    private Set<ClientLoanDTO> clientLoans = new HashSet<>();
    private Set<CardDTO> cards = new HashSet<>();
    private Role role;


    // Constructores -------------------------------------------------------------------------//

    public ClientDTO (){}
    public ClientDTO(Client client) {
        this.id = client.getId();
        this.first_name = client.getFirst_name();
        this.last_name = client.getLast_name();
        this.email = client.getEmail();
        this.role = client.getRole();
        this.accounts = client.getAccounts().stream().map(AccountDTO::new).collect(Collectors.toSet());
        this.clientLoans = client.getClientLoans().stream().map(clientLoan -> new ClientLoanDTO(clientLoan)).collect(Collectors.toSet());
        this.cards = client.getCards().stream().map(card -> new CardDTO(card)).collect(Collectors.toSet());
    }


    // Getter y Setters -------------------------------------------------------------------------//

    public long getId() {return id;}

    public String getFirst_name() {return first_name;}

    public String getLast_name() {return last_name;}

    public String getEmail() {return email;}

    public Set<AccountDTO> getAccounts() {
        return accounts;
    }

    public Set<ClientLoanDTO> getClientLoans() {return clientLoans;}

    public Set<CardDTO> getCards() {return cards;}

    public Role getRole() {return role;}
}

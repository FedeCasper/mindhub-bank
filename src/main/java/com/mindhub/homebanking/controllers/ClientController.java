package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.dtos.ClientLoanDTO;
import com.mindhub.homebanking.errors.MiExcepcionVerificada;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Role;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import static com.mindhub.homebanking.utils.AccountUtils.getRandomAccountNumber;

@RestController
@RequestMapping("/api")
@SuppressWarnings("unused")
public class ClientController {

    @Autowired
    private ClientService clientService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/clients")
    public List<ClientDTO> getClients() {
        return clientService.getClientsDTO();
    }

    @PostMapping("/clients")
    public ResponseEntity<Object> register (@RequestParam String firstName,
                                            @RequestParam String lastName,
                                            @RequestParam String email,
                                            @RequestParam String password) throws MiExcepcionVerificada {

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if (clientService.getClientByEmail(email) !=  null) {
            return new ResponseEntity<>("Email is already in use", HttpStatus.FORBIDDEN);
        }
        if (!email.contains("@")) {
            return new ResponseEntity<>("the email does not contain the @ character", HttpStatus.BAD_REQUEST);
        }
        if (password.length() <= 4) {
            return new ResponseEntity<>("The password is too short", HttpStatus.BAD_REQUEST);
        }
        if(password.length() >= 15){
            return new ResponseEntity<>("The password is too long", HttpStatus.BAD_REQUEST);
        }
        if (firstName.equalsIgnoreCase("admin") && email.toLowerCase().contains("@admin.com")){
            Client admin = new Client(firstName, lastName, email, passwordEncoder.encode(password), Role.ADMIN);
            clientService.saveCLient(admin);
            return new ResponseEntity<>("Admin has been created succesfully", HttpStatus.CREATED);
        }else{
            Client client = new Client(firstName, lastName, email, passwordEncoder.encode(password), Role.CLIENT);
            clientService.saveCLient(client);

            Account account = new Account("VIN" + getRandomAccountNumber(10000000,99999999), LocalDateTime.now(), 0, client );
            accountService.saveAccount(account);
            return new ResponseEntity<>(client, HttpStatus.CREATED);
        }
    }

    @RequestMapping("/clients/current")
    public ClientDTO getClientCurrent (Authentication authentication) {
        return new ClientDTO(clientService.getClientCurrent(authentication));
    }

    @GetMapping("/clients/current/accounts")
    public Set<AccountDTO> getCurrentClientAccounts(Authentication authentication) {
        Client client = clientService.getClientCurrent(authentication);
        return client.getAccounts().stream().map(AccountDTO::new).collect(Collectors.toSet());
    }

    @GetMapping("/clients/current/cards")
    public Set<CardDTO> getCurrentClientCards(Authentication authentication) {
        Client client = clientService.getClientCurrent(authentication);
        return client.getCards().stream().map(CardDTO::new).collect(Collectors.toSet());
    }

    @GetMapping("/clients/current/clientLoans")
    public Set<ClientLoanDTO> getCurrentClientClientLoans (Authentication authentication) {
        Client client = clientService.getClientCurrent(authentication);
        return client.getClientLoans().stream().map(ClientLoanDTO::new).collect(Collectors.toSet());
    }
}





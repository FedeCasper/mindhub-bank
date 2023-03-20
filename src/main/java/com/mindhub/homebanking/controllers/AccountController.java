package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static com.mindhub.homebanking.utils.AccountUtils.getRandomAccountNumber;


@RestController
@RequestMapping("/api")
@SuppressWarnings("unused")
public class AccountController {

    @Autowired
    ClientService clientService;
    @Autowired
    AccountService accountService;


    @GetMapping("/accounts")
    public List<AccountDTO> getAccounts(){
        return accountService.getAccountsDTO();
    }

    @GetMapping("accounts/{id}")
    public AccountDTO getAccount(@PathVariable Long id) {
        return accountService.getAccountDTOById(id);
    }

    @PostMapping("/clients/current/accounts")
    public ResponseEntity<Object> createAccount (Authentication authentication ) {
        Client client = clientService.getClientCurrent(authentication);
        if (client.getAccounts().size() >= 3){
            return new ResponseEntity<>("The maximum number of accounts has been reached", HttpStatus.FORBIDDEN);
        }
        Account account = new Account("VIN" + getRandomAccountNumber(10000000,99999999), LocalDateTime.now(), 0, client );
        accountService.saveAccount(account);
        return new ResponseEntity<>("The account was created successfully", HttpStatus.CREATED);
    }
}

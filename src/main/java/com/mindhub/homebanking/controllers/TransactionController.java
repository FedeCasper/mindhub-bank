package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.TransactionDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

import static com.mindhub.homebanking.models.TransactionType.CREDITO;
import static com.mindhub.homebanking.models.TransactionType.DEBITO;

@RestController
@RequestMapping("/api")
@SuppressWarnings("unused")
public class TransactionController {

    @Autowired
    ClientService clientService;
    @Autowired
    AccountService accountService;
    @Autowired
    TransactionService transactionService;


    @GetMapping("/transaction")
    public List<TransactionDTO> getTransaction(){
        return transactionService.getTransactionsDTO();
    }

    @GetMapping("transaction/{id}")
    public TransactionDTO getTransaction(@PathVariable Long id) {
        return transactionService.getTransactionById(id);
    }

    @Transactional
    @PostMapping("/transactions")
    public ResponseEntity<Object> createTransaction (
            @RequestParam(required = false) Double amount,
            @RequestParam String description,
            @RequestParam String sourceAccount,
            @RequestParam String destinationAccount,
            Authentication authentication ){

        Client client = clientService.getClientCurrent(authentication);
        Account accountSource = accountService.getAccountByNumber(sourceAccount);
        Account accountDestination = accountService.getAccountByNumber(destinationAccount);

        if (amount == null || description.isEmpty() || sourceAccount.isEmpty() || destinationAccount.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if (sourceAccount.equals(destinationAccount)) {
            return new ResponseEntity<>("The source account is the same as the destination account", HttpStatus.FORBIDDEN);
        }
        if (accountService.getAccountByNumber(sourceAccount) ==  null) {
            return new ResponseEntity<>("Source account does not exist", HttpStatus.FORBIDDEN);
        }
        if(!client.getAccounts().contains(accountSource)){
            return new ResponseEntity<>("The source account does not belong to the logged in client", HttpStatus.FORBIDDEN);
        }
        if (accountService.getAccountByNumber(destinationAccount) ==  null) {
            return new ResponseEntity<>("Destination account does not exist", HttpStatus.FORBIDDEN);
        }
        if( amount > accountSource.getBalance()){
            return new ResponseEntity<>("You do not have sufficient funds for this operation", HttpStatus.FORBIDDEN);
        }
        Transaction debitTransaction = new Transaction( -amount, description, DEBITO, LocalDateTime.now(), accountSource);
        transactionService.saveTransaction(debitTransaction);
        Transaction creditTransaction = new Transaction( amount, description, CREDITO, LocalDateTime.now().plusMonths(1), accountDestination);
        transactionService.saveTransaction(creditTransaction);

        accountSource.setBalance(accountSource.getBalance() - amount);
        accountService.saveAccount(accountSource);

        accountDestination.setBalance(accountDestination.getBalance() + amount);
        accountService.saveAccount(accountDestination);

        return new ResponseEntity<>("The transaction has been realized",HttpStatus.CREATED);
    }

}


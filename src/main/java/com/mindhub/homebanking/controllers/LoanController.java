package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientLoanDTO;
import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.mindhub.homebanking.models.TransactionType.CREDITO;

@RestController
@RequestMapping("/api")
public class LoanController {

    @Autowired
    ClientService clientService;
    @Autowired
    AccountService accountService;
    @Autowired
    TransactionService transactionService;
    @Autowired
    LoanService loanService;
    @Autowired
    ClientLoanService clientLoanService;


    @GetMapping("/loans")
    public List<LoanDTO> getLoans() {
        return loanService.getLoansDTO();
    }

    @Transactional
    @PostMapping("/loans")
    public ResponseEntity<Object> createLoan(@RequestBody LoanApplicationDTO loanApplicationDTO, Authentication authentication){
        Loan loan =  loanService.findLoanById(loanApplicationDTO.getId());
        Account account = accountService.getAccountByNumber(loanApplicationDTO.getDestinationAccountNumber());
        Client client = clientService.getClientCurrent(authentication);

        if (loanApplicationDTO.getAmount() == 0 || loanApplicationDTO.getPayment() == 0) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        if (loanService.findLoanById(loanApplicationDTO.getId()) == null) {
            return new ResponseEntity<>("This kind of loan doesn't exist", HttpStatus.FORBIDDEN);
        }

        if(loanApplicationDTO.getAmount() > loan.getMaxAmount()){
            return new ResponseEntity<>("The amount requested is is greater than the amount allowed", HttpStatus.FORBIDDEN);
        }

        if(!loan.getPayments().contains(loanApplicationDTO.getPayment())){
            return new ResponseEntity<>("The amount of payments is not allowed in this type of loan", HttpStatus.FORBIDDEN);
        }

        if(account == null){
            return new ResponseEntity<>("The destination account doesn't exist", HttpStatus.FORBIDDEN);
        }

        if(!client.getAccounts().contains(account)){
            return new ResponseEntity<>("The destination account does not belong to the client", HttpStatus.FORBIDDEN);
        }


        ClientLoan clientLoan = new ClientLoan( loanApplicationDTO.getAmount() * 1.20 , loanApplicationDTO.getPayment(), client, loan);
        clientLoanService.saveClientLoan(clientLoan);

        Transaction creditTransaction = new Transaction( loanApplicationDTO.getAmount(), loan.getName() + " Loan approved" , CREDITO, LocalDateTime.now(), account);
        transactionService.saveTransaction(creditTransaction);

        account.setBalance(account.getBalance() + loanApplicationDTO.getAmount());
        accountService.saveAccount(account);

        return new ResponseEntity<>("The loan has been succesfully requested", HttpStatus.CREATED);
    }
}

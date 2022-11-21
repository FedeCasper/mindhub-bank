package com.mindhub.homebanking;

import com.mindhub.homebanking.controllers.CardController;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import com.mindhub.homebanking.services.CardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/*@DataJpaTest*/ // Ya que mi base de datos es H2 por ahora no puedo usar esta anotación
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RepositoriesTest {

    @Autowired
    ClientRepository clientRepository;
    @Autowired
    LoanRepository loanRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    CardRepository cardRepository;
    @Autowired
    TransactionRepository transactionRepository;
    /*@Autowired
    CardService cardService;
    @Autowired
    CardController cardController;*/
    /*@MockBean
    PasswordEncoder passwordEncoder;*/


    // Loan Repository -------------------------------------------------------------------------//
    @Test
    public void existLoans(){
        List<Loan> loans = loanRepository.findAll();
        assertThat(loans,is(not(empty())));
    }
    @Test
    public void existPropertyPersonal(){
        List<Loan> loans = loanRepository.findAll();
        assertThat(loans, hasItem(hasProperty("name", is("Personal"))));
    }
    @Test
    public void existPropertyAutomotive(){
        List<Loan> loans = loanRepository.findAll();
        assertThat(loans, hasItem(hasProperty("name", is("Automotive"))));
    }
    @Test
    public void existPropertyMortgage(){
        List<Loan> loans = loanRepository.findAll();
        assertThat(loans, hasItem(hasProperty("name", is("Mortgage"))));
    }
    @Test
    public void PropertyTypeAutomotive(){
        List<Loan> loans = loanRepository.findAll();
        assertThat(loans, hasItem(hasProperty("name", isA(String.class))));
    }
    @Test
    public void PropertyTypeMaxAmount(){
        List<Loan> loans = loanRepository.findAll();
        assertThat(loans, hasItem(hasProperty("maxAmount", isA(int.class))));
    }
    @Test
    public void PropertyTypePayments(){
        List<Loan> loans = loanRepository.findAll();
        assertThat(loans, hasItem(hasProperty("payments", isA(List.class))));
    }




    @Test
    public void existAccounts(){
        List<Account> accounts = accountRepository.findAll();
        assertThat(accounts,is(not(empty())));
    }
    @Test
    public void existAccountNumber(){
        List<Account> account = accountRepository.findAll();
        assertThat(account,hasItem(hasProperty("number", is("VIN001"))));
    }
    @Test
    public void existCards(){
        List<Card> cards = cardRepository.findAll();
        assertThat(cards,is(not(empty())));
    }
    /*@Test
    public void existCVV (){
        List<Card> cards = cardRepository.findAll();
        assertThat(cards,hasItem(hasProperty("cvv", is(117))));
    }*/
    @Test
    public void existeSilverCard(){
        List<Card> cards = cardRepository.findAll();
        assertThat(cards,hasItem(hasProperty("color", is(CardColor.SILVER)))); // Enum: se coloca el tipo de dato y el dato en si
    }
    @Test
    public void existeGoldCard(){
        List<Card> cards = cardRepository.findAll();
        assertThat(cards,hasItem(hasProperty("color", is(CardColor.GOLD))));
    }
    @Test
    public void existeTitaniumCard(){
        List<Card> cards = cardRepository.findAll();
        assertThat(cards,hasItem(hasProperty("color", is(CardColor.TITANIUM))));
    }
    @Test
    public void existClient (){
        List<Client> clients = clientRepository.findAll();
        assertThat(clients,is(not(empty())));
    }
    @Test
    public void existClientMelba(){
    List<Client> clients = clientRepository.findAll();
    assertThat(clients,hasItem(hasProperty("first_name", is("Melba"))));
    }
    @Test
    public void existTransaction (){
        List<Transaction> transactions = transactionRepository.findAll();
        assertThat(transactions,is(not(empty())));
    }

    /*@Test
    void deleteClient2(){
        cardController.deleteCard(2L);
    }
    @Test
    void deleteClient3(){
        cardController.deleteCard(3L);
    }
    @Test
    void deleteClient4(){
        cardController.deleteCard(4L);
    }

    /*@Test
    public Card getCardById(long id){
        Card card = cardRepository.findById(id);
        assertThat(card, is(not(empty())));
    }*/

}

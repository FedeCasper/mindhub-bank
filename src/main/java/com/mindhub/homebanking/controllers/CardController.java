package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.services.CardService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.utils.CardUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.mindhub.homebanking.utils.CardUtils.getRandomCardNumber;
import static com.mindhub.homebanking.utils.CardUtils.getRandomCvvNumber;


@RestController
@RequestMapping("/api")
public class CardController {

    @Autowired
    ClientService clientService;
    @Autowired
    CardService cardService;

    @GetMapping("/cards")
    public Set<CardDTO> getCardsDTO(){
        return cardService.getCardsDTO();
    }

    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> createCard (@RequestParam CardType type, @RequestParam CardColor color, Authentication authentication){

        Client client = clientService.getClientCurrent(authentication);
        String cardHolder = client.getFirst_name() + " " + client.getLast_name();
        List<Card> fiteredCards = client.getCards().stream().filter(card -> card.getType() == type).collect(Collectors.toList());

        if(fiteredCards.size() >= 3){
            return new ResponseEntity<>("The maximum cards has been reached", HttpStatus.FORBIDDEN);}

        String cardNumber = getRandomCardNumber();
        int cvv = getRandomCvvNumber();

        Card card = new Card(type, color, cardNumber, LocalDate.now(), LocalDate.now().plusYears(5),cvv, client, true);
        cardService.saveCard(card);
        return new ResponseEntity<>("Card created successfully", HttpStatus.CREATED);

    }

    @GetMapping("/clients/current/cards/{id}")
    public Card getCardDTOById (@PathVariable Long id) {
        return cardService.getCardById(id);
    }

    /*@PatchMapping("/clients/current/cards/{id}")
    public ResponseEntity<Object> deleteCard (@PathVariable (value = "id") Long id){
        cardService.deleteCard(id);
        Client client = clientService.findClientById(id);
        return new ResponseEntity<>(client, HttpStatus.OK);
    }*/

    @GetMapping("/clients/current/cards/colors")
    public List<CardColor> getCardColor(){
       return Arrays.stream(CardColor.values()).collect(Collectors.toList());
    }

}

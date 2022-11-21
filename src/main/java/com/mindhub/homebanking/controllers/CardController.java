package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.services.CardService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.utils.CardUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.mindhub.homebanking.utils.CardUtils.getRandomCardNumber;

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
        int cvv = CardUtils.getRandonNumberCVV();

        Card card = new Card(type, color, cardNumber, LocalDate.now(), LocalDate.now().plusYears(5),cvv, client, true);
        cardService.saveCard(card);
        return new ResponseEntity<>("Card created succesfully", HttpStatus.CREATED);

    }

    @GetMapping("/clients/current/cards/{id}")
    public Card getCardDTOById (@PathVariable Long id) {
        return cardService.getCardById(id);
    }

    @DeleteMapping("/clients/current/cards/{id}")
    public void deleteCard (@PathVariable (value = "id") Long id){
        cardService.deleteCard(id);
    }
}

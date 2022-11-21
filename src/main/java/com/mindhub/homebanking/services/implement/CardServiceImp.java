package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CardServiceImp implements CardService {

    @Autowired
    CardRepository cardRepository;

    @Override
    public Set<CardDTO> getCardsDTO() {
        return cardRepository.findAll().stream().map(CardDTO::new).collect(Collectors.toSet());
    }
    @Override
    public void saveCard(Card card) {
        cardRepository.save(card);
    }

    @Override
    public Card getCardById(Long id) {
        return cardRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteCard (long id){
        Card card = cardRepository.findById(id);
        card.setActive(false);
    }
}

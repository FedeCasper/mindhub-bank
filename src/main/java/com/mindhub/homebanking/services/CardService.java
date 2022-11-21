package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.Card;

import java.util.Set;

public interface CardService {

    Set<CardDTO> getCardsDTO();
    void saveCard (Card card);
    Card getCardById(Long id);
    void deleteCard (long id);

}

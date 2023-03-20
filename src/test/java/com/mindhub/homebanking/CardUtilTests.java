package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.services.CardService;
import com.mindhub.homebanking.services.implement.CardServiceImp;
import com.mindhub.homebanking.utils.CardUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
public class CardUtilTests {

    @Test
    public void cardNumberIsCreated(){
        String cardNumber = CardUtils.getRandomCardNumber();
        assertThat(cardNumber,is(not(emptyOrNullString())));
    }
    @Test
    public void cvvNumberIsCreated(){
        int cvv = CardUtils.getRandomCvvNumber();
        assertThat(cvv,is(not(nullValue())));
    }

}

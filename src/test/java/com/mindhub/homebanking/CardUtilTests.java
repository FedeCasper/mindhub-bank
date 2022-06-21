package com.mindhub.homebanking;

import com.mindhub.homebanking.utils.CardUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

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
        int cvv = CardUtils.getRandonNumberCVV();
        assertThat(cvv,is(not(nullValue())));
    }

}

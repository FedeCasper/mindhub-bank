package com.mindhub.homebanking;

import com.mindhub.homebanking.errors.MiExcepcionVerificada;
import com.mindhub.homebanking.utils.AccountUtils;
import com.mindhub.homebanking.utils.CardUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
public class AccountUtilTests {

    @Test
    public void accountNumberIsCreated() throws MiExcepcionVerificada {
        int accountNumber = AccountUtils.getRandomAccountNumber(10000000,99999999);
        assertThat(accountNumber,is(not(nullValue())));
    }

}

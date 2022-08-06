package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.dtos.ClientLoanDTO;
import com.mindhub.homebanking.models.ClientLoan;

import java.util.List;

public interface ClientLoanService {

    void saveClientLoan (ClientLoan clientLoan);

}

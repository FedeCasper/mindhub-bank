package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Client;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ClientService {

    List<ClientDTO> getClientsDTO();
    Client getClientCurrent(Authentication authentication);
    ClientDTO getClientDTO(Long id);
    Client findClientById (Long id);
    void saveCLient (Client client);
    Client getClientByEmail(String email);

}

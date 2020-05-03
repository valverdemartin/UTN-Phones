package edu.utn.TpFinal.service;

import ch.qos.logback.core.net.server.Client;
import edu.utn.TpFinal.controller.ClientsController;
import edu.utn.TpFinal.model.Clients;
import edu.utn.TpFinal.repository.ClientsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientsService {

    private final ClientsRepository clientsRepository;

    @Autowired
    public ClientsService(ClientsRepository clientsRepository) {
        this.clientsRepository = clientsRepository;
    }

    public Clients getClientsById(Integer clientId){
        return clientsRepository.findById(clientId).get();
    }

    public List<Clients> getClients(){
        return clientsRepository.findAll();
    }

    public void addClient(final Clients client){
        clientsRepository.save(client);
    }

    public Clients getClientsByLastName(String lastName){
        return clientsRepository.findByLastName(lastName);
    }
}

package edu.utn.TpFinal.controller;

import edu.utn.TpFinal.Exceptions.*;
import edu.utn.TpFinal.model.Clients;
import edu.utn.TpFinal.model.DTO.ClientUpdateDTO;
import edu.utn.TpFinal.service.ClientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

@Controller
public class ClientsController {

    private final ClientsService clientsService;

    @Autowired
    public ClientsController(final ClientsService clientsService) {
        this.clientsService = clientsService;
    }


    public Clients getClientById(Integer clientId) throws ClientNotExists {
        return clientsService.getClientsById(clientId);

    }

    public Page<Clients> getClients(Pageable pageable){
        return clientsService.getClients(pageable);
    }


    public Clients addClient(Clients client) throws UserDniAlreadyExist, UserNameAlreadyExist {
        return clientsService.addClient(client);
    }


    public Clients updateClient(ClientUpdateDTO client, Integer idClient, boolean active) throws UserDniAlreadyExist, UserNameAlreadyExist, UserAlreadyDeleted, UserAlreadyActive, DeletionNotAllowed, ClientNotExists {
        return clientsService.updateClient(client, idClient, active);
    }


    public Clients deleteClient(Integer clientId) throws UserNotExists,UserAlreadyActive, UserAlreadyDeleted{
        return clientsService.deleteClients(clientId);
    }


    //ToDo 			□ Login user y Log out (borra session 1hs:6min 4/6)
    //ToDo 			□ Consulta de facturas por rango de fecha de user logueado
    //ToDo DTO persona con líneas.


}




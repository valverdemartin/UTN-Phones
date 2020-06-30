package edu.utn.TpFinal.controller;

import edu.utn.TpFinal.Exceptions.*;
import edu.utn.TpFinal.model.Clients;
import edu.utn.TpFinal.model.DTO.UserDTO;
import edu.utn.TpFinal.service.ClientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import javax.validation.ValidationException;

@Controller
public class ClientsController {

    private final ClientsService clientsService;

    @Autowired
    public ClientsController(final ClientsService clientsService) {
        this.clientsService = clientsService;
    }

    public Clients login(String username, String password) throws ValidationException {
        if ((username != null) && (password != null)) {
            return clientsService.login(username, password);
        } else {
            throw new ValidationException("username and password must have a value");
        }
    }

    public Clients getClientById(Integer clientId) throws ClientNotExists {
        return clientsService.getClientsById(clientId);

    }

    public Page<Clients> getClients(Pageable pageable){
        return clientsService.getClients(pageable);
    }


    public Clients addClient(UserDTO client) throws UserDniAlreadyExist, UserNameAlreadyExist {
        return clientsService.addClient(client);
    }

    public Clients updateClient(UserDTO client, Integer idClient, boolean active) throws UserDniAlreadyExist, UserNameAlreadyExist, UserAlreadyDeleted, UserAlreadyActive, DeletionNotAllowed, ClientNotExists {
        return clientsService.updateClient(client, idClient, active);
    }

    public Clients deleteClient(Integer clientId) throws UserNotExists,UserAlreadyActive, UserAlreadyDeleted{
        return clientsService.deleteClients(clientId);
    }

}




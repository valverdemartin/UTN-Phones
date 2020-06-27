package edu.utn.TpFinal.service;

import edu.utn.TpFinal.Exceptions.*;
import edu.utn.TpFinal.model.Clients;
import edu.utn.TpFinal.model.DTO.ClientUpdateDTO;
import edu.utn.TpFinal.repository.ClientsRepository;
import edu.utn.TpFinal.repository.LinesRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class ClientsService{

    private final ClientsRepository clientsRepository;
    private final LinesRespository linesRespository;

    @Autowired
    public ClientsService(ClientsRepository clientsRepository, LinesRespository linesRespository) {
        this.clientsRepository = clientsRepository;
        this.linesRespository = linesRespository;
    }

    public Clients getClientsById(Integer clientId) throws ClientNotExists {
        return clientsRepository.findByIdAndActiveTrue(clientId).orElseThrow(()-> new ClientNotExists());
    }

    public Page<Clients> getClients(Pageable pageable){
        return clientsRepository.findByActiveTrue(pageable);
    }

    public Clients addClient(final Clients client) throws UserDniAlreadyExist, UserNameAlreadyExist {
        if(clientsRepository.existsByDni(client.getDni()))
            throw new UserDniAlreadyExist();
        if(clientsRepository.existsByUserName(client.getUserName()))
            throw new UserNameAlreadyExist();
        return clientsRepository.save(client);
    }

    public Clients updateClient(ClientUpdateDTO newClient, Integer clientID, Boolean active) throws UserDniAlreadyExist, UserNameAlreadyExist, UserAlreadyDeleted, UserAlreadyActive, DeletionNotAllowed, ClientNotExists {
        Clients oldClient = this.clientsRepository.findById(clientID).orElseThrow(()-> new ClientNotExists());
        if(newClient.getActive()){
            if(active)
                this.activeValidationClient(oldClient, true);
        }else{
            throw new DeletionNotAllowed();
        }
        oldClient = this.setUpdates(newClient, oldClient);
        return clientsRepository.save(oldClient);
    }

    public Clients deleteClients(Integer id) throws UserAlreadyDeleted, UserAlreadyActive, UserNotExists {
        Clients client = clientsRepository.findById(id).orElseThrow(()-> new UserNotExists());
        activeValidationClient(client, false);
        client.setActive(false);
        return clientsRepository.save(client);
    }

    public Clients setUpdates(ClientUpdateDTO newClient, Clients oldClient) throws UserDniAlreadyExist, UserNameAlreadyExist {
        this.dniAndUserNameValidations(oldClient, newClient);
        oldClient.setDni(newClient.getDni());
        oldClient.setLastName(newClient.getLastName());
        oldClient.setName(newClient.getName());
        oldClient.setPassword(newClient.getPassword());
        oldClient.setUserName(newClient.getUserName());
        oldClient.setActive(newClient.getActive());
        return oldClient;
    }


    ///////////////////////////VALIDATIONS//////////////////////////////////////////
    public void activeValidationClient(Clients client, Boolean active) throws UserAlreadyDeleted, UserAlreadyActive {
        if(!active && !client.getActive())
            throw new UserAlreadyDeleted();
        if(active && client.getActive())
            throw new UserAlreadyActive();
    }

    public void dniAndUserNameValidations(Clients oldClient, ClientUpdateDTO newClient) throws UserDniAlreadyExist, UserNameAlreadyExist {
        if(clientsRepository.existsByIdNotAndDni(oldClient.getId(), newClient.getDni()))
            throw new UserDniAlreadyExist();
        if(clientsRepository.existsByIdNotAndUserName(oldClient.getId(), newClient.getUserName()))
            throw new UserNameAlreadyExist();
    }
    ///////////////////////////END VALIDATIONS//////////////////////////////////

}



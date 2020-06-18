package edu.utn.TpFinal.service;

import edu.utn.TpFinal.Exceptions.*;
import edu.utn.TpFinal.Projections.DurationByMonth;
import edu.utn.TpFinal.Projections.FavouriteCall;
import edu.utn.TpFinal.Projections.UserCalls;
import edu.utn.TpFinal.model.Clients;
import edu.utn.TpFinal.model.Lines;
import edu.utn.TpFinal.repository.ClientsRepository;
import edu.utn.TpFinal.repository.LinesRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


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

    public Clients updateClient(Clients client, Boolean active) throws UserNotExists, UserDniAlreadyExist, UserNameAlreadyExist, UserAlreadyDeleted, UserAlreadyActive, DeletionNotAllowed {
        //Clients client = clientsRepository.findById(clientId).orElseThrow(() -> new UserNotExists(HttpStatus.BAD_REQUEST));
        if(!clientsRepository.existsById(client.getId()))
                throw new UserNotExists();
        if(client.getActive()){
            if(active)
                activeValidationClient(client.getId(), true);
        }else{
            throw new DeletionNotAllowed();
        }
        dniAndUserNameValidations(client);
        return clientsRepository.save(client);
    }

    public Clients deleteClients(Integer id) throws UserAlreadyDeleted, UserAlreadyActive, UserNotExists {
        Clients client = clientsRepository.findById(id).orElseThrow(()-> new UserNotExists());
        activeValidationClient(id, false);
        client.setActive(false);
        return clientsRepository.save(client);
    }

    ///////////////////////////VALIDATIONS//////////////////////////////////////////
    public void activeValidationClient(Integer id, Boolean active) throws UserAlreadyDeleted, UserAlreadyActive {
        Clients client = clientsRepository.findById(id).get();
        if(!active && !client.getActive())
            throw new UserAlreadyDeleted();
        if(active && client.getActive())
            throw new UserAlreadyActive();
    }

    public void dniAndUserNameValidations(Clients client) throws UserDniAlreadyExist, UserNameAlreadyExist {
        if(clientsRepository.existsByIdNotAndDni(client.getId(), client.getDni()))
            throw new UserDniAlreadyExist();
        if(clientsRepository.existsByIdNotAndUserName(client.getId(), client.getUserName()))
            throw new UserNameAlreadyExist();
    }
    ///////////////////////////END VALIDATIONS//////////////////////////////////

    /////////////////////////////Practica Examen////////////////////////////////

    public List<UserCalls> getCallsGreaterThan(Integer clientId, Double price) throws UserNotExists {
        if(!clientsRepository.existsById(clientId)){
            //throw new UserNotExists(HttpStatus.BAD_REQUEST);
            throw new UserNotExists();
        }
        return clientsRepository.getCallsGreaterThan(clientId, price);
    }

    public FavouriteCall favouriteCall(Integer idLine){
        Lines line = linesRespository.findById(idLine).get();
        return clientsRepository.favouriteCall(idLine,line.getClient().getId(),line.getPhoneNumber());
    }

    public DurationByMonth getDurationByMont(Integer idUser, Integer selectedMonth) throws UserNotExists {
        if(!clientsRepository.existsById(idUser)){
            //throw new UserNotExists(HttpStatus.BAD_REQUEST);
            throw new UserNotExists();
        }
        return clientsRepository.getDurationByMont(idUser, selectedMonth);
    }


    ////////////////////////////////////////////////////////////////////////////
}



package edu.utn.TpFinal.service;

import edu.utn.TpFinal.Exceptions.UserNotExistException;
import edu.utn.TpFinal.Projections.FavouriteCall;
import edu.utn.TpFinal.Projections.DurationByMonth;
import edu.utn.TpFinal.model.Clients;
import edu.utn.TpFinal.model.Lines;
import edu.utn.TpFinal.repository.ClientsRepository;
import edu.utn.TpFinal.repository.LinesRespository;
import org.springframework.beans.factory.annotation.Autowired;
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
        return clientsRepository.findByLastName(lastName).get();
    }///// agregar exception



    public void putActive(Integer clientId) throws UserNotExistException {
        Clients client = clientsRepository.findById(clientId).orElseThrow(UserNotExistException::new);
        client.setActive(Boolean.TRUE);
        clientsRepository.save(client);
    }

    public FavouriteCall favouriteCall(Integer idLine){
        Lines line = linesRespository.findById(idLine).get();
        return clientsRepository.favouriteCall(idLine,line.getClient().getId(),line.getPhoneNumber());
    }

    public DurationByMonth getDurationByMont(Integer idLine, Integer selectedMonth){
        return clientsRepository.getDurationByMont(idLine, selectedMonth);
    }
}

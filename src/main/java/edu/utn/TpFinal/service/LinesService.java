package edu.utn.TpFinal.service;

import edu.utn.TpFinal.Exceptions.*;
import edu.utn.TpFinal.Projections.UserLine;
import edu.utn.TpFinal.model.Cities;
import edu.utn.TpFinal.model.Clients;
import edu.utn.TpFinal.model.Lines;
import edu.utn.TpFinal.repository.CitiesRepository;
import edu.utn.TpFinal.repository.ClientsRepository;
import edu.utn.TpFinal.repository.LinesRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;



@Service

public class LinesService {

    private LinesRespository linesRespository;
    private ClientsRepository clientsRepository;
    private CitiesRepository citiesRepository;
    private ClientsService clientsService;

    @Autowired
    public LinesService(LinesRespository linesRespository, ClientsService clientsService, CitiesRepository citiesRepository, ClientsRepository clientsRepository) {
        this.linesRespository = linesRespository;
        this.clientsService = clientsService;
        this.citiesRepository = citiesRepository;
        this.clientsRepository = clientsRepository;
    }

    public Lines addLine(final Lines line) throws ClientNotExists, InvalidType, InvalidPrefix, InvalidStatus, CityNotExists, InvalidPhoneNumber {
        verifyLine(line);
        return linesRespository.save(line);
    }

    public Page<Lines> getLines(Pageable pageable, Lines. Status status){
        return linesRespository.findByStatus(pageable, status);
    }

    public Lines findById(Integer lineId) throws LineNotExists {
        return linesRespository.findById(lineId).orElseThrow(() -> new LineNotExists());}

    public UserLine getLineByClient(Integer clientId, Integer lineId) throws ClientNotExists, LineNotExists {
        verifyClientAndLine(clientId, lineId);
        return linesRespository.findByIDAndByClient(lineId,clientId);
    }

    public Lines updateLine(Lines line, Integer clientId, Integer lineId) throws LineNotExists, ClientNotExists, InvalidPhoneNumber, CityNotExists, InvalidPrefix, InvalidStatus, InvalidType, DeletionNotAllowed {
        verifyClientAndLine(clientId, lineId);
        Clients client = clientsRepository.findById(clientId).get();
        line.setId(lineId);
        line.setClient(client);
        if(linesRespository.existsByPhoneNumberAndClientNot(line.getPhoneNumber(),client))//revisar
            throw new InvalidPhoneNumber();
        verifyCityPrefixAndPhoneNumber(line.getCity().getId(), line.getPhoneNumber());//ver prefix
        if(line.getStatus().equals(Lines.Status.CANCELLED))
            throw new DeletionNotAllowed();
        verifyStatusAndType(line.getStatus(), line.getType());
        return linesRespository.save(line);
    }

    public Lines deleteLine(Integer clientId, Integer lineId) throws LineNotExists, ClientNotExists {
        verifyClientAndLine(clientId, lineId);
        Lines line = findById(lineId);
        line.setStatus(Lines.Status.CANCELLED);
        return linesRespository.save(line);
    }

    /////////////////////////////////Validations//////////////////////////////////////////////////
    public void verifyClientAndLine(Integer clientId, Integer lineId) throws ClientNotExists, LineNotExists {
        clientsService.getClientsById(clientId);
        if(!linesRespository.existsByIdAndClient(lineId, clientsRepository.findById(clientId).get()))
            throw new LineNotExists();
    }

    public Boolean existsByLineNumber(String lineNumber){
        return linesRespository.existsByPhoneNumber(lineNumber);
    }

    public void verifyLine(Lines line) throws ClientNotExists, CityNotExists, InvalidPrefix, InvalidType, InvalidStatus, InvalidPhoneNumber {
        Clients client = clientsService.getClientsById(line.getClient().getId());
        if(existsByLineNumber(line.getPhoneNumber()))
            throw new InvalidPhoneNumber();
        verifyCityPrefixAndPhoneNumber(line.getCity().getId(), line.getPhoneNumber());
        verifyStatusAndType(line.getStatus(), line.getType());
    }

    public void verifyStatusAndType(Lines.Status status, Lines.Type type) throws InvalidStatus, InvalidType {
        if(!status.getClass().isAssignableFrom(Lines.Status.class))
            throw new InvalidStatus();
        if(!type.getClass().isAssignableFrom(Lines.Type.class))
            throw new InvalidType();
    }
    public void verifyCityPrefixAndPhoneNumber(Integer cityId, String phoneNumber) throws CityNotExists, InvalidPrefix {
        Cities city = citiesRepository.findById(cityId).orElseThrow(()->new CityNotExists());
        String prefix = String.valueOf(city.getPrefix());
        if(!phoneNumber.startsWith(prefix))
            throw new InvalidPrefix();
    }
    /////////////////////////////////End Validations//////////////////////////////////////////////////
}

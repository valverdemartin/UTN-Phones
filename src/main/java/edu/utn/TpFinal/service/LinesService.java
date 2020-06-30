package edu.utn.TpFinal.service;

import edu.utn.TpFinal.Exceptions.*;
import edu.utn.TpFinal.Projections.UserLine;
import edu.utn.TpFinal.model.Cities;
import edu.utn.TpFinal.model.Clients;
import edu.utn.TpFinal.model.DTO.LineDTO;
import edu.utn.TpFinal.model.Lines;
import edu.utn.TpFinal.repository.CitiesRepository;
import edu.utn.TpFinal.repository.ClientsRepository;
import edu.utn.TpFinal.repository.EmployeesRepository;
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
    private EmployeesRepository employeeRepository;

    @Autowired
    public LinesService(LinesRespository linesRespository, ClientsService clientsService, CitiesRepository citiesRepository, ClientsRepository clientsRepository, EmployeesRepository employeeRepository) {
        this.linesRespository = linesRespository;
        this.clientsService = clientsService;
        this.citiesRepository = citiesRepository;
        this.clientsRepository = clientsRepository;
        this.employeeRepository = employeeRepository;
    }

    public Lines addLine(final Lines line, Integer clientId) throws ClientNotExists, InvalidType, InvalidPrefix, InvalidStatus, CityNotExists, InvalidPhoneNumber {
        line.setClient(clientsRepository.findById(clientId).orElseThrow(()-> new ClientNotExists()));
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

    public Page<UserLine> findByClient(Pageable pageable, Integer clientId) throws ClientNotExists {
        Clients c = clientsRepository.findById(clientId).orElseThrow(()-> new ClientNotExists());
        return linesRespository.findByClient(pageable, c);
    }

    public Lines updateLine(LineDTO newLine, Integer lineId) throws LineNotExists, ClientNotExists, InvalidPhoneNumber, CityNotExists, InvalidPrefix, InvalidStatus, InvalidType, DeletionNotAllowed {
        //verifyClientAndLine(clientId, lineId);
        Lines oldLine = this.linesRespository.findById(lineId).orElseThrow(()->new LineNotExists());
        //Clients client = clientsRepository.findById(clientId).get();
        verifyUpdateData(newLine, oldLine.getClient());
        verifyStatusAndType(newLine.getStatus(), newLine.getType());
        oldLine = this.setUpdate(newLine, oldLine);
        return linesRespository.save(oldLine);
    }

    public Lines deleteLine(Integer lineId) throws LineNotExists, ClientNotExists, LineAlreadyDeleted {
        //verifyClientAndLine(clientId, lineId);
        Lines line = findById(lineId);
        if(line.getStatus().equals(Lines.Status.CANCELLED))
            throw new LineAlreadyDeleted();
        line.setStatus(Lines.Status.CANCELLED);
        return linesRespository.save(line);
    }

    public Lines setUpdate(LineDTO newLine, Lines oldLine){
        oldLine.setStatus(newLine.getStatus());
        oldLine.setCity(newLine.getCity());
        oldLine.setPhoneNumber(newLine.getPhoneNumber());
        oldLine.setType(newLine.getType());
        return oldLine;
    }

    /////////////////////////////////Validations//////////////////////////////////////////////////

    public void verifyUpdateData(LineDTO newLine, Clients client) throws InvalidPhoneNumber, CityNotExists, InvalidPrefix, DeletionNotAllowed {
        if(linesRespository.existsByPhoneNumberAndClientNot(newLine.getPhoneNumber(),client))//revisar
            throw new InvalidPhoneNumber();
        verifyCityPrefixAndPhoneNumber(newLine.getCity().getId(), newLine.getPhoneNumber());//ver prefix
        if(newLine.getStatus().equals(Lines.Status.CANCELLED))
            throw new DeletionNotAllowed();
    }
    public void verifyClientAndLine(Integer clientId, Integer lineId) throws ClientNotExists, LineNotExists {
        Clients c = clientsRepository.findById(clientId).orElseThrow(()-> new ClientNotExists());
        if(!linesRespository.existsByIdAndClient(lineId, c))
            throw new LineNotExists();
    }

    public Boolean existsByLineNumber(String lineNumber){
        return linesRespository.existsByPhoneNumber(lineNumber);
    }

    public Lines findByLineNumber(String originNumber) throws LineNotExists {
        return linesRespository.findByPhoneNumber(originNumber).orElseThrow(()-> new LineNotExists());
    }

    public Boolean existsByStatusAndId(Lines.Status status, Integer id) {
        return linesRespository.existsByStatusAndId(status, id);
    }

    public void verifyLine(Lines line) throws ClientNotExists, CityNotExists, InvalidPrefix, InvalidType, InvalidStatus, InvalidPhoneNumber {
        clientsService.getClientsById(line.getClient().getId());
        if(existsByLineNumber(line.getPhoneNumber()) || line.getPhoneNumber().length() != 10)
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

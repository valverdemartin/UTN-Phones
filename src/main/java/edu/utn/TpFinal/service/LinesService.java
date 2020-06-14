package edu.utn.TpFinal.service;

import edu.utn.TpFinal.Exceptions.BillNotExist;
import edu.utn.TpFinal.Exceptions.ClientNotExist;
import edu.utn.TpFinal.Exceptions.LineNotExist;
import edu.utn.TpFinal.Projections.UserLine;
import edu.utn.TpFinal.model.Bills;
import edu.utn.TpFinal.model.Clients;
import edu.utn.TpFinal.model.Employees;
import edu.utn.TpFinal.model.Lines;
import edu.utn.TpFinal.repository.ClientsRepository;
import edu.utn.TpFinal.repository.LinesRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class LinesService {

    private LinesRespository linesRespository;
    private ClientsRepository clientsRepository;

    @Autowired
    public LinesService(LinesRespository linesRespository, ClientsRepository clientsRepository) {
        this.linesRespository = linesRespository;
        this.clientsRepository = clientsRepository;
    }

    public void addLine(final Lines line){
        linesRespository.save(line);
    }

    public List<Lines> getLines(){
        return linesRespository.findAll();
    }

    public Lines findById(Integer lineId){return linesRespository.findById(lineId).orElseThrow(() -> new LineNotExist(HttpStatus.BAD_REQUEST));}

    public UserLine getLineByClient(Integer clientId, Integer lineId){
        verifyClientAndLine(clientId, lineId);
        return linesRespository.findByIDAndByClient(lineId,clientId);
    }

    public void verifyClientAndLine(Integer clientId, Integer lineId){//ToDo Revisar
        if(!clientsRepository.existsById(clientId))
            throw  new ClientNotExist(HttpStatus.BAD_REQUEST);
        if(!linesRespository.existsById(lineId) || !linesRespository.existsByIdAndClient(lineId, clientsRepository.findById(clientId).get()))
            throw new LineNotExist(HttpStatus.BAD_REQUEST);
    }
}

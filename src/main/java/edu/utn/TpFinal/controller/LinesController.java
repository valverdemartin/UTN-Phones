package edu.utn.TpFinal.controller;

import edu.utn.TpFinal.Exceptions.*;
import edu.utn.TpFinal.Projections.UserLine;
import edu.utn.TpFinal.model.Clients;
import edu.utn.TpFinal.model.DTO.LineDTO;
import edu.utn.TpFinal.model.Lines;
import edu.utn.TpFinal.service.ClientsService;
import edu.utn.TpFinal.service.LinesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.validation.Valid;


@Controller
public class LinesController {
    private LinesService linesService;
    private ClientsService clientsService;

    @Autowired
    public LinesController(LinesService linesService, ClientsService clientsService) {
        this.linesService = linesService;
        this.clientsService = clientsService;
    }


    public Page<Lines> getLines(Pageable pageable, Lines.Status status){
        return linesService.getLines(pageable, status);
    }


    public UserLine getLineByClient(Integer clientId, Integer lineId) throws LineNotExists, ClientNotExists {
        return linesService.getLineByClient(clientId, lineId);
    }


    public Lines addLine(Lines line, Integer clientId) throws ClientNotExists, InvalidPrefix, CityNotExists, InvalidType, InvalidStatus, InvalidPhoneNumber {
        return linesService.addLine(line, clientId);
    }


    public Lines updateLine(final LineDTO line, Integer lineId) throws LineNotExists, InvalidStatus, ClientNotExists, InvalidType, InvalidPrefix, InvalidPhoneNumber, CityNotExists, DeletionNotAllowed {
        return linesService.updateLine(line, lineId);
    }


    public Lines deleteLine(Integer lineId) throws LineNotExists, ClientNotExists, LineAlreadyDeleted {
        return linesService.deleteLine(lineId);
    }
}


//ToDo enum con tipo de línea.
//ToDo verificar "active" sin valor x defecto.
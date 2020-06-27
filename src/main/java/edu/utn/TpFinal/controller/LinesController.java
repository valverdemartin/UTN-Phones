package edu.utn.TpFinal.controller;

import edu.utn.TpFinal.Exceptions.*;
import edu.utn.TpFinal.Projections.UserLine;
import edu.utn.TpFinal.model.DTO.LineDTO;
import edu.utn.TpFinal.model.Lines;
import edu.utn.TpFinal.service.LinesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/lines")
public class LinesController {
    private LinesService linesService;

    @Autowired
    public LinesController(LinesService linesService) {
        this.linesService = linesService;
    }


    public Page<Lines> getLines(Pageable pageable, Lines.Status status){
        return linesService.getLines(pageable, status);
    }


    public UserLine getLineByClient(@PathVariable Integer clientId, @PathVariable Integer lineId) throws LineNotExists, ClientNotExists {
        return linesService.getLineByClient(clientId, lineId);
    }

    @PostMapping("/")
    public Lines addLine(Lines line) throws ClientNotExists, InvalidPrefix, CityNotExists, InvalidType, InvalidStatus, InvalidPhoneNumber {
        return linesService.addLine(line);
    }


    public Lines updateLine(@RequestBody @Valid final LineDTO line, @PathVariable Integer clientId, @PathVariable Integer lineId) throws LineNotExists, InvalidStatus, ClientNotExists, InvalidType, InvalidPrefix, InvalidPhoneNumber, CityNotExists, DeletionNotAllowed {
        return linesService.updateLine(line, clientId, lineId);
    }


    public Lines deleteLine(Integer clientId, Integer lineId) throws LineNotExists, ClientNotExists {
        return linesService.deleteLine(clientId, lineId);
    }
}


//ToDo enum con tipo de línea.
//ToDo verificar "active" sin valor x defecto.
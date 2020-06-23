package edu.utn.TpFinal.controller;

import edu.utn.TpFinal.Exceptions.*;
import edu.utn.TpFinal.Projections.UserLine;
import edu.utn.TpFinal.model.Employees;
import edu.utn.TpFinal.model.Lines;
import edu.utn.TpFinal.service.LinesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

    @GetMapping("/")
    public Page<Lines> getLines(@PageableDefault(page=0, size=5) Pageable pageable, @RequestParam("status") Lines.Status status){
        return linesService.getLines(pageable, status);
    }

    @PostMapping("/")
    public ResponseEntity<Lines> addLine(@RequestBody @Valid final Lines line) throws ClientNotExists, InvalidPrefix, CityNotExists, InvalidType, InvalidStatus, InvalidPhoneNumber {
        return ResponseEntity.ok(linesService.addLine(line));
    }

    @GetMapping("/{clientId}/{lineId}/")
    public ResponseEntity<UserLine> getLineByClient(@PathVariable Integer clientId, @PathVariable Integer lineId) throws LineNotExists, ClientNotExists {
        UserLine line = linesService.getLineByClient(clientId, lineId);
        return line == null ? ResponseEntity.status(404).build() :ResponseEntity.ok(line);
    }

    @PutMapping("/{clientId}/{lineId}/")
    public ResponseEntity<Lines> updateLine(@RequestBody @Valid final Lines line, @PathVariable Integer clientId, @PathVariable Integer lineId) throws LineNotExists, InvalidStatus, ClientNotExists, InvalidType, InvalidPrefix, InvalidPhoneNumber, CityNotExists, DeletionNotAllowed {
        return ResponseEntity.ok(linesService.updateLine(line, clientId, lineId));
    }

    @DeleteMapping("/{clientId}/{lineId}/")
    public ResponseEntity<Lines> deleteLine(@PathVariable Integer clientId, @PathVariable Integer lineId) throws LineNotExists, ClientNotExists {
        return ResponseEntity.ok(linesService.deleteLine(clientId, lineId));
    }
}


//ToDo enum con tipo de línea.
//ToDo verificar "active" sin valor x defecto.
package edu.utn.TpFinal.controller.web;

import edu.utn.TpFinal.Exceptions.*;
import edu.utn.TpFinal.Projections.UserLine;
import edu.utn.TpFinal.controller.ClientsController;
import edu.utn.TpFinal.controller.LinesController;
import edu.utn.TpFinal.model.Clients;
import edu.utn.TpFinal.model.DTO.ClientUpdateDTO;
import edu.utn.TpFinal.model.DTO.LineDTO;
import edu.utn.TpFinal.model.Lines;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/backoffice")
public class BackOfficeController {

    private final ClientsController clientsController;
    private final LinesController linesController;

    @Autowired
    public BackOfficeController(ClientsController clientsController, LinesController linesController) {
        this.clientsController = clientsController;
        this.linesController = linesController;
    }

                                                /*CLIENTS*/
    @GetMapping("/clients/")
    public ResponseEntity<Page<Clients>> getClients(@PageableDefault(page=0, size=5) Pageable pageable){
            Page<Clients> clients = clientsController.getClients(pageable);
            return clients.isEmpty() ? ResponseEntity.status(204).build() : ResponseEntity.ok(clients);
    }

    @GetMapping("/clients/{clientId}/")
    public ResponseEntity<Clients> getClientById(@PathVariable Integer clientId) throws ClientNotExists {
        Clients client = clientsController.getClientById(clientId);
        return ResponseEntity.ok(client);
    }

    @PostMapping("/clients/")
    public ResponseEntity<Clients> addClient(@RequestBody @Valid final Clients client) throws UserDniAlreadyExist, UserNameAlreadyExist {
        return ResponseEntity.ok(clientsController.addClient(client));
    }

    @PutMapping("/clients/{clientId}/")
    public ResponseEntity<Clients> updateClient(@RequestBody @Valid final ClientUpdateDTO client, @PathVariable @Valid final Integer clientId, @RequestParam(value="active", required = false) boolean active) throws UserDniAlreadyExist, UserNameAlreadyExist, UserAlreadyDeleted, UserAlreadyActive, DeletionNotAllowed, ClientNotExists {
        return ResponseEntity.ok(clientsController.updateClient(client, clientId, active));
    }

    @DeleteMapping("clients/{clientId}/")
    public ResponseEntity<Clients> deleteClient( @PathVariable @Valid final Integer clientId) throws UserNotExists,UserAlreadyActive, UserAlreadyDeleted{
        return ResponseEntity.ok(clientsController.deleteClient(clientId));
    }

                                                    /*LINES*/
    /*@GetMapping("/lines")
    public ResponseEntity<Page<Lines>> getLines(@PageableDefault(page=0, size=5) Pageable pageable, @RequestParam("status") Lines.Status status){
        Page<Lines> lines = linesController.getLines(pageable, status);
        return lines.isEmpty()? ResponseEntity.status(204).build() : ResponseEntity.ok(lines);
    }*/

    @GetMapping("clients/{clientId}/{lineId}/")
    public ResponseEntity<UserLine> getLineByClient(@PathVariable Integer clientId, @PathVariable Integer lineId) throws LineNotExists, ClientNotExists {
        UserLine line = linesController.getLineByClient(clientId, lineId);
        return line == null ? ResponseEntity.status(404).build() :ResponseEntity.ok(line);
    }

    @PostMapping("/clients/{idClient}/lines/")
    public ResponseEntity<Lines> addLine(@RequestBody @Valid final Lines line) throws ClientNotExists, InvalidPrefix, CityNotExists, InvalidType, InvalidStatus, InvalidPhoneNumber {
        return ResponseEntity.ok(linesController.addLine(line));
    }

    @PutMapping("/{clientId}/{lineId}/")
    public ResponseEntity<Lines> updateLine(@RequestBody @Valid final LineDTO line, @PathVariable Integer clientId, @PathVariable Integer lineId) throws LineNotExists, InvalidStatus, ClientNotExists, InvalidType, InvalidPrefix, InvalidPhoneNumber, CityNotExists, DeletionNotAllowed {
        return ResponseEntity.ok(linesController.updateLine(line, clientId, lineId));
    }

    @DeleteMapping("/{clientId}/{lineId}/")
    public ResponseEntity<Lines> deleteLine(@PathVariable Integer clientId, @PathVariable Integer lineId) throws LineNotExists, ClientNotExists {
        return ResponseEntity.ok(linesController.deleteLine(clientId, lineId));
    }


}

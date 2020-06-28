package edu.utn.TpFinal.controller.web;

import edu.utn.TpFinal.Exceptions.*;
import edu.utn.TpFinal.Projections.UserBills;
import edu.utn.TpFinal.Projections.UserCalls;
import edu.utn.TpFinal.Projections.UserLine;
import edu.utn.TpFinal.config.Configuration;
import edu.utn.TpFinal.controller.*;
import edu.utn.TpFinal.model.Clients;
import edu.utn.TpFinal.model.DTO.ClientUpdateDTO;
import edu.utn.TpFinal.model.DTO.LineDTO;
import edu.utn.TpFinal.model.Lines;
import edu.utn.TpFinal.model.Rates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.sql.Timestamp;

@RestController
@RequestMapping("/backoffice")
public class BackOfficeController {

    private final ClientsController clientsController;
    private final LinesController linesController;
    private final RatesController ratesController;
    private final CallsController callsController;
    private final BillsController billsController;

    @Autowired
    public BackOfficeController(ClientsController clientsController, LinesController linesController, RatesController ratesController, CallsController callsController, BillsController billsController) {
        this.clientsController = clientsController;
        this.linesController = linesController;
        this.ratesController = ratesController;
        this.callsController = callsController;
        this.billsController = billsController;
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
        URI uri = Configuration.UriGenerator.getLocation(clientsController.addClient(client).getId());
        return ResponseEntity.created(uri).build();
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
    public ResponseEntity<Lines> addLine(@RequestBody @Valid final Lines line, @PathVariable Integer idClient) throws ClientNotExists, InvalidPrefix, CityNotExists, InvalidType, InvalidStatus, InvalidPhoneNumber {
        URI uri = Configuration.UriGenerator.getLocation(linesController.addLine(line, idClient).getId());
        return ResponseEntity.created(uri).build();
        //return ResponseEntity.ok(linesController.addLine(line, clientId));
    }

    @PutMapping("/lines/{lineId}/")
    public ResponseEntity<Lines> updateLine(@RequestBody @Valid final LineDTO line,@PathVariable Integer lineId) throws LineNotExists, InvalidStatus, ClientNotExists, InvalidType, InvalidPrefix, InvalidPhoneNumber, CityNotExists, DeletionNotAllowed {
        return ResponseEntity.ok(linesController.updateLine(line, lineId));
    }

    @DeleteMapping("/lines/{lineId}/")
    public ResponseEntity<Lines> deleteLine(@PathVariable Integer lineId) throws LineNotExists, ClientNotExists, LineAlreadyDeleted {
        return ResponseEntity.ok(linesController.deleteLine(lineId));
    }

                                                    /*Rates*/

    @GetMapping("/rates/")
    public ResponseEntity<Page<Rates>> getRates(@PageableDefault(page=0, size=5) Pageable pageable){
        Page<Rates> rates = ratesController.getRates(pageable);
        return rates.isEmpty() ? ResponseEntity.status(204).build() : ResponseEntity.ok(rates);
    }

    @GetMapping("/rates/{rateId}/")
    public ResponseEntity<Rates> getRates(@PathVariable Integer rateId) throws RateNotExists {
        Rates rate = ratesController.getRatesById(rateId);
        return ResponseEntity.ok(rate);
    }

                                                    /*Calls*/
    @GetMapping("/clients/{clientId}/lines/{lineId}/")
    public ResponseEntity <Page<UserCalls>> getUsersCalls(@PageableDefault(page=0, size=5) Pageable pageable,
                                                          @PathVariable Integer clientId, @PathVariable Integer lineId,
                                                          @RequestParam(required = false) Timestamp from,
                                                          @RequestParam(required = false)  Timestamp to)
                                                            throws LineNotExists, ClientNotExists {
        Page<UserCalls> calls = callsController.getUserCalls(pageable, clientId, lineId, from, to);
        return calls.isEmpty() ? ResponseEntity.status(204).build() : ResponseEntity.ok(calls);
    }

                                                    /*Bills*/

    @GetMapping("/clients/{clientId}/lines/{lineId}/bills/")
    public ResponseEntity<Page<UserBills>> getUserBills(@PageableDefault(page=0, size=5) Pageable pageable,
                                                         @PathVariable Integer clientId,
                                                         @PathVariable Integer lineId,
                                                         @RequestParam(required = false) Timestamp from,
                                                         @RequestParam(required = false) Timestamp to) throws LineNotExists, BillNotExists, ClientNotExists {
        Page<UserBills> calls = billsController.getUserBills(pageable,clientId, lineId, from, to);
        return calls.isEmpty() ? ResponseEntity.status(204).build() : ResponseEntity.ok(calls);
    }




}

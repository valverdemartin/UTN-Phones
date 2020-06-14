package edu.utn.TpFinal.controller;

import edu.utn.TpFinal.Exceptions.UserNotExistException;
import edu.utn.TpFinal.Projections.UserCalls;
import edu.utn.TpFinal.Projections.DurationByMonth;
import edu.utn.TpFinal.Projections.FavouriteCall;
import edu.utn.TpFinal.model.Clients;
import edu.utn.TpFinal.service.ClientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientsController {

    private final ClientsService clientsService;

    @Autowired
    public ClientsController(final ClientsService clientsService) {
        this.clientsService = clientsService;
    }

    @GetMapping("/{clientId}/")
    public Clients getClientsById(@PathVariable Integer clientId){
        return clientsService.getClientsById(clientId);
    }

    @GetMapping("/")
    public List<Clients> getClients(){
        return clientsService.getClients();
    }

    @GetMapping("/ln/{lastName}/")
    public Clients getClients(@PathVariable String lastName){
        return clientsService.getClientsByLastName(lastName);
    }

    @PostMapping("/")
    public void addClient(@RequestBody @Valid final Clients client){
        clientsService.addClient(client);
    }

    @PutMapping("/{clientId}/")
    public void activeClient(@PathVariable Integer clientId) throws UserNotExistException {
        this.clientsService.putActive(clientId);
    }

    //Endpoint que retorne el nombre, apellido y llamada más realizada del cliente

    @GetMapping("/projection/{lineId}/")
    public FavouriteCall favouriteCall(@PathVariable Integer lineId){
        return clientsService.favouriteCall(lineId);
    }

    @GetMapping("/projection/{userId}/{selectedMonth}/")
    public  ResponseEntity<DurationByMonth> getDurationByMonth(@PathVariable Integer userId, @PathVariable Integer selectedMonth){
        DurationByMonth dr = clientsService.getDurationByMont(userId, selectedMonth);
        return dr.getSumDuration() == null ? ResponseEntity.status(204).build() : ResponseEntity.ok(dr);
    }

    @GetMapping("/calls/{clientId}/")
    public ResponseEntity<List<UserCalls>> getCallsGreaterThan(@PathVariable Integer clientId, @PathVariable Double price){
        List<UserCalls> calls = clientsService.getCallsGreaterThan(clientId, price);
        return calls.isEmpty() ? ResponseEntity.status(204).build() : ResponseEntity.ok(calls);
    }



    //ToDo 			□ Login user y Log out (borra session 1hs:6min 4/6)
    //ToDo 			□ Consulta de facturas por rango de fecha de user logueado



}



//ToDo DTO persona con líneas.
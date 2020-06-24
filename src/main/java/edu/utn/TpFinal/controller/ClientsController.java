package edu.utn.TpFinal.controller;

import edu.utn.TpFinal.Exceptions.*;
import edu.utn.TpFinal.model.Clients;
import edu.utn.TpFinal.service.ClientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;

@RestController
@RequestMapping("/client")
public class ClientsController {

    private final ClientsService clientsService;

    @Autowired
    public ClientsController(final ClientsService clientsService) {
        this.clientsService = clientsService;
    }


    public Clients login(String username, String password) throws UserNotExists, ValidationException {
        if ((username != null) && (password != null)) {
            return clientsService.login(username, password);
        } else {
            throw new ValidationException("username and password must have a value");
        }
    }

    @GetMapping("/{clientId}/")
    public ResponseEntity<Clients> getClientsById(@PathVariable Integer clientId) throws ClientNotExists {
        Clients client = clientsService.getClientsById(clientId);
        return ResponseEntity.ok(client);
    }

    @GetMapping("/")
    public ResponseEntity<Page<Clients>> getClients(@PageableDefault(page=0, size=5) Pageable pageable){
        Page<Clients> clients = clientsService.getClients(pageable);
        return clients.isEmpty() ? ResponseEntity.status(204).build() : ResponseEntity.ok(clients);
    }

    @PostMapping("/")
    public ResponseEntity<Clients> addClient(@RequestBody @Valid final Clients client) throws UserDniAlreadyExist, UserNameAlreadyExist {
        return ResponseEntity.ok(clientsService.addClient(client));
    }

    @PutMapping("/{active}/")
    public ResponseEntity<Clients> updateClient(@RequestBody @Valid final Clients client, @PathVariable boolean active) throws UserNotExists, UserDniAlreadyExist, UserNameAlreadyExist, UserAlreadyDeleted, UserAlreadyActive, DeletionNotAllowed {
        return ResponseEntity.ok(clientsService.updateClient(client, active));
    }

    @DeleteMapping("/{clientId}/")
    public ResponseEntity<Clients> deleteClient( @PathVariable @Valid final Integer clientId) throws UserNotExists,UserAlreadyActive, UserAlreadyDeleted{
        return ResponseEntity.ok(clientsService.deleteClients(clientId));
    }

    /////////////////////////////Practica Examen////////////////////////////////
    //Endpoint que retorne el nombre, apellido y llamada más realizada del cliente

//    @GetMapping("/projection/{lineId}/")
//    public FavouriteCall favouriteCall(@PathVariable Integer lineId){
//        return clientsService.favouriteCall(lineId);
//    }
//
//    @GetMapping("/projection/{userId}/{selectedMonth}/")
//    public  ResponseEntity<DurationByMonth> getDurationByMonth(@PathVariable Integer userId, @PathVariable Integer selectedMonth) throws UserNotExists {
//        DurationByMonth dr = clientsService.getDurationByMont(userId, selectedMonth);
//        return dr.getSumDuration() == null ? ResponseEntity.status(204).build() : ResponseEntity.ok(dr);
//    }
//
//    @GetMapping("/calls/{clientId}/")
//    public ResponseEntity<List<UserCalls>> getCallsGreaterThan(@PathVariable Integer clientId, @PathVariable Double price) throws UserNotExists {
//        List<UserCalls> calls = clientsService.getCallsGreaterThan(clientId, price);
//        return calls.isEmpty() ? ResponseEntity.status(204).build() : ResponseEntity.ok(calls);
//    }
    ////////////////////////////////////FIN/////////////////////////////////////
    //ToDo 			□ Login user y Log out (borra session 1hs:6min 4/6)
    //ToDo 			□ Consulta de facturas por rango de fecha de user logueado
    //ToDo DTO persona con líneas.


}




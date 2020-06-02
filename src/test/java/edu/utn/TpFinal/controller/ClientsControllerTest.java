package edu.utn.TpFinal.controller;

import edu.utn.TpFinal.Projections.FavouriteCall;
import edu.utn.TpFinal.model.Cities;
import edu.utn.TpFinal.model.Clients;
import edu.utn.TpFinal.model.Lines;
import edu.utn.TpFinal.model.Provinces;
import edu.utn.TpFinal.service.ClientsService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;



public class ClientsControllerTest {


    ClientsController clientsController;
    ClientsService clientsService;

    @Before
    public void onSetUp(){
        clientsService = mock(ClientsService.class);
        clientsController = new ClientsController(clientsService);
    }

    @Test
    public void addClientOk(){
        Clients clientToAdd = new Clients();
        doNothing().when(clientsService).addClient(clientToAdd);
        clientsController.addClient(clientToAdd);
        verify(clientsService, times(1)).addClient(clientToAdd);
    }

    @Test public void favouriteCallok(){
        Integer idLine = 1;
        FavouriteCall fv = null;
        when(clientsService.favouriteCall(idLine)).thenReturn(fv);
        clientsController.favouriteCall(idLine);
        verify(clientsService, times(1)).favouriteCall(idLine);
    }

    /*@Test
    public void testRemoveUserOk() throws UserNotexistException {
        User userToRemove = new User(1, "Nombre", "username", "", "Surname", null);
        doNothing().when(service).removeUser(userToRemove);
        userController.removeUser(userToRemove);
        verify(service, times(1)).removeUser(userToRemove);
    }*/

}

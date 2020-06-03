package edu.utn.TpFinal.controller;

import edu.utn.TpFinal.Projections.FavouriteCall;
import edu.utn.TpFinal.Projections.UserCityLastCallDuration;
import edu.utn.TpFinal.model.Clients;
import edu.utn.TpFinal.service.ClientsService;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;

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
    public void addClientOk() throws ParseException {
        Clients clientToAdd = Clients.builder().name("Martin").lastName("Valverde").dni(39137741).active(true).line(null)
                .password("123").userName("tincho").build();
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

    @Test public void lastCallDurationOk(){
        Integer idLine = 1;
        UserCityLastCallDuration lc = null;
        when(clientsService.getLastCallDuration(idLine)).thenReturn(lc);
        clientsController.getLastCallDuration(idLine);
        verify(clientsService, times(1)).getLastCallDuration(idLine);
    }

    /*@Test
    public void testRemoveUserOk() throws UserNotexistException {
        User userToRemove = new User(1, "Nombre", "username", "", "Surname", null);
        doNothing().when(service).removeUser(userToRemove);
        userController.removeUser(userToRemove);
        verify(service, times(1)).removeUser(userToRemove);
    }*/

}

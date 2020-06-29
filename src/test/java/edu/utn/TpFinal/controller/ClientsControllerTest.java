package edu.utn.TpFinal.controller;

import edu.utn.TpFinal.Exceptions.UserDniAlreadyExist;
import edu.utn.TpFinal.Exceptions.UserNameAlreadyExist;
import edu.utn.TpFinal.model.Clients;
import edu.utn.TpFinal.service.ClientsService;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;



public class ClientsControllerTest {


    ClientsController clientsController;
    ClientsService clientsService;

    @Before
    public void onSetUp(){
        clientsService = mock(ClientsService.class);
        clientsController = new ClientsController(clientsService);
    }

    /*@Test
    public void addClientOk() throws  UserDniAlreadyExist, UserNameAlreadyExist {
        Clients clientToAdd = Clients.builder().name("Martin").lastName("Valverde").dni(39137741).active(true).line(null)
                .password("123").userName("tincho").build();
        doReturn(clientToAdd).when(clientsService).addClient(clientToAdd);
        clientsController.addClient(clientToAdd);
        verify(clientsService, times(1)).addClient(clientToAdd);
    }*/

    /*@Test public void favouriteCallok(){
        Integer idLine = 1;
        FavouriteCall fv = null;
        when(clientsService.favouriteCall(idLine)).thenReturn(fv);
        clientsController.favouriteCall(idLine);
        verify(clientsService, times(1)).favouriteCall(idLine);
    }*/

    /*@Test
    public void testRemoveUserOk() throws UserNotexistException {
        User userToRemove = new User(1, "Nombre", "username", "", "Surname", null);
        doNothing().when(service).removeUser(userToRemove);
        userController.removeUser(userToRemove);
        verify(service, times(1)).removeUser(userToRemove);
    }*/

}

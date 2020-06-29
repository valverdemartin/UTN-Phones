package edu.utn.TpFinal.controller;

import edu.utn.TpFinal.Exceptions.ClientNotExists;
import edu.utn.TpFinal.Exceptions.UserDniAlreadyExist;
import edu.utn.TpFinal.Exceptions.UserNameAlreadyExist;
import edu.utn.TpFinal.Exceptions.UserNotExists;
import edu.utn.TpFinal.dto.LoginRequestDto;
import edu.utn.TpFinal.model.Clients;
import edu.utn.TpFinal.service.ClientsService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;



public class ClientsControllerTest {


    ClientsController clientsController;
    ClientsService clientsService;

    @Before
    public void onSetUp() {
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

    //ToDo validar hash del password.

    @Test
    public void clientLogin() throws UserNotExists {
        Clients loggedClient = Clients.builder().name("test").lastName("Valverde").dni(39137741).active(true)
                .line(null).password("123").userName("test").build();
        LoginRequestDto testLogin = new LoginRequestDto();
        testLogin.setPassword("123");
        testLogin.setUsername("test");
        doReturn(loggedClient).when(clientsService).login(testLogin.getUsername(), testLogin.getPassword());
        clientsController.login(loggedClient.getUserName(), loggedClient.getPassword());
        verify(clientsService, times(1)).login(testLogin.getUsername(), testLogin.getPassword());
    }

    @Test
    public void clientLoginFail() throws UserNotExists {
        LoginRequestDto testLogin = new LoginRequestDto();
        testLogin.setUsername("test");
        testLogin.setPassword("123");
        when(clientsController.login(testLogin.getUsername(), testLogin.getPassword())).thenThrow(new UserNotExists());
    }

    @Test
    public void getCients() throws ClientNotExists {
        Clients clientById = new Clients();
        when(clientsService.getClientsById(1)).thenReturn(clientById);
        Clients mockedClient = clientsController.getClientById(1);
        verify(clientsService, times(1)).getClientsById(1);

    }

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
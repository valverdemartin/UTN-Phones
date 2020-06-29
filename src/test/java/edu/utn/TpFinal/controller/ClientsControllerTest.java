package edu.utn.TpFinal.controller;

import edu.utn.TpFinal.Exceptions.UserDniAlreadyExist;
import edu.utn.TpFinal.Exceptions.UserNameAlreadyExist;
import edu.utn.TpFinal.Exceptions.UserNotExists;
import edu.utn.TpFinal.model.DTO.LoginRequestDto;
import edu.utn.TpFinal.model.Clients;
import edu.utn.TpFinal.model.DTO.UserDTO;
import edu.utn.TpFinal.service.ClientsService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.validation.ValidationException;

import java.util.Collections;

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


    @Test
    public void addClientOk() throws  UserDniAlreadyExist, UserNameAlreadyExist {
        UserDTO clientToAdd = new UserDTO();
        Clients clientAdded = new Clients();
        clientToAdd.setName("Dummy");
        clientToAdd.setLastName("Client");
        clientToAdd.setUserName("DummyClient");
        clientToAdd.setPassword("DummyPassw0rd");
        clientToAdd.setActive(true);
        clientToAdd.setDni(9999999);
        doReturn(clientAdded).when(clientsService).addClient(clientToAdd);
        clientsController.addClient(clientToAdd);
        verify(clientsService, times(1)).addClient(clientToAdd);
    }


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

    @Test(expected = UserNotExists.class)
    public void clientLoginFail() throws UserNotExists {
        LoginRequestDto testLogin = new LoginRequestDto();
        testLogin.setUsername("test");
        testLogin.setPassword("123");
        when(clientsController.login(testLogin.getUsername(), testLogin.getPassword())).thenThrow(new UserNotExists());
        clientsController.login(testLogin.getUsername(), testLogin.getPassword());
    }

    @Test(expected = ValidationException.class)
    public void clientEmptyUsernamePassword() throws ValidationException, UserNotExists {
        LoginRequestDto testLogin = new LoginRequestDto();
        testLogin.setUsername(null);
        testLogin.setPassword(null);
        when(clientsController.login(testLogin.getUsername(), testLogin.getPassword())).thenThrow(new ValidationException());
        clientsController.login(testLogin.getUsername(), testLogin.getPassword());
    }

    @Test
    public void getClients() {
        Pageable pageable = PageRequest.of(0,1);
        Clients mockedClient = Clients.builder().name("test").lastName("Valverde").dni(39137741).active(true)
                .line(null).password("123").userName("test").build();
        Page<Clients> clientsPage = new PageImpl<Clients>(Collections.singletonList(mockedClient));
        when(clientsController.getClients(pageable)).thenReturn(clientsPage);
        clientsService.getClients(pageable);
        verify(clientsService, times(1)).getClients(pageable);
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
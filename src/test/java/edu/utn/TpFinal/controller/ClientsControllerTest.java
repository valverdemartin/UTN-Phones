package edu.utn.TpFinal.controller;


import edu.utn.TpFinal.Exceptions.*;
import edu.utn.TpFinal.model.Clients;
import edu.utn.TpFinal.model.DTO.LoginRequestDto;
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

import static org.junit.Assert.assertEquals;
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
    public void clientLogin() {
        Clients loggedClient = Clients.builder().name("test").lastName("Valverde").dni(39137741).active(true)
                .line(null).password("123").userName("test").build();
        LoginRequestDto testLogin = new LoginRequestDto();
        testLogin.setPassword("123");
        testLogin.setUsername("test");
        doReturn(loggedClient).when(clientsService).login(testLogin.getUsername(), testLogin.getPassword());
        clientsController.login(loggedClient.getUserName(), loggedClient.getPassword());
        verify(clientsService, times(1)).login(testLogin.getUsername(), testLogin.getPassword());
    }

    @Test(expected = ValidationException.class)
    public void clientEmptyUsernamePassword() throws ValidationException {
        LoginRequestDto testLogin = new LoginRequestDto();
        testLogin.setUsername(null);
        testLogin.setPassword(null);
        when(clientsController.login(testLogin.getUsername(), testLogin.getPassword())).thenThrow(new ValidationException());
        clientsController.login(testLogin.getUsername(), testLogin.getPassword());
    }

    @Test(expected = ValidationException.class)
    public void clientEmptyUserNameOnly() throws ValidationException{
        LoginRequestDto testLogin = new LoginRequestDto();
        testLogin.setUsername("test");
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
        clientsController.getClients(pageable);
        verify(clientsService, times(1)).getClients(pageable);
    }

    @Test
    public void updateClient()throws UserDniAlreadyExist, UserNameAlreadyExist, UserAlreadyDeleted, UserAlreadyActive, DeletionNotAllowed, ClientNotExists {
        Clients mockedClient = Clients.builder().name("test").lastName("Valverde").dni(39137741).active(true)
                .line(null).password("123").userName("test").build();
        UserDTO userDTO = null;
        when(clientsController.updateClient(userDTO, 1, true)).thenReturn(mockedClient);
        clientsController.updateClient(userDTO, 1, true);
        verify(clientsService, times(1)).updateClient(userDTO, 1, true);
    }

    @Test
    public void deleteClient() throws UserNotExists, UserAlreadyActive, UserAlreadyDeleted{
        Clients mockedClient = Clients.builder().name("test").lastName("Valverde").dni(39137741).active(false)
                .line(null).password("123").userName("test").build();
        mockedClient.setId(1);
        when(clientsController.deleteClient(1)).thenReturn(mockedClient);
        clientsController.deleteClient(1);
        verify(clientsService, times(1)).deleteClients(1);
        assertEquals(mockedClient.getActive(), false);
    }

    @Test
    public void getClientById() throws ClientNotExists{
        Clients mockedClient = Clients.builder().name("test").lastName("Valverde").dni(39137741).active(false)
                .line(null).password("123").userName("test").build();
        when(clientsController.getClientById(1)).thenReturn(mockedClient);
        clientsController.getClientById(1);
        verify(clientsService,times(1)).getClientsById(1);
    }

    @Test(expected = ClientNotExists.class)
    public void getClientNotExists() throws ClientNotExists{
        when(clientsController.getClientById(1)).thenThrow(new ClientNotExists());
        clientsController.getClientById(1);
        verify(clientsService,times(1)).getClientsById(1);
    }

}
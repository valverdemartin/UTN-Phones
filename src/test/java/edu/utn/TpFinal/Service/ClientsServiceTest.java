package edu.utn.TpFinal.Service;

import edu.utn.TpFinal.Exceptions.*;
import edu.utn.TpFinal.model.Clients;
import edu.utn.TpFinal.model.DTO.UserDTO;
import edu.utn.TpFinal.repository.ClientsRepository;
import edu.utn.TpFinal.repository.LinesRespository;
import edu.utn.TpFinal.service.ClientsService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.*;

public class ClientsServiceTest {

    ClientsRepository clientsRepository;
    ClientsService clientsService;
    LinesRespository linesRespository;

    @Before
    public void onSetUp(){
        clientsRepository= mock(ClientsRepository.class);
        clientsService = new ClientsService(clientsRepository, linesRespository);
    }

    @Test
    public void getClientById() throws ClientNotExists {
        Clients mockedClient = Clients.builder().name("test").lastName("Valverde").dni(39137741).active(false)
                .line(null).password("123").userName("test").id(1).build();
        when(clientsRepository.findByIdAndActiveTrue(1)).thenReturn(java.util.Optional.of(mockedClient));
        Clients clientById = clientsService.getClientsById(1);
        assertEquals(mockedClient.getId(), clientById.getId());
        verify(clientsRepository,times(1)).findByIdAndActiveTrue(1);
    }

    @Test(expected = ClientNotExists.class)
    public void getClientNotExistsById() throws ClientNotExists {
        when(clientsRepository.findByIdAndActiveTrue(1)).thenReturn(java.util.Optional.ofNullable(null));
        clientsService.getClientsById(1);
        verify(clientsRepository,times(1)).findByIdAndActiveTrue(1);
    }

    @Test
    public void getCLients(){
        Pageable pageable = PageRequest.of(0,1);
        Clients mockedClient = Clients.builder().name("test").lastName("Valverde").dni(39137741).active(true)
                .line(null).password("123").userName("test").build();
        Page<Clients> clientsPage = new PageImpl<Clients>(Collections.singletonList(mockedClient));
        when(clientsService.getClients(pageable)).thenReturn(clientsPage);
        Page<Clients> newPage = clientsService.getClients(pageable);
        verify(clientsRepository, times(1)).findByActiveTrue(pageable);
        assertEquals(newPage.getTotalPages(), clientsPage.getTotalPages());
    }

    @Test
    public void addClient() throws UserDniAlreadyExist, UserNameAlreadyExist {
        UserDTO clientToAdd = new UserDTO();
        Clients clientAdded = new Clients();
        clientToAdd.setName("Dummy");
        clientToAdd.setLastName("Client");
        clientToAdd.setUserName("DummyClient");
        clientToAdd.setPassword("DummyPassw0rd");
        clientToAdd.setActive(true);
        clientToAdd.setDni(9999999);
        when(clientsService.addClient(clientToAdd)).thenReturn(clientAdded);
        clientsService.addClient(clientToAdd);
        verify(clientsRepository, times(1)).save(clientAdded);
    }

    @Test(expected = UserDniAlreadyExist.class)
    public void addClientDniExists() throws UserDniAlreadyExist, UserNameAlreadyExist {
        UserDTO clientToAdd = new UserDTO();
        Clients clientAdded = new Clients();
        clientToAdd.setName("Dummy");
        clientToAdd.setLastName("Client");
        clientToAdd.setUserName("DummyClient");
        clientToAdd.setPassword("DummyPassw0rd");
        clientToAdd.setActive(true);
        clientToAdd.setDni(9999999);
        when(clientsRepository.existsByDni(9999999)).thenReturn(true);
        clientsService.addClient(clientToAdd);
        verify(clientsRepository, times(1)).save(clientAdded);
    }

    @Test(expected = UserNameAlreadyExist.class)
    public void addClientUserNameExists() throws UserDniAlreadyExist, UserNameAlreadyExist {
        UserDTO clientToAdd = new UserDTO();
        Clients clientAdded = new Clients();
        clientToAdd.setName("Dummy");
        clientToAdd.setLastName("Client");
        clientToAdd.setUserName("DummyClient");
        clientToAdd.setPassword("DummyPassw0rd");
        clientToAdd.setActive(true);
        clientToAdd.setDni(9999999);
        when(clientsRepository.existsByUserName("DummyClient")).thenReturn(true);
        clientsService.addClient(clientToAdd);
        verify(clientsRepository, times(1)).save(clientAdded);
    }

    @Test
    public void updateClient() throws UserDniAlreadyExist, UserNameAlreadyExist, UserAlreadyDeleted, UserAlreadyActive, DeletionNotAllowed, ClientNotExists{
        UserDTO clientToUpdate = new UserDTO();
        Clients clientUpdated;
        Clients notUpdatedClient;
        clientToUpdate.setName("Dummy");
        clientToUpdate.setLastName("Client");
        clientToUpdate.setUserName("DummyClient");
        clientToUpdate.setPassword("DummyPassw0rd");
        clientToUpdate.setActive(true);
        clientToUpdate.setDni(9999999);
        clientUpdated = Clients.builder().name("Dummy").lastName("Client").userName("DummyClient").password("DummyPassw0rd").active(false).dni(9999998).build();
        notUpdatedClient = Clients.builder().name("Dummy").lastName("Client").userName("DummyClient").password("DummyPassw0rd").active(false).dni(9999999).build();
        when(clientsRepository.findById(1)).thenReturn(Optional.ofNullable(notUpdatedClient));
        when(clientsService.updateClient(clientToUpdate,1,false)).thenReturn(clientUpdated);
        clientsService.updateClient(clientToUpdate, 1, false);
        assertNotEquals(clientUpdated.getDni(), notUpdatedClient.getDni());
        verify(clientsRepository, times(1)).save(clientUpdated);
    }

    @Test(expected = DeletionNotAllowed.class)
    public void updateClientDeletionNotAllowed() throws UserDniAlreadyExist, UserNameAlreadyExist, UserAlreadyDeleted, UserAlreadyActive, DeletionNotAllowed, ClientNotExists{
        UserDTO clientToUpdate = new UserDTO();
        Clients clientUpdated;
        Clients notUpdatedClient;
        clientToUpdate.setName("Dummy");
        clientToUpdate.setLastName("Client");
        clientToUpdate.setUserName("DummyClient");
        clientToUpdate.setPassword("DummyPassw0rd");
        clientToUpdate.setActive(false);
        clientToUpdate.setDni(9999999);
        clientUpdated = Clients.builder().name("Dummy").lastName("Client").userName("DummyClient").password("DummyPassw0rd").active(false).dni(9999998).build();
        notUpdatedClient = Clients.builder().name("Dummy").lastName("Client").userName("DummyClient").password("DummyPassw0rd").active(false).dni(9999999).build();
        when(clientsRepository.findById(1)).thenReturn(Optional.ofNullable(notUpdatedClient));
        clientsService.updateClient(clientToUpdate, 1, false);
        verify(clientsRepository, times(1)).save(clientUpdated);
    }

    @Test(expected = UserAlreadyActive.class)
    public void updateClientUserAlreadyActive() throws UserDniAlreadyExist, UserNameAlreadyExist, UserAlreadyDeleted, UserAlreadyActive, DeletionNotAllowed, ClientNotExists{
        UserDTO clientToUpdate = new UserDTO();
        Clients clientUpdated;
        Clients notUpdatedClient;
        clientToUpdate.setName("Dummy");
        clientToUpdate.setLastName("Client");
        clientToUpdate.setUserName("DummyClient");
        clientToUpdate.setPassword("DummyPassw0rd");
        clientToUpdate.setActive(true);
        clientToUpdate.setDni(9999999);
        clientUpdated = Clients.builder().name("Dummy").lastName("Client").userName("DummyClient").password("DummyPassw0rd").active(true).dni(9999998).build();
        notUpdatedClient = Clients.builder().name("Dummy").lastName("Client").userName("DummyClient").password("DummyPassw0rd").active(true).dni(9999999).build();
        when(clientsRepository.findById(1)).thenReturn(Optional.ofNullable(notUpdatedClient));
        when(clientsService.updateClient(clientToUpdate,1,true)).thenReturn(clientUpdated);
        clientsService.updateClient(clientToUpdate, 1, true);
        verify(clientsRepository, times(1)).save(clientUpdated);
    }

    @Test(expected = ClientNotExists.class)
    public void updateClientClientNotExists() throws UserDniAlreadyExist, UserNameAlreadyExist, UserAlreadyDeleted, UserAlreadyActive, DeletionNotAllowed, ClientNotExists{
        UserDTO clientToUpdate = new UserDTO();
        Clients clientUpdated;
        clientToUpdate.setName("Dummy");
        clientToUpdate.setLastName("Client");
        clientToUpdate.setUserName("DummyClient");
        clientToUpdate.setPassword("DummyPassw0rd");
        clientToUpdate.setActive(true);
        clientToUpdate.setDni(9999999);
        clientUpdated = Clients.builder().name("Dummy").lastName("Client").userName("DummyClient").password("DummyPassw0rd").active(true).dni(9999998).build();
        when(clientsRepository.findById(1)).thenReturn(Optional.ofNullable(null));
        clientsService.updateClient(clientToUpdate, 1, true);
        verify(clientsRepository, times(1)).save(clientUpdated);
    }

    @Test(expected = UserDniAlreadyExist.class)
    public void updateClientUserDniAlreadyExist() throws UserDniAlreadyExist, UserNameAlreadyExist, UserAlreadyDeleted, UserAlreadyActive, DeletionNotAllowed, ClientNotExists{
        UserDTO clientToUpdate = new UserDTO();
        Clients clientUpdated;
        Clients notUpdatedClient;
        clientToUpdate.setName("Dummy");
        clientToUpdate.setLastName("Client");
        clientToUpdate.setUserName("DummyClient");
        clientToUpdate.setPassword("DummyPassw0rd");
        clientToUpdate.setActive(true);
        clientToUpdate.setDni(9999999);
        clientUpdated = Clients.builder().name("Dummy").lastName("Client").userName("DummyClient").password("DummyPassw0rd").active(false).dni(9999998).build();
        notUpdatedClient = Clients.builder().name("Dummy").lastName("Client").userName("DummyClient").password("DummyPassw0rd").active(false).dni(9999999).build();
        when(clientsRepository.findById(1)).thenReturn(Optional.ofNullable(notUpdatedClient));
        when(clientsRepository.existsByIdNotAndDni(notUpdatedClient.getId(),clientToUpdate.getDni())).thenReturn(true);
        clientsService.updateClient(clientToUpdate, 1, false);
        verify(clientsRepository, times(1)).save(clientUpdated);
    }

    @Test(expected = UserNameAlreadyExist.class)
    public void updateClientUserNameAlreadyExist() throws UserDniAlreadyExist, UserNameAlreadyExist, UserAlreadyDeleted, UserAlreadyActive, DeletionNotAllowed, ClientNotExists{
        UserDTO clientToUpdate = new UserDTO();
        Clients clientUpdated;
        Clients notUpdatedClient;
        clientToUpdate.setName("Dummy");
        clientToUpdate.setLastName("Client");
        clientToUpdate.setUserName("DummyClient");
        clientToUpdate.setPassword("DummyPassw0rd");
        clientToUpdate.setActive(true);
        clientToUpdate.setDni(9999999);
        clientUpdated = Clients.builder().name("Dummy").lastName("Client").userName("DummyClient").password("DummyPassw0rd").active(false).dni(9999998).build();
        notUpdatedClient = Clients.builder().name("Dummy").lastName("Client").userName("DummyClient").password("DummyPassw0rd").active(false).dni(9999999).build();
        when(clientsRepository.findById(1)).thenReturn(Optional.ofNullable(notUpdatedClient));
        when(clientsRepository.existsByIdNotAndUserName(notUpdatedClient.getId(),clientToUpdate.getUserName())).thenReturn(true);
        clientsService.updateClient(clientToUpdate, 1, false);
        verify(clientsRepository, times(1)).save(clientUpdated);
    }

    @Test
    public void deleteClient()throws UserAlreadyDeleted, UserAlreadyActive, UserNotExists{
        Clients notDeletedClient = Clients.builder().name("Dummy").lastName("Client").userName("DummyClient").password("DummyPassw0rd").active(true).dni(9999999).build();
        Clients deletedClient = Clients.builder().name("Dummy").lastName("Client").userName("DummyClient").password("DummyPassw0rd").active(false).dni(9999999).build();
        when(clientsRepository.findById(1)).thenReturn(Optional.ofNullable(notDeletedClient));
        when(clientsService.deleteClients(1)).thenReturn(deletedClient);
    }

    @Test(expected = UserAlreadyDeleted.class)
    public void deleteClientUserAlreadyDeleted()throws UserAlreadyDeleted, UserAlreadyActive, UserNotExists{
        Clients notDeletedClient = Clients.builder().name("Dummy").lastName("Client").userName("DummyClient").password("DummyPassw0rd").active(false).dni(9999999).build();
        Clients deletedClient = Clients.builder().name("Dummy").lastName("Client").userName("DummyClient").password("DummyPassw0rd").active(false).dni(9999999).build();
        when(clientsRepository.findById(1)).thenReturn(Optional.ofNullable(notDeletedClient));
        when(clientsService.deleteClients(1)).thenReturn(deletedClient);
    }

    @Test
    public void login(){
        Clients loggedClient = Clients.builder().name("Dummy").lastName("Client").userName("DummyClient").password("DummyPassw0rd").active(false).dni(9999999).build();
        Clients notLoggedClient = Clients.builder().name("Dummy").lastName("Client").userName("DummyClient").password("DummyPassw0rd").active(false).dni(9999999).build();
        when(clientsService.login(loggedClient.getUserName(), loggedClient.getPassword())).thenReturn(notLoggedClient);
        when(clientsRepository.findByUserNameAndPassword(loggedClient.getUserName(), loggedClient.getPassword())).thenReturn(loggedClient);
        clientsService.login(loggedClient.getUserName(), loggedClient.getPassword());
        verify(clientsRepository, times(1)).findByUserNameAndPassword(loggedClient.getUserName(), loggedClient.getPassword());
        assertEquals(loggedClient.getUserName(), notLoggedClient.getUserName());
    }

}

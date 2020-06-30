package edu.utn.TpFinal.Service;

import edu.utn.TpFinal.Exceptions.*;
import edu.utn.TpFinal.Projections.UserLine;
import edu.utn.TpFinal.model.Cities;
import edu.utn.TpFinal.model.Clients;
import edu.utn.TpFinal.model.DTO.LineDTO;
import edu.utn.TpFinal.model.Lines;
import edu.utn.TpFinal.model.Provinces;
import edu.utn.TpFinal.repository.CitiesRepository;
import edu.utn.TpFinal.repository.ClientsRepository;
import edu.utn.TpFinal.repository.EmployeesRepository;
import edu.utn.TpFinal.repository.LinesRespository;
import edu.utn.TpFinal.service.ClientsService;
import edu.utn.TpFinal.service.LinesService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class LinesServiceTest {

    LinesService linesService;
    LinesRespository linesRepository;
    ClientsRepository clientsRepository;
    CitiesRepository citiesRepository;
    ClientsService clientsService;
    EmployeesRepository employeeRepository;

    @Before
    public void onSetUp() {
        linesRepository= mock(LinesRespository.class);
        clientsRepository = mock(ClientsRepository.class);
        citiesRepository = mock(CitiesRepository.class);
        clientsService = new ClientsService(clientsRepository, linesRepository);
        linesService = new LinesService(linesRepository, clientsService, citiesRepository, clientsRepository, employeeRepository);
    }

    @Test
    public void addLines() throws ClientNotExists, InvalidType, InvalidPrefix, InvalidStatus, CityNotExists, InvalidPhoneNumber {
        Clients mockedCLient = Clients.builder().name("test").lastName("Valverde").dni(39137741).active(true)
                .password("123").userName("test").id(1).build();
        Provinces mockedProvince = Provinces.builder().id(1).name("Buenos Aires").active(true).build();
        Cities mockedCity = Cities.builder().id(1).active(true).name("Mar del Plata").shortName("MDQ").prefix(223).province(mockedProvince).build();
        Lines mockedLine = Lines.builder().city(mockedCity).client(mockedCLient).phoneNumber("2234822789").id(1).status(Lines.Status.ACTIVE).type(Lines.Type.MOBILE).build();
        when(clientsRepository.findById(mockedLine.getClient().getId())).thenReturn(java.util.Optional.of(mockedCLient));
        when(linesService.existsByLineNumber(mockedLine.getPhoneNumber())).thenReturn(false);
        when(clientsRepository.findByIdAndActiveTrue(mockedLine.getClient().getId())).thenReturn(java.util.Optional.of(mockedCLient));
        when(linesRepository.existsByPhoneNumber(mockedLine.getPhoneNumber())).thenReturn(false);
        when(citiesRepository.findById(mockedLine.getCity().getId())).thenReturn(java.util.Optional.of(mockedCity));
        when(linesService.addLine(mockedLine, mockedLine.getClient().getId())).thenReturn(mockedLine);
        linesService.addLine(mockedLine, mockedLine.getClient().getId());
        verify(linesRepository, times(1)).save(mockedLine);


    }

    @Test(expected = ClientNotExists.class)
    public void addLineClientNotExists() throws ClientNotExists, InvalidType, InvalidPrefix, CityNotExists, InvalidPhoneNumber, InvalidStatus {
        Clients mockedCLient = Clients.builder().name("test").lastName("Valverde").dni(39137741).active(true)
                .password("123").userName("test").id(1).build();
        Provinces mockedProvince = Provinces.builder().id(1).name("Buenos Aires").active(true).build();
        Cities mockedCity = Cities.builder().id(1).active(true).name("Mar del Plata").shortName("MDQ").prefix(223).province(mockedProvince).build();
        Lines mockedLine = Lines.builder().city(mockedCity).client(mockedCLient).phoneNumber("2234822789").id(1).status(Lines.Status.ACTIVE).type(Lines.Type.MOBILE).build();
        when(clientsRepository.findById(mockedLine.getClient().getId())).thenReturn(java.util.Optional.ofNullable(null));
        linesService.addLine(mockedLine, mockedLine.getClient().getId());
    }

    @Test(expected = InvalidPhoneNumber.class)
    public void verifyLineInvalidPhoneNumber() throws InvalidPhoneNumber, ClientNotExists, InvalidPrefix, InvalidStatus, InvalidType, CityNotExists {
        Clients mockedCLient = Clients.builder().name("test").lastName("Valverde").dni(39137741).active(true)
                .password("123").userName("test").id(1).build();
        Provinces mockedProvince = Provinces.builder().id(1).name("Buenos Aires").active(true).build();
        Cities mockedCity = Cities.builder().id(1).active(true).name("Mar del Plata").shortName("MDQ").prefix(223).province(mockedProvince).build();
        Lines mockedLine = Lines.builder().city(mockedCity).client(mockedCLient).phoneNumber("2234822789").id(1).status(Lines.Status.ACTIVE).type(Lines.Type.MOBILE).build();
        when(clientsRepository.findByIdAndActiveTrue(mockedLine.getClient().getId())).thenReturn(java.util.Optional.of(mockedCLient));
        when(linesRepository.existsByPhoneNumber(mockedLine.getPhoneNumber())).thenReturn(true);
        linesService.verifyLine(mockedLine);
    }

    @Test
    public void updateLine() throws LineNotExists, InvalidStatus, ClientNotExists, InvalidType, InvalidPrefix, InvalidPhoneNumber, DeletionNotAllowed, CityNotExists {
        Clients mockedCLient = Clients.builder().name("test").lastName("Valverde").dni(39137741).active(true)
                .password("123").userName("test").id(1).build();
        Provinces mockedProvince = Provinces.builder().id(1).name("Buenos Aires").active(true).build();
        Cities mockedCity = Cities.builder().id(1).active(true).name("Mar del Plata").shortName("MDQ").prefix(223).province(mockedProvince).build();
        Lines mockedLine = Lines.builder().city(mockedCity).client(mockedCLient).phoneNumber("2234822789").id(1).status(Lines.Status.ACTIVE).type(Lines.Type.MOBILE).build();
        LineDTO newLine = LineDTO.builder().city(mockedCity).phoneNumber("2234822788").status(Lines.Status.ACTIVE).type(Lines.Type.MOBILE).build();
        Lines updatedLine = Lines.builder().city(mockedCity).client(mockedCLient).phoneNumber("2234822788").id(1).status(Lines.Status.ACTIVE).type(Lines.Type.MOBILE).build();
        when(linesRepository.findById(mockedLine.getCity().getId())).thenReturn(java.util.Optional.of(mockedLine));
        when(linesRepository.existsByPhoneNumberAndClientNot(mockedLine.getPhoneNumber(), mockedCLient)).thenReturn(false);
        when(citiesRepository.findById(mockedLine.getCity().getId())).thenReturn(java.util.Optional.of(mockedCity));
        when(linesService.updateLine(newLine, mockedLine.getId())).thenReturn(updatedLine);
        linesService.updateLine(newLine, mockedLine.getId());
        verify(linesRepository, times(1)).save(updatedLine);
        assertEquals(updatedLine.getPhoneNumber(), mockedLine.getPhoneNumber());
    }

    @Test
    public void deleteLine() throws LineNotExists, ClientNotExists, LineAlreadyDeleted {
        Clients mockedCLient = Clients.builder().name("test").lastName("Valverde").dni(39137741).active(true)
                .password("123").userName("test").id(1).build();
        Provinces mockedProvince = Provinces.builder().id(1).name("Buenos Aires").active(true).build();
        Cities mockedCity = Cities.builder().id(1).active(true).name("Mar del Plata").shortName("MDQ").prefix(223).province(mockedProvince).build();
        Lines mockedLine = Lines.builder().city(mockedCity).client(mockedCLient).phoneNumber("2234822789").id(1).status(Lines.Status.ACTIVE).type(Lines.Type.MOBILE).build();
        when(linesRepository.findById(mockedLine.getCity().getId())).thenReturn(java.util.Optional.of(mockedLine));
        linesService.deleteLine(mockedLine.getId());
        verify(linesRepository, times(1)).save(mockedLine);
    }

    @Test(expected = LineAlreadyDeleted.class)
    public void deleteLineAlreadyDeleted() throws LineNotExists, ClientNotExists, LineAlreadyDeleted {
        Clients mockedCLient = Clients.builder().name("test").lastName("Valverde").dni(39137741).active(true)
                .password("123").userName("test").id(1).build();
        Provinces mockedProvince = Provinces.builder().id(1).name("Buenos Aires").active(true).build();
        Cities mockedCity = Cities.builder().id(1).active(true).name("Mar del Plata").shortName("MDQ").prefix(223).province(mockedProvince).build();
        Lines mockedLine = Lines.builder().city(mockedCity).client(mockedCLient).phoneNumber("2234822789").id(1).status(Lines.Status.CANCELLED).type(Lines.Type.MOBILE).build();
        when(linesRepository.findById(mockedLine.getCity().getId())).thenReturn(java.util.Optional.of(mockedLine));
        linesService.deleteLine(mockedLine.getId());
        verify(linesRepository, times(1)).save(mockedLine);
    }

    @Test(expected = LineNotExists.class)
    public void deleteLineLineNotExists() throws LineNotExists, ClientNotExists, LineAlreadyDeleted {
        Clients mockedCLient = Clients.builder().name("test").lastName("Valverde").dni(39137741).active(true)
                .password("123").userName("test").id(1).build();
        Provinces mockedProvince = Provinces.builder().id(1).name("Buenos Aires").active(true).build();
        Cities mockedCity = Cities.builder().id(1).active(true).name("Mar del Plata").shortName("MDQ").prefix(223).province(mockedProvince).build();
        Lines mockedLine = Lines.builder().city(mockedCity).client(mockedCLient).phoneNumber("2234822789").id(1).status(Lines.Status.CANCELLED).type(Lines.Type.MOBILE).build();
        when(linesRepository.findById(mockedLine.getCity().getId())).thenReturn(java.util.Optional.ofNullable(null));
        linesService.deleteLine(mockedLine.getId());
        verify(linesRepository, times(1)).save(mockedLine);
    }

    @Test(expected = LineNotExists.class)
    public void verifyClientAndLineLineNotExists() throws LineNotExists, ClientNotExists {
        Clients mockedCLient = Clients.builder().name("test").lastName("Valverde").dni(39137741).active(true)
                .password("123").userName("test").id(1).build();
        Provinces mockedProvince = Provinces.builder().id(1).name("Buenos Aires").active(true).build();
        Cities mockedCity = Cities.builder().id(1).active(true).name("Mar del Plata").shortName("MDQ").prefix(223).province(mockedProvince).build();
        Lines mockedLine = Lines.builder().city(mockedCity).client(mockedCLient).phoneNumber("2234822789").id(1).status(Lines.Status.CANCELLED).type(Lines.Type.MOBILE).build();
        when(clientsRepository.findById(mockedLine.getClient().getId())).thenReturn(java.util.Optional.of(mockedCLient));
        when(linesRepository.existsByIdAndClient(mockedLine.getId(), mockedCLient)).thenReturn(false);
        linesService.verifyClientAndLine(mockedLine.getClient().getId(), mockedLine.getId());
    }

    @Test
    public void verifyClientAndLine() throws LineNotExists, ClientNotExists {
        Clients mockedCLient = Clients.builder().name("test").lastName("Valverde").dni(39137741).active(true)
                .password("123").userName("test").id(1).build();
        Provinces mockedProvince = Provinces.builder().id(1).name("Buenos Aires").active(true).build();
        Cities mockedCity = Cities.builder().id(1).active(true).name("Mar del Plata").shortName("MDQ").prefix(223).province(mockedProvince).build();
        Lines mockedLine = Lines.builder().city(mockedCity).client(mockedCLient).phoneNumber("2234822789").id(1).status(Lines.Status.CANCELLED).type(Lines.Type.MOBILE).build();
        when(clientsRepository.findById(mockedLine.getClient().getId())).thenReturn(java.util.Optional.of(mockedCLient));
        when(linesRepository.existsByIdAndClient(mockedLine.getId(), mockedCLient)).thenReturn(true);
        linesService.verifyClientAndLine(mockedLine.getClient().getId(), mockedLine.getId());
    }

    @Test
    public void getLineByClient() throws LineNotExists, ClientNotExists {
        ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
        UserLine userLine = factory.createProjection(UserLine.class);
        userLine.setPhoneNumber("2234822789");
        userLine.setType("ACTIVE");
        Clients mockedCLient = Clients.builder().name("test").lastName("Valverde").dni(39137741).active(true)
                .password("123").userName("test").id(1).build();
        Provinces mockedProvince = Provinces.builder().id(1).name("Buenos Aires").active(true).build();
        Cities mockedCity = Cities.builder().id(1).active(true).name("Mar del Plata").shortName("MDQ").prefix(223).province(mockedProvince).build();
        Lines mockedLine = Lines.builder().city(mockedCity).client(mockedCLient).phoneNumber("2234822789").id(1).status(Lines.Status.ACTIVE).type(Lines.Type.MOBILE).build();
        when(clientsRepository.findById(mockedLine.getClient().getId())).thenReturn(java.util.Optional.of(mockedCLient));
        when(linesRepository.existsByIdAndClient(mockedLine.getId(), mockedCLient)).thenReturn(true);
        when(linesRepository.findByIDAndByClient(mockedLine.getId(),mockedCLient.getId())).thenReturn(userLine);
        assertEquals(linesService.getLineByClient(mockedLine.getClient().getId(), mockedLine.getId()), userLine);
        verify(linesRepository, times(1)).existsByIdAndClient(mockedLine.getId(), mockedCLient);
        verify(linesRepository, times(1)).findByIDAndByClient(mockedLine.getId(),mockedCLient.getId());
    }

    @Test
    public void findByClient() throws ClientNotExists {
        ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
        Pageable pageable = PageRequest.of(0,1);
        UserLine userLine = factory.createProjection(UserLine.class);
        Page<UserLine> userLinePage = new PageImpl<>(Collections.singletonList(userLine));
        Clients mockedCLient = Clients.builder().name("test").lastName("Valverde").dni(39137741).active(true)
                .password("123").userName("test").id(1).build();
        Provinces mockedProvince = Provinces.builder().id(1).name("Buenos Aires").active(true).build();
        Cities mockedCity = Cities.builder().id(1).active(true).name("Mar del Plata").shortName("MDQ").prefix(223).province(mockedProvince).build();
        Lines mockedLine = Lines.builder().city(mockedCity).client(mockedCLient).phoneNumber("2234822789").id(1).status(Lines.Status.CANCELLED).type(Lines.Type.MOBILE).build();
        when(clientsRepository.findById(mockedLine.getClient().getId())).thenReturn(java.util.Optional.of(mockedCLient));
        when(linesRepository.findByClient(pageable, mockedCLient)).thenReturn(userLinePage);
        linesService.findByClient(pageable, mockedCLient.getId());
        verify(clientsRepository, times(1)).findById(mockedLine.getClient().getId());
        verify(linesRepository, times(1)).findByClient(pageable, mockedCLient);
        assertEquals(linesRepository.findByClient(pageable, mockedCLient).getTotalPages(), 1);
    }

    @Test(expected = ClientNotExists.class)
    public void findByClientClientNotExists() throws ClientNotExists {
        ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
        Pageable pageable = PageRequest.of(0,1);
        UserLine userLine = factory.createProjection(UserLine.class);
        Page<UserLine> userLinePage = new PageImpl<>(Collections.singletonList(userLine));
        Clients mockedCLient = Clients.builder().name("test").lastName("Valverde").dni(39137741).active(true)
                .password("123").userName("test").id(1).build();
        Provinces mockedProvince = Provinces.builder().id(1).name("Buenos Aires").active(true).build();
        Cities mockedCity = Cities.builder().id(1).active(true).name("Mar del Plata").shortName("MDQ").prefix(223).province(mockedProvince).build();
        Lines mockedLine = Lines.builder().city(mockedCity).client(mockedCLient).phoneNumber("2234822789").id(1).status(Lines.Status.CANCELLED).type(Lines.Type.MOBILE).build();
        when(clientsRepository.findById(mockedLine.getClient().getId())).thenReturn(java.util.Optional.ofNullable(null));
        when(linesRepository.findByClient(pageable, mockedCLient)).thenReturn(userLinePage);
        linesService.findByClient(pageable, mockedCLient.getId());
        verify(clientsRepository, times(1)).findById(mockedLine.getClient().getId());
    }

    @Test
    public void findByLineNumber() throws LineNotExists {
        Clients mockedCLient = Clients.builder().name("test").lastName("Valverde").dni(39137741).active(true)
                .password("123").userName("test").id(1).build();
        Provinces mockedProvince = Provinces.builder().id(1).name("Buenos Aires").active(true).build();
        Cities mockedCity = Cities.builder().id(1).active(true).name("Mar del Plata").shortName("MDQ").prefix(223).province(mockedProvince).build();
        Lines mockedLine = Lines.builder().city(mockedCity).client(mockedCLient).phoneNumber("2234822789").id(1).status(Lines.Status.ACTIVE).type(Lines.Type.MOBILE).build();
        when(linesRepository.findByPhoneNumber(mockedLine.getPhoneNumber())).thenReturn(java.util.Optional.of(mockedLine));
        Lines newLine = linesService.findByLineNumber(mockedLine.getPhoneNumber());
        assertEquals(newLine.getPhoneNumber(), mockedLine.getPhoneNumber());
        verify(linesRepository, times(1)).findByPhoneNumber(mockedLine.getPhoneNumber());
    }

    @Test(expected = LineNotExists.class)
    public void findByLineNumberLineNotExists() throws LineNotExists {
        Clients mockedCLient = Clients.builder().name("test").lastName("Valverde").dni(39137741).active(true)
                .password("123").userName("test").id(1).build();
        Provinces mockedProvince = Provinces.builder().id(1).name("Buenos Aires").active(true).build();
        Cities mockedCity = Cities.builder().id(1).active(true).name("Mar del Plata").shortName("MDQ").prefix(223).province(mockedProvince).build();
        Lines mockedLine = Lines.builder().city(mockedCity).client(mockedCLient).phoneNumber("2234822789").id(1).status(Lines.Status.ACTIVE).type(Lines.Type.MOBILE).build();
        when(linesRepository.findByPhoneNumber(mockedLine.getPhoneNumber())).thenReturn(java.util.Optional.ofNullable(null));
        linesService.findByLineNumber(mockedLine.getPhoneNumber());
    }

}
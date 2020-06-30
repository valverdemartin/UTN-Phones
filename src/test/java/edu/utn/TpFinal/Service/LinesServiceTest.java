package edu.utn.TpFinal.Service;

import edu.utn.TpFinal.Exceptions.*;
import edu.utn.TpFinal.model.Cities;
import edu.utn.TpFinal.model.Clients;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

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

}
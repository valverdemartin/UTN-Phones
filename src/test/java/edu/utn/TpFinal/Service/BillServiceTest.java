package edu.utn.TpFinal.Service;

import edu.utn.TpFinal.Exceptions.BillNotExists;
import edu.utn.TpFinal.Exceptions.ClientNotExists;
import edu.utn.TpFinal.Exceptions.LineNotExists;
import edu.utn.TpFinal.Projections.UserBills;
import edu.utn.TpFinal.model.Cities;
import edu.utn.TpFinal.model.Clients;
import edu.utn.TpFinal.model.Lines;
import edu.utn.TpFinal.repository.BillsRepository;
import edu.utn.TpFinal.repository.ClientsRepository;
import edu.utn.TpFinal.repository.LinesRespository;
import edu.utn.TpFinal.service.BillsService;
import edu.utn.TpFinal.service.LinesService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class BillServiceTest {

    private LinesService linesService;
    private LinesRespository linesRespository;
    private BillsRepository billsRepository;
    private ClientsRepository clientsRepository;
    private BillsService billsService;


    @Before
    public void onSetUp() {
        billsRepository = mock(BillsRepository.class);
        linesService = mock(LinesService.class);
        linesRespository = mock(LinesRespository.class);
        clientsRepository = mock(ClientsRepository.class);
        billsService = new BillsService(billsRepository, linesService, clientsRepository);
    }

    @Test
    public void getUserBills() throws LineNotExists, ClientNotExists, BillNotExists {
        Integer clientId = 1;
        Integer lineId = 1;
        Clients mockedClient = Clients.builder().name("test").lastName("Valverde").dni(39137741).active(false)
                .line(null).password("123").userName("test").id(clientId).build();
        Lines mockedLine = Lines.builder().city(Cities.builder().id(lineId).build()).client(mockedClient).phoneNumber("2234822789").id(1).status(Lines.Status.ACTIVE).type(Lines.Type.MOBILE).build();
        Timestamp date = Timestamp.valueOf(LocalDateTime.now());
        Pageable pageable = PageRequest.of(0,1);
        ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
        UserBills userBills = factory.createProjection(UserBills.class);
        userBills.setActive(true);
        userBills.setBillDate(Date.valueOf(LocalDate.now()));
        userBills.setCallCounter(100);
        userBills.setCostPrice(10.5);
        userBills.setLine(mockedLine);
        userBills.setTotalPrice(userBills.getCostPrice()*userBills.getCallCounter());
        userBills.setDueDate(Date.valueOf(LocalDate.now()));
        Page<UserBills> userBillsPage = new PageImpl<>(Collections.singletonList(userBills));
        when(clientsRepository.findById(clientId)).thenReturn(Optional.ofNullable(mockedClient));
        when(linesRespository.existsByIdAndClient(lineId, mockedClient)).thenReturn(true);
        when(linesService.findById(lineId)).thenReturn(mockedLine);
        when(billsRepository.findByBillDateBetweenAndLine(pageable, date, date, mockedLine)).thenReturn(userBillsPage);
        Page<UserBills> pg = billsService.getUserBills(pageable, clientId, lineId, date,  date);
        assertEquals(userBillsPage, pg);
        verify(linesService, times(1)).findById(lineId);
        verify(billsRepository, times(1)).findByBillDateBetweenAndLine(pageable, date, date, mockedLine);
    }

    @Test
    public void getUserBillsFromAndToNulls() throws LineNotExists, ClientNotExists, BillNotExists {
        Integer clientId = 1;
        Integer lineId = 1;
        Clients mockedClient = Clients.builder().name("test").lastName("Valverde").dni(39137741).active(false)
                .line(null).password("123").userName("test").id(clientId).build();
        Lines mockedLine = Lines.builder().city(Cities.builder().id(lineId).build()).client(mockedClient).phoneNumber("2234822789").id(1).status(Lines.Status.ACTIVE).type(Lines.Type.MOBILE).build();
        Timestamp date = Timestamp.valueOf(LocalDateTime.now());
        Pageable pageable = PageRequest.of(0,1);
        ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
        UserBills userBills = factory.createProjection(UserBills.class);
        userBills.setActive(true);
        userBills.setBillDate(Date.valueOf(LocalDate.now()));
        userBills.setCallCounter(100);
        userBills.setCostPrice(10.5);
        userBills.setLine(mockedLine);
        userBills.setTotalPrice(userBills.getCostPrice()*userBills.getCallCounter());
        userBills.setDueDate(Date.valueOf(LocalDate.now()));
        Page<UserBills> userBillsPage = new PageImpl<>(Collections.singletonList(userBills));
        when(clientsRepository.findById(clientId)).thenReturn(Optional.ofNullable(mockedClient));
        when(linesRespository.existsByIdAndClient(lineId, mockedClient)).thenReturn(true);
        when(linesService.findById(lineId)).thenReturn(mockedLine);
        when(billsRepository.findByLine(pageable, mockedLine)).thenReturn(userBillsPage);
        Page<UserBills> pg = billsService.getUserBills(pageable, clientId, lineId, null,  null);
        assertEquals(userBillsPage, pg);
        verify(linesService, times(1)).findById(lineId);
        verify(billsRepository, times(1)).findByLine(pageable, mockedLine);
    }

    @Test
    public void getUserBillsToNulls() throws LineNotExists, ClientNotExists, BillNotExists {
        Integer clientId = 1;
        Integer lineId = 1;
        Clients mockedClient = Clients.builder().name("test").lastName("Valverde").dni(39137741).active(false)
                .line(null).password("123").userName("test").id(clientId).build();
        Lines mockedLine = Lines.builder().city(Cities.builder().id(lineId).build()).client(mockedClient).phoneNumber("2234822789").id(1).status(Lines.Status.ACTIVE).type(Lines.Type.MOBILE).build();
        Timestamp date = Timestamp.valueOf(LocalDateTime.now());
        Pageable pageable = PageRequest.of(0,1);
        ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
        UserBills userBills = factory.createProjection(UserBills.class);
        userBills.setActive(true);
        userBills.setBillDate(Date.valueOf(LocalDate.now()));
        userBills.setCallCounter(100);
        userBills.setCostPrice(10.5);
        userBills.setLine(mockedLine);
        userBills.setTotalPrice(userBills.getCostPrice()*userBills.getCallCounter());
        userBills.setDueDate(Date.valueOf(LocalDate.now()));
        Page<UserBills> userBillsPage = new PageImpl<>(Collections.singletonList(userBills));
        when(clientsRepository.findById(clientId)).thenReturn(Optional.ofNullable(mockedClient));
        when(linesRespository.existsByIdAndClient(lineId, mockedClient)).thenReturn(true);
        when(linesService.findById(lineId)).thenReturn(mockedLine);
        when(billsRepository.findByLine(pageable, mockedLine)).thenReturn(userBillsPage);
        Page<UserBills> pg = billsService.getUserBills(pageable, clientId, lineId, date,  null);
        assertEquals(userBillsPage, pg);
        verify(linesService, times(1)).findById(lineId);
        verify(billsRepository, times(1)).findByLine(pageable, mockedLine);
    }

    @Test
    public void getUserBillsFromNulls() throws LineNotExists, ClientNotExists, BillNotExists {
        Integer clientId = 1;
        Integer lineId = 1;
        Clients mockedClient = Clients.builder().name("test").lastName("Valverde").dni(39137741).active(false)
                .line(null).password("123").userName("test").id(clientId).build();
        Lines mockedLine = Lines.builder().city(Cities.builder().id(lineId).build()).client(mockedClient).phoneNumber("2234822789").id(1).status(Lines.Status.ACTIVE).type(Lines.Type.MOBILE).build();
        Timestamp date = Timestamp.valueOf(LocalDateTime.now());
        Pageable pageable = PageRequest.of(0,1);
        ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
        UserBills userBills = factory.createProjection(UserBills.class);
        userBills.setActive(true);
        userBills.setBillDate(Date.valueOf(LocalDate.now()));
        userBills.setCallCounter(100);
        userBills.setCostPrice(10.5);
        userBills.setLine(mockedLine);
        userBills.setTotalPrice(userBills.getCostPrice()*userBills.getCallCounter());
        userBills.setDueDate(Date.valueOf(LocalDate.now()));
        Page<UserBills> userBillsPage = new PageImpl<>(Collections.singletonList(userBills));
        when(clientsRepository.findById(clientId)).thenReturn(Optional.ofNullable(mockedClient));
        when(linesRespository.existsByIdAndClient(lineId, mockedClient)).thenReturn(true);
        when(linesService.findById(lineId)).thenReturn(mockedLine);
        when(billsRepository.findByLine(pageable, mockedLine)).thenReturn(userBillsPage);
        Page<UserBills> pg = billsService.getUserBills(pageable, clientId, lineId, null,  date);
        assertEquals(userBillsPage, pg);
        verify(linesService, times(1)).findById(lineId);
        verify(billsRepository, times(1)).findByLine(pageable, mockedLine);
    }



}

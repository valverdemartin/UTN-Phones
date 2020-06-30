package edu.utn.TpFinal.controller.web;

import edu.utn.TpFinal.Exceptions.ClientNotExists;
import edu.utn.TpFinal.Exceptions.LineNotExists;
import edu.utn.TpFinal.Projections.UserCalls;
import edu.utn.TpFinal.controller.*;
import edu.utn.TpFinal.model.Cities;
import edu.utn.TpFinal.model.Provinces;
import edu.utn.TpFinal.model.Rates;
import edu.utn.TpFinal.model.UserLogin;
import edu.utn.TpFinal.session.SessionManager;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class BackOfficeControllerTest {

    CallsController callsController;
    BillsController billsController;
    SessionManager sessionManager;
    UserController userController;
    RatesController ratesController;
    LinesController linesController;
    ClientsController clientsController;
    BackOfficeController backOfficeController;

    @Before
    public void onSetUp() {
        callsController = mock(CallsController.class);
        billsController = mock(BillsController.class);
        sessionManager = mock(SessionManager.class);
        userController = mock(UserController.class);
        ratesController = mock(RatesController.class);
        linesController = mock(LinesController.class);
        clientsController = mock(ClientsController.class);
        backOfficeController = new BackOfficeController(clientsController, linesController, ratesController, callsController, billsController);
    }

    @Test
    public void getUsersCalls() throws LineNotExists, ClientNotExists {
        String token = "123";
        Integer lineId = 1;
        Integer clientId= 1;
        UserLogin userLogin = UserLogin.builder().id(1).build();
        Timestamp date = Timestamp.valueOf(LocalDateTime.now());
        ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
        UserCalls userCalls = factory.createProjection(UserCalls.class);
        userCalls.setCallDate(date);
        userCalls.setDestNumber("2235520040");
        userCalls.setDuration(200);
        userCalls.setTotalPrice(200.5);
        Pageable pageable = PageRequest.of(0,1);
        Page<UserCalls> userBillsPage = new PageImpl<>(Collections.singletonList(userCalls));
        when(sessionManager.getCurrentUserDTO(token)).thenReturn(userLogin);
        when(callsController.getUserCalls(pageable,userLogin.getId(), lineId, date, date)).thenReturn(userBillsPage);
        ResponseEntity<Page<UserCalls>> responseEntity = backOfficeController.getUsersCalls(pageable, clientId, lineId, date, date);
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        assertEquals(userBillsPage, responseEntity.getBody());
    }

    @Test
    public void getRates(){
        Provinces mockedProvince = Provinces.builder().id(1).name("Buenos Aires").active(true).build();
        Cities mockedCity = Cities.builder().id(1).active(true).name("Mar del Plata").shortName("MDQ").province(mockedProvince).build();
        Rates mockedRate = Rates.builder().id(1).pricePerMinute(1).costPrice(0.5).rateDate(Timestamp.valueOf(LocalDateTime.now())).destCity(mockedCity).originCity(mockedCity).build();
        Pageable pageable = PageRequest.of(0,1);
        Page<Rates> ratePage = new PageImpl<>(Collections.singletonList(mockedRate));
        UserLogin userLogin = UserLogin.builder().id(1).build();
        String token = "123";
        when(sessionManager.getCurrentUserDTO(token)).thenReturn(userLogin);
        when(ratesController.getRates(pageable)).thenReturn(ratePage);
        backOfficeController.getRates(pageable);
        verify(ratesController, times(1)).getRates(pageable);
        assertEquals(pageable.getPageSize(), 1);
    }


}

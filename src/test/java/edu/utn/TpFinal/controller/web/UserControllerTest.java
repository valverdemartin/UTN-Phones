package edu.utn.TpFinal.controller.web;

import edu.utn.TpFinal.Exceptions.BillNotExists;
import edu.utn.TpFinal.Exceptions.ClientNotExists;
import edu.utn.TpFinal.Exceptions.LineNotExists;
import edu.utn.TpFinal.Projections.TopCalls;
import edu.utn.TpFinal.Projections.UserBills;
import edu.utn.TpFinal.Projections.UserCalls;
import edu.utn.TpFinal.controller.BillsController;
import edu.utn.TpFinal.controller.CallsController;
import edu.utn.TpFinal.model.DTO.UserDTO;
import edu.utn.TpFinal.model.Lines;
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

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    CallsController callsController;
    BillsController billsController;
    SessionManager sessionManager;
    UserController userController;

    @Before
    public void onSetUp() {
        callsController = mock(CallsController.class);
        billsController = mock(BillsController.class);
        sessionManager = mock(SessionManager.class);
        userController = new UserController(callsController, billsController, sessionManager);
    }

    @Test
    public void getUsersCalls() throws LineNotExists, ClientNotExists {
        String token = "123";
        Integer lineId = 1;
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
        ResponseEntity<Page<UserCalls>> responseEntity = userController.getUsersCalls(token,pageable,lineId,date,date);
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        assertEquals(userBillsPage, responseEntity.getBody());
    }

    @Test
    public void getUsersCallsEmpty() throws LineNotExists, ClientNotExists {
        String token = "123";
        Integer lineId = 1;
        UserLogin userLogin = UserLogin.builder().id(1).build();
        Timestamp date = Timestamp.valueOf(LocalDateTime.now());
        ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
        UserCalls userCalls = factory.createProjection(UserCalls.class);
        userCalls.setCallDate(date);
        userCalls.setDestNumber("2235520040");
        userCalls.setDuration(200);
        userCalls.setTotalPrice(200.5);
        Pageable pageable = PageRequest.of(0,1);
        Page<UserCalls> userCallsPage = new PageImpl<>(Collections.emptyList());
        when(sessionManager.getCurrentUserDTO(token)).thenReturn(userLogin);
        when(callsController.getUserCalls(pageable,userLogin.getId(), lineId, date, date)).thenReturn(userCallsPage);
        ResponseEntity<Page<UserCalls>> responseEntity = userController.getUsersCalls(token,pageable,lineId,date,date);
        assertEquals(HttpStatus.NO_CONTENT,responseEntity.getStatusCode());
    }

    @Test
    public void getUserBills() throws LineNotExists, BillNotExists, ClientNotExists {
        String token = "123";
        Integer lineId = 1;
        Timestamp date = Timestamp.valueOf(LocalDateTime.now());
        UserLogin userLogin = UserLogin.builder().id(1).build();
        Date todayDate =  Date.valueOf(LocalDate.now());
        Lines mockedLine = Lines.builder().city(null).client(null).phoneNumber("2234822789").build();
        ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
        UserBills mockedBill = factory.createProjection(UserBills.class);
        mockedBill.setActive(true);
        mockedBill.setBillDate(todayDate);
        mockedBill.setCallCounter(10);
        mockedBill.setCostPrice(1.0);
        mockedBill.setTotalPrice(1.5);
        mockedBill.setLine(mockedLine);
        Pageable pageable = PageRequest.of(0,1);
        Page<UserBills> userBillsPage = new PageImpl<>(Collections.singletonList(mockedBill));
        when(sessionManager.getCurrentUserDTO(token)).thenReturn(userLogin);
        when(billsController.getUserBills(pageable,userLogin.getId(), lineId, date, date)).thenReturn(userBillsPage);
        ResponseEntity<Page<UserBills>> responseEntity = userController.getUserBills(token,pageable,lineId,date,date);
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        assertEquals(userBillsPage, responseEntity.getBody());
    }

    @Test
    public void getUserBillsEmpty() throws LineNotExists, BillNotExists, ClientNotExists {
        String token = "123";
        Integer lineId = 1;
        Timestamp date = Timestamp.valueOf(LocalDateTime.now());
        UserLogin userLogin = UserLogin.builder().id(1).build();
        Pageable pageable = PageRequest.of(0,1);
        Page<UserBills> userBillsPage = new PageImpl<>(Collections.emptyList());
        when(sessionManager.getCurrentUserDTO(token)).thenReturn(userLogin);
        when(billsController.getUserBills(pageable,userLogin.getId(), lineId, date, date)).thenReturn(userBillsPage);
        ResponseEntity<Page<UserBills>> responseEntity = userController.getUserBills(token,pageable,lineId,date,date);
        assertEquals(HttpStatus.NO_CONTENT,responseEntity.getStatusCode());
    }

    @Test
    public void findTop10Calls() throws LineNotExists, ClientNotExists {
        String token = "123";
        Integer lineId = 1;
        UserLogin userLogin = UserLogin.builder().id(1).build();
        ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
        TopCalls topCalls = factory.createProjection(TopCalls.class);
        topCalls.setDestNumber("2235520040");
        topCalls.setCount(20);
        List<TopCalls> list = Arrays.asList(topCalls);
        when(sessionManager.getCurrentUserDTO(token)).thenReturn(userLogin);
        when(callsController.findTop10Calls(userLogin.getId(), lineId)).thenReturn(list);
        ResponseEntity<List<TopCalls>> responseEntity = userController.findTop10Calls(token,lineId);
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
    }

    @Test
    public void findTop10CallsEmpty() throws LineNotExists, ClientNotExists {
        String token = "123";
        Integer lineId = 1;
        UserLogin userLogin = UserLogin.builder().id(1).build();
        when(sessionManager.getCurrentUserDTO(token)).thenReturn(userLogin);
        when(callsController.findTop10Calls(userLogin.getId(), lineId)).thenReturn(Collections.emptyList());
        ResponseEntity<List<TopCalls>> responseEntity = userController.findTop10Calls(token,lineId);
        assertEquals(HttpStatus.NO_CONTENT,responseEntity.getStatusCode());
    }
}

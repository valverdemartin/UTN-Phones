package edu.utn.TpFinal.controller;

import edu.utn.TpFinal.Exceptions.BillNotExists;
import edu.utn.TpFinal.Exceptions.ClientNotExists;
import edu.utn.TpFinal.Exceptions.LineNotExists;
import edu.utn.TpFinal.Projections.UserBills;
import edu.utn.TpFinal.model.*;
import edu.utn.TpFinal.service.BillsService;
import org.apache.catalina.User;
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

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class BillsControllerTest {

    BillsController billsController;
    BillsService billsService;

    @Before
    public void onSetUp() {
        billsService = mock(BillsService.class);
        billsController = new BillsController(billsService);
    }

    @Test
    public void getUserBills()throws LineNotExists, BillNotExists, ClientNotExists {
        Date todayDate =  Date.valueOf(LocalDate.now());
        Timestamp todayTimeStamp = Timestamp.valueOf(LocalDateTime.now());
        Pageable pageable = PageRequest.of(0,1);
        Clients mockedCLient = Clients.builder().name("test").lastName("Valverde").dni(39137741).active(true)
                .line(null).password("123").userName("test").build();
        Provinces mockedProvince = Provinces.builder().id(1).name("Buenos Aires").active(true).build();
        Cities mockedCity = Cities.builder().id(1).active(true).name("Mar del Plata").shortName("MDQ").province(mockedProvince).build();
        Lines mockedLine = Lines.builder().city(mockedCity).client(mockedCLient).phoneNumber("2234822789").build();
        ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
        UserBills mockedBill = factory.createProjection(UserBills.class);
        mockedBill.setActive(true);
        mockedBill.setBillDate(todayDate);
        mockedBill.setCallCounter(10);
        mockedBill.setCostPrice(1.0);
        mockedBill.setTotalPrice(1.5);
        mockedBill.setLine(mockedLine);
        Page<UserBills> billsPage = new PageImpl<UserBills>(Collections.singletonList(mockedBill));
        when(billsController.getUserBills(pageable, 1, 1, todayTimeStamp, todayTimeStamp)).thenReturn(billsPage);
        billsController.getUserBills(pageable, 1, 1, todayTimeStamp, todayTimeStamp);
        verify(billsService, times(1)).getUserBills(pageable, 1, 1, todayTimeStamp, todayTimeStamp);

    }

}

package edu.utn.TpFinal.controller;

import edu.utn.TpFinal.Exceptions.RateAlreadyExists;
import edu.utn.TpFinal.Exceptions.RateNotExists;
import edu.utn.TpFinal.model.Cities;
import edu.utn.TpFinal.model.Clients;
import edu.utn.TpFinal.model.DTO.RateDTO;
import edu.utn.TpFinal.model.Provinces;
import edu.utn.TpFinal.model.Rates;
import edu.utn.TpFinal.repository.RatesRepository;
import edu.utn.TpFinal.service.RatesService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class RatesControllerTest {

    RatesController ratesController;
    RatesService ratesService;

    @Before
    public void onSetUp() {
        ratesService = mock(RatesService.class);
        ratesController = new RatesController(ratesService);
    }

    @Test
    public void getRates() {
        Provinces mockedProvince = Provinces.builder().id(1).name("Buenos Aires").active(true).build();
        Cities mockedCity = Cities.builder().id(1).active(true).name("Mar del Plata").shortName("MDQ").province(mockedProvince).build();
        Pageable pageable = PageRequest.of(0,1);
        Rates mockedRate = Rates.builder().id(1).pricePerMinute(1).costPrice(0.5).rateDate(Timestamp.valueOf(LocalDateTime.now())).destCity(mockedCity).originCity(mockedCity).build();
        Page<Rates> ratesPage = new PageImpl<Rates>(Collections.singletonList(mockedRate));
        when(ratesController.getRates(pageable)).thenReturn(ratesPage);
        ratesController.getRates(pageable);
        verify(ratesService, times(1)).getRates(pageable);
    }

    @Test
    public void getRatesById()throws RateNotExists {
        Rates resultRate = null;
        when(ratesController.getRatesById(1)).thenReturn(resultRate);
        ratesController.getRatesById(1);
        verify(ratesService, times(1)).getRatesById(1);
    }

    @Test(expected = RateNotExists.class)
    public void getRatesByIdNotExists()throws RateNotExists {
        when(ratesController.getRatesById(1)).thenThrow(new RateNotExists());
        ratesController.getRatesById(1);
        verify(ratesService, times(1)).getRatesById(1);
    }

    @Test
    public void addRate()throws RateAlreadyExists{
        Provinces mockedProvince = Provinces.builder().id(1).name("Buenos Aires").active(true).build();
        Cities mockedCity = Cities.builder().id(1).active(true).name("Mar del Plata").shortName("MDQ").province(mockedProvince).build();
        RateDTO newRateDTO = RateDTO.builder().pricePerMinute(1).costPrice(0.5).rateDate(Timestamp.valueOf(LocalDateTime.now())).destCity(mockedCity).originCity(mockedCity).build();
        Rates returnRate = null;
        when(ratesController.addRate(newRateDTO)).thenReturn(returnRate);
        ratesController.addRate(newRateDTO);
        verify(ratesService, times(1)).addRate(newRateDTO);
    }

    @Test(expected = RateAlreadyExists.class)
    public void addRateAlreadyExist()throws RateAlreadyExists{
        Provinces mockedProvince = Provinces.builder().id(1).name("Buenos Aires").active(true).build();
        Cities mockedCity = Cities.builder().id(1).active(true).name("Mar del Plata").shortName("MDQ").province(mockedProvince).build();
        RateDTO newRateDTO = RateDTO.builder().pricePerMinute(1).costPrice(0.5).rateDate(Timestamp.valueOf(LocalDateTime.now())).destCity(mockedCity).originCity(mockedCity).build();
        when(ratesController.addRate(newRateDTO)).thenThrow(new RateAlreadyExists());
        ratesController.addRate(newRateDTO);
        verify(ratesService, times(1)).addRate(newRateDTO);
    }

}

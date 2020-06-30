package edu.utn.TpFinal.Service;

import edu.utn.TpFinal.Exceptions.CityNotExists;
import edu.utn.TpFinal.Exceptions.RateAlreadyExists;
import edu.utn.TpFinal.model.Cities;
import edu.utn.TpFinal.model.DTO.RateDTO;
import edu.utn.TpFinal.model.Provinces;
import edu.utn.TpFinal.model.Rates;
import edu.utn.TpFinal.repository.CitiesRepository;
import edu.utn.TpFinal.repository.RatesRepository;
import edu.utn.TpFinal.service.RatesService;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class RatesServicesTest {

    private RatesRepository ratesRepository;
    private RatesService ratesService;
    private CitiesRepository citiesRepository;

    @Before
    public void onSetUp() {
        ratesRepository= mock(RatesRepository.class);
        citiesRepository=mock(CitiesRepository.class);
        ratesService = new RatesService(ratesRepository, citiesRepository);
    }

    @Test
    public void addRate() throws RateAlreadyExists, CityNotExists {
        Provinces mockedProvince = Provinces.builder().id(1).name("Buenos Aires").active(true).build();
        Cities mockedCity = Cities.builder().id(1).active(true).name("Mar del Plata").shortName("MDQ").province(mockedProvince).build();
        RateDTO newRateDTO = RateDTO.builder().pricePerMinute(1).costPrice(0.5).rateDate(Timestamp.valueOf(LocalDateTime.now())).destCity(mockedCity).originCity(mockedCity).build();
        Rates rate = Rates.builder()
                .rateDate(Timestamp.valueOf(LocalDateTime.now()))
                .pricePerMinute(1)
                .originCity(mockedCity)
                .destCity(mockedCity)
                .costPrice(0.5)
                .id(1)
                .build();
        when(citiesRepository.existsById(any(Integer.class))).thenReturn(true);
        when(ratesRepository.existsByOriginCityAndDestCityAndRateDate(newRateDTO.getOriginCity(),
                newRateDTO.getDestCity(), Timestamp.valueOf(LocalDateTime.now()))).thenReturn(false);
        when(ratesRepository.save(any(Rates.class))).thenReturn(rate);
        Rates r = ratesService.addRate(newRateDTO);
        assertEquals(rate, r);
        verify(ratesRepository, times(1)).save(any(Rates.class));
    }

    @Test(expected = RateAlreadyExists.class)
    public void addRateRateAlreadyExists() throws RateAlreadyExists, CityNotExists {
        Provinces mockedProvince = Provinces.builder().id(1).name("Buenos Aires").active(true).build();
        Cities mockedCity = Cities.builder().id(1).active(true).name("Mar del Plata").shortName("MDQ").province(mockedProvince).build();
        RateDTO newRateDTO = RateDTO.builder().pricePerMinute(1).costPrice(0.5).rateDate(Timestamp.valueOf(LocalDateTime.now())).destCity(mockedCity).originCity(mockedCity).build();
        when(citiesRepository.existsById(any(Integer.class))).thenReturn(true);
        when(ratesRepository.existsByOriginCityAndDestCityAndRateDate(any(Cities.class),
                    any(Cities.class), any(Timestamp.class))).thenReturn(true);
        Rates r = ratesService.addRate(newRateDTO);

    }
}

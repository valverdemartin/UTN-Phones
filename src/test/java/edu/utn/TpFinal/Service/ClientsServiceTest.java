package edu.utn.TpFinal.Service;
import edu.utn.TpFinal.Exceptions.UserNotExist;
import edu.utn.TpFinal.Projections.DurationByMonth;
import edu.utn.TpFinal.Projections.UserCityLastCallDuration;
import edu.utn.TpFinal.repository.ClientsRepository;
import edu.utn.TpFinal.repository.LinesRespository;
import edu.utn.TpFinal.service.ClientsService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
    public void DurationByMonthOk(){
        Integer idUser = 1;
        Integer selectedMonth = 5;
        DurationByMonth durationByMonth = null;
        when(clientsRepository.existsById(idUser)).thenReturn(true);
        when(clientsRepository.getDurationByMont(idUser, selectedMonth)).thenReturn(durationByMonth);
        clientsService.getDurationByMont(idUser,selectedMonth);
    }

    @Test(expected = UserNotExist.class)
    public void DurationByMonthFail() throws UserNotExist{
        Integer idUser = 1;
        Integer selectedMonth = 5;
        when(clientsRepository.existsById(idUser)).thenReturn(false);
        clientsService.getDurationByMont(idUser,selectedMonth);
    }

    @Test
    public void LastCallDurtationOK() {
        Integer idClient = 1;
        UserCityLastCallDuration lc = null;
        when(clientsRepository.existsById(idClient)).thenReturn(true);
        when(clientsRepository.getLastCallDuration(idClient)).thenReturn(lc);
        clientsRepository.existsById(idClient);
        clientsRepository.getLastCallDuration(idClient);
    }

	@Test(expected = UserNotExist.class)
    public void LastCallDurationFail() throws UserNotExist{
        Integer idUser = 1;
        when(clientsRepository.existsById(idUser)).thenReturn(false);
        clientsService.getLastCallDuration(idUser);
	}

}

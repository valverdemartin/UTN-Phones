package edu.utn.TpFinal.Service;

import edu.utn.TpFinal.repository.ClientsRepository;
import edu.utn.TpFinal.repository.LinesRespository;
import edu.utn.TpFinal.service.ClientsService;
import org.junit.Before;

import static org.mockito.Mockito.mock;

public class ClientsServiceTest {


    ClientsRepository clientsRepository;
    ClientsService clientsService;
    LinesRespository linesRespository;

    @Before
    public void onSetUp(){
        clientsRepository= mock(ClientsRepository.class);
        clientsService = new ClientsService(clientsRepository, linesRespository);
    }

    /*@Test
    public void DurationByMonthOk() throws UserNotExists {
        Integer idUser = 1;
        Integer selectedMonth = 5;
        DurationByMonth durationByMonth = null;
        when(clientsRepository.existsById(idUser)).thenReturn(true);
        when(clientsRepository.getDurationByMont(idUser, selectedMonth)).thenReturn(durationByMonth);
        clientsService.getDurationByMont(idUser,selectedMonth);
    }

    @Test(expected = UserNotExists.class)
    public void DurationByMonthFail() throws UserNotExists {
        Integer idUser = 1;
        Integer selectedMonth = 5;
        when(clientsRepository.existsById(idUser)).thenReturn(false);
        clientsService.getDurationByMont(idUser,selectedMonth);
    }*/

}

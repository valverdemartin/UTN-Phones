package edu.utn.TpFinal.service;
import edu.utn.TpFinal.model.Rates;
import edu.utn.TpFinal.repository.RatesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service

public class RatesService {

    private RatesRepository ratesRepository;

    @Autowired
    public RatesService(RatesRepository ratesRepository) {
        this.ratesRepository = ratesRepository;
    }

    public void addRate(final Rates rate){
        ratesRepository.save(rate);
    }

    public List<Rates> getRates(){
        return ratesRepository.findAll();
    }
}

package edu.utn.TpFinal.service;
import edu.utn.TpFinal.model.Bills;
import edu.utn.TpFinal.repository.BillsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service

public class BillsService {

    private BillsRepository billsRepository;

    @Autowired
    public BillsService(BillsRepository billsRepository) {
        this.billsRepository = billsRepository;
    }

    public void addBill(final Bills bill){
        billsRepository.save(bill);
    }

    public List<Bills> getBills(){
        return billsRepository.findAll();
    }
}
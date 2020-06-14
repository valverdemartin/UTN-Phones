package edu.utn.TpFinal.service;

import edu.utn.TpFinal.Exceptions.BillNotExists;
import edu.utn.TpFinal.model.Bills;
import edu.utn.TpFinal.repository.BillsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
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
        return billsRepository.findByActive(true);
    }

    @Scheduled()//https://docs.oracle.com/cd/E19226-01/820-7627/giqlg/index.html
                //https://www.baeldung.com/spring-email
    public void deleteBill(Integer id) {
        Bills bill = billsRepository.findById(id).orElseThrow(() -> new BillNotExists(HttpStatus.BAD_REQUEST));//existByid()
        bill.setActive(Boolean.FALSE);
        billsRepository.save(bill);
    }
}
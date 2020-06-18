package edu.utn.TpFinal.service;

import edu.utn.TpFinal.Exceptions.BillNotExists;
import edu.utn.TpFinal.model.Bills;
import edu.utn.TpFinal.repository.BillsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service

public class BillsService {

    private BillsRepository billsRepository;

    @Autowired
    public BillsService(BillsRepository billsRepository) {
        this.billsRepository = billsRepository;
    }

    public Page<Bills> getBills(Pageable pageable){
        return billsRepository.findByActiveTrue(pageable);
    }//Deberìa tener alguna exception?

    public void deleteBill(Integer id) throws BillNotExists {
        //Bills bill = billsRepository.findById(id).orElseThrow(() -> new BillNotExists(HttpStatus.BAD_REQUEST));//existByid()
        Bills bill = billsRepository.findById(id).orElseThrow(() -> new BillNotExists());
        bill.setActive(Boolean.FALSE);
        billsRepository.save(bill);
    }
}
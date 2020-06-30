package edu.utn.TpFinal.service;

import edu.utn.TpFinal.Exceptions.BillNotExists;
import edu.utn.TpFinal.Exceptions.ClientNotExists;
import edu.utn.TpFinal.Exceptions.LineNotExists;
import edu.utn.TpFinal.Projections.UserBills;
import edu.utn.TpFinal.model.Lines;
import edu.utn.TpFinal.repository.BillsRepository;
import edu.utn.TpFinal.repository.ClientsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service

public class BillsService {

    private LinesService linesService;
    private BillsRepository billsRepository;
    private ClientsRepository clientsRepository;

    @Autowired
    public BillsService(BillsRepository billsRepository, LinesService linesService, ClientsRepository clientsRepository) {
        this.billsRepository = billsRepository;
        this.clientsRepository = clientsRepository;
        this.linesService = linesService;
    }

    public Page<UserBills> getUserBills(Pageable pageable, Integer clientId, Integer lineId, Timestamp from, Timestamp to) throws BillNotExists, LineNotExists, ClientNotExists {
        linesService.verifyClientAndLine(clientId, lineId);
        Lines line = linesService.findById(lineId);
        Page<UserBills> b;
        if(from == null || to == null)
            b = billsRepository.findByLine(pageable, line);
        else
            b = billsRepository.findByBillDateBetweenAndLine(pageable, from, to, line);
        return b;
    }

     /*public UserBills getUserBillById(Integer clientId, Integer lineId, Integer billId) throws LineNotExists, ClientNotExists, BillNotExists {
        linesService.verifyClientAndLine(clientId, lineId);
        Lines line = verifyLineAndBill(lineId, billId);
        Bills bill = billsRepository.findById(billId).orElseThrow(()-> new BillNotExists());
        return billsRepository.findByIdAndActiveTrue(billId);
    }*/

     /*public void deleteBill(Integer id) throws BillNotExists {
        Bills bill = billsRepository.findById(id).orElseThrow(() -> new BillNotExists());
        bill.setActive(Boolean.FALSE);
        billsRepository.save(bill);
    }*/

   /*public Lines verifyLineAndBill(Integer lineId, Integer billId) throws LineNotExists, BillNotExists {
        Lines line = linesService.findById(lineId);
        if(!billsRepository.existsByIdAndLine(billId, line))
            throw new BillNotExists();
        return line;
    }*/
}
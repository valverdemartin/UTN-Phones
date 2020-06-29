package edu.utn.TpFinal.repository;

import edu.utn.TpFinal.Projections.UserBills;
import edu.utn.TpFinal.model.Bills;
import edu.utn.TpFinal.model.Lines;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.function.Supplier;

public interface BillsRepository extends JpaRepository <Bills, Integer>{
    Page<Bills> findByActiveTrue(Pageable pageable);

    Page<UserBills> findByBillDateBetweenAndLine(Pageable pageable, Timestamp from, Timestamp to, Lines line);

    boolean existsByIdAndLine(Integer billId, Lines byId);

    Page<UserBills> findByLine(Pageable pageable, Lines line);

    UserBills findByIdAndActiveTrue(Integer billId);
}
package edu.utn.TpFinal.repository;

import edu.utn.TpFinal.model.Bills;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillsRepository extends JpaRepository <Bills, Integer>{
    Page<Bills> findByActiveTrue(Pageable pageable);
}
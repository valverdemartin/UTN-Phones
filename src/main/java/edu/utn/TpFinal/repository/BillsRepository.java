package edu.utn.TpFinal.repository;
import edu.utn.TpFinal.model.Bills;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BillsRepository extends JpaRepository <Bills, Integer>{
    public List<Bills> findByActive(Boolean active);
}
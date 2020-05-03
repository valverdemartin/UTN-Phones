package edu.utn.TpFinal.repository;

import edu.utn.TpFinal.model.Employees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface EmployeesRepository extends JpaRepository<Employees, Integer> {
}

package edu.utn.TpFinal.repository;

import edu.utn.TpFinal.model.Cities;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CitiesRepository extends JpaRepository<Cities, Integer> {
}

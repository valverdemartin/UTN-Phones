package edu.utn.TpFinal.repository;

import edu.utn.TpFinal.model.Cities;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CitiesRepository extends JpaRepository<Cities, Integer> {

    Page<Cities> findByActive(Pageable pageable, Boolean active);

    boolean existsByPrefix(Integer prefix);

    boolean existsByName(String name);

    boolean existsByShortName(String shortName);
}

package edu.utn.TpFinal.repository;

import edu.utn.TpFinal.model.Provinces;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProvincesRepository extends JpaRepository<Provinces, Integer> {


    boolean existsByName(String name);

    Page<Provinces> findByCitiesId(Pageable pageable, int id);

    boolean existsByNameAndIdNot(String name, Integer id);
}

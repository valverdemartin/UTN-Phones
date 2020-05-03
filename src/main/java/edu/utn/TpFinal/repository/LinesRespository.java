package edu.utn.TpFinal.repository;

import edu.utn.TpFinal.model.Lines;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface LinesRespository extends JpaRepository<Lines, Integer> {
}

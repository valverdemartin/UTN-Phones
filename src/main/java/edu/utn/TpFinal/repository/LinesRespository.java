package edu.utn.TpFinal.repository;

import edu.utn.TpFinal.Projections.UserLine;
import edu.utn.TpFinal.model.Clients;
import edu.utn.TpFinal.model.Lines;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository

public interface LinesRespository extends JpaRepository<Lines, Integer> {

    @Query(value = "SELECT line_number as phoneNumber, line_type as type "+
                "FROM phone_lines " +
                "WHERE id = :lineId AND id_client = :clientId ;", nativeQuery = true)
    UserLine findByIDAndByClient(Integer lineId, Integer clientId);

    boolean existsByIdAndClient(Integer lineId, Clients client);
}

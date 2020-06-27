package edu.utn.TpFinal.repository;

import edu.utn.TpFinal.Projections.TopCalls;
import edu.utn.TpFinal.Projections.UserCalls;
import edu.utn.TpFinal.model.Calls;
import edu.utn.TpFinal.model.Lines;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public interface CallsRepository extends JpaRepository <Calls, Integer>{

    Page<UserCalls> findByCallDateBetweenAndOriginLine (Pageable pageable, Timestamp callDateFrom, Timestamp callDateTo, Lines originLine);

    @Query(value = "SELECT c.dest_number as destNumber, COUNT(c.dest_number) as count " +
            "FROM calls as c " +
            "WHERE c.id_origin_line = :idLine " +
            "GROUP BY c.dest_number " +
            "Order by COUNT(c.dest_number) DESC Limit 10;"
            , nativeQuery = true)
    List<TopCalls> getFavouritesCalls(@Param("idLine") Integer idLine);

    Boolean existsByOriginNumberAndCallDate(String originNumber, Timestamp callDate);

    Page<UserCalls> findByOriginLine(Pageable pageable, Lines line);
}

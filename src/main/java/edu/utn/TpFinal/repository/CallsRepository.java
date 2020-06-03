package edu.utn.TpFinal.repository;

import edu.utn.TpFinal.Projections.LastCall;
import edu.utn.TpFinal.model.Calls;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CallsRepository extends JpaRepository <Calls, Integer>{
    //@Query(value = "call sp(:origin,:destiny,:duration);", nativeQuery = true)
    //void insertNewCall(@Param("origin") String origin, @Param("destiny") String destiny, @Param("duration") Long duration);

    @Query(value = "SELECT * FROM calls as c " +
            "WHERE c.call_date  = " +
            "(SELECT MAX(c.call_date) " +
            "  FROM calls as c);"
            , nativeQuery = true)
    List<LastCall> getLastCalls();
}

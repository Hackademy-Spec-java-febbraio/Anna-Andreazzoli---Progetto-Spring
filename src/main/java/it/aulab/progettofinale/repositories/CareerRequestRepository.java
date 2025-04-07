package it.aulab.progettofinale.repositories;

import java.util.List;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import it.aulab.progettofinale.models.CareerRequest;


public interface CareerRequestRepository extends CrudRepository<CareerRequest, Long>{
    List<CareerRequest> findByIsCheckedFalse();

    @Query(value = "SELECT user_id FROM user_roles", nativeQuery = true)
    List<Long> findAllUsersIds();

    @Query(value = "SELECT role_id FROM user_roles WHERE user_id = :id", nativeQuery = true)
    List<Long> findByUserId(@Param("id") Long id);
}

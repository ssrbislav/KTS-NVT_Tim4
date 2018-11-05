package com.sbvtransport.sbvtransport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sbvtransport.sbvtransport.model.Passenger;

public interface PassengerRepository extends JpaRepository<Passenger,Long> {

}

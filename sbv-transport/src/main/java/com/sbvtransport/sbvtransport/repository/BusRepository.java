package com.sbvtransport.sbvtransport.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sbvtransport.sbvtransport.model.Bus;
import com.sbvtransport.sbvtransport.model.Transport;


public interface BusRepository extends JpaRepository<Bus, Long>{

  Bus save(Transport newBus);

}

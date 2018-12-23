package com.sbvtransport.sbvtransport.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sbvtransport.sbvtransport.model.Subway;
import com.sbvtransport.sbvtransport.model.Transport;


public interface SubwayRepository extends JpaRepository<Subway, Long>{
	
	Subway save(Transport newSubway);
	
}
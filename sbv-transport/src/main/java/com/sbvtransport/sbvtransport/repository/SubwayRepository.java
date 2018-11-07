package com.sbvtransport.sbvtransport.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sbvtransport.sbvtransport.model.Subway;


public interface SubwayRepository extends JpaRepository<Subway, Long>{
	
}
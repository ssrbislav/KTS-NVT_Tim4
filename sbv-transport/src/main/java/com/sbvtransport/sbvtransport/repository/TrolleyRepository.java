package com.sbvtransport.sbvtransport.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sbvtransport.sbvtransport.model.Transport;
import com.sbvtransport.sbvtransport.model.Trolley;

public interface TrolleyRepository extends JpaRepository<Trolley, Long>{
	
	Trolley save(Transport newTrolley);
	
}

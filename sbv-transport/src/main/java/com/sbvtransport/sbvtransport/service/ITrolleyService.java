package com.sbvtransport.sbvtransport.service;

import java.util.List;

import com.sbvtransport.sbvtransport.enumeration.TypeTransport;
import com.sbvtransport.sbvtransport.model.Trolley;

public interface ITrolleyService {
	
	List<Trolley> findAll();
	Trolley getOne(Long id);
	Trolley create(Trolley trolley);
	Trolley update(Trolley trolley);
	boolean delete(Long id);
	TypeTransport setType(TypeTransport type);

}

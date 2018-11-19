package com.sbvtransport.sbvtransport.service;

import java.util.List;

import com.sbvtransport.sbvtransport.model.Transport;

public interface ITransportService {
	
	  Transport getOne(Long id);
	  List<Transport> findAll();
	  Transport create(Transport transport);
	  Transport update(Transport transport);
	  boolean delete(Long id);	
}

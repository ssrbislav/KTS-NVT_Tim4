package com.sbvtransport.sbvtransport.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.sbvtransport.sbvtransport.model.Transport;
import com.sbvtransport.sbvtransport.repository.TransportRepository;

public class TransportService implements ITransportService {

	@Autowired
	TransportRepository transportRepository;

	@Override
	public Transport getOne(Long id) {
		return transportRepository.getOne(id);
	}

	@Override
	public List<Transport> findAll() {
		return transportRepository.findAll();
	}

	@Override
	public Transport create(Transport transport) {
		return transportRepository.save(transport);
	}

	@Override
	public Transport update(Transport transport) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(Long id) {
		// TODO Auto-generated method stub
		return false;
	}
	
}

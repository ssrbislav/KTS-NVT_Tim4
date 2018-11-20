package com.sbvtransport.sbvtransport.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbvtransport.sbvtransport.model.Transport;
import com.sbvtransport.sbvtransport.repository.TransportRepository;

@Service
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
		Optional<Transport> updatetransport = transportRepository.findById(transport.getId());
		updatetransport.get().setName(transport.getName());
		updatetransport.get().setLine(transport.getLine());
		updatetransport.get().setSpeed(transport.getSpeed());
		updatetransport.get().setLate(transport.isLate());
		
		return transportRepository.save(updatetransport.get());
	}

	@Override
	public boolean delete(Long id) {
		
		for(Transport transport : findAll())
			if(transport.getId() == id) {
				transportRepository.delete(transport);
				return true;
			}
		return false;
	}
	
}

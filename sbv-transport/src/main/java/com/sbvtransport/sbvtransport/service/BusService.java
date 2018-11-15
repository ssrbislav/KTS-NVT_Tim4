package com.sbvtransport.sbvtransport.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbvtransport.sbvtransport.dto.BusDTO;
import com.sbvtransport.sbvtransport.model.Bus;
import com.sbvtransport.sbvtransport.model.Line;
import com.sbvtransport.sbvtransport.repository.BusRepository;
import com.sbvtransport.sbvtransport.repository.LineRepository;

@Service
public class BusService implements IBusService {

	@Autowired
	BusRepository busRepository;

	@Override
	public List<Bus> findAll() {

		return busRepository.findAll();
	}

	@Override
	public Bus getOne(Long id) {

		return busRepository.getOne(id);
	}

	@Override
	public Bus create(BusDTO bus) {
		
		Bus newBus = new Bus(bus.getId(), bus.getCode(), bus.getSpeed(), bus.getId_line(), bus.isLate(), bus.getName());

		return busRepository.save(newBus);
	}

	@Override
	public Bus update(BusDTO bus) {

		Optional<Bus> updateBus = busRepository.findById(bus.getId());
		updateBus.get().setCode(bus.getCode());
		updateBus.get().setSpeed(bus.getSpeed());
		updateBus.get().setName(bus.getName());
		updateBus.get().setLate(bus.isLate());
		updateBus.get().setLine(bus.getId_line());

		return busRepository.save(updateBus.get());
	}

	@Override
	public boolean delete(Long id) {
		for (Bus bus : findAll())
			if (bus.getId() == id) {
				busRepository.delete(bus);
				return true;
			}
		return false;
	}

}

package com.sbvtransport.sbvtransport.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import com.sbvtransport.sbvtransport.model.Bus;
import com.sbvtransport.sbvtransport.repository.BusRepository;

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
	public Bus create(Bus bus) {

		return busRepository.save(bus);
	}

	@Override
	public Bus update(Bus bus) {

		for (Bus bu : findAll()) {
			if(bu.getName().equals(bus.getName()) && bu.getCode().equals(bus.getCode())){
				return null;
			}
		}
		Optional<Bus> updateBus = busRepository.findById(bus.getId());
		updateBus.get().setCode(bus.getCode());
		updateBus.get().setSpeed(bus.getSpeed());
		updateBus.get().setName(bus.getName());
		updateBus.get().setLate(bus.isLate());
		updateBus.get().setLine(bus.getLine());

		return busRepository.save(bus);
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

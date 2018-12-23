package com.sbvtransport.sbvtransport.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbvtransport.sbvtransport.dto.BusDTO;
import com.sbvtransport.sbvtransport.enumeration.TypeTransport;
import com.sbvtransport.sbvtransport.model.Bus;
import com.sbvtransport.sbvtransport.model.Line;
import com.sbvtransport.sbvtransport.model.Transport;
import com.sbvtransport.sbvtransport.repository.BusRepository;

@Service
public class BusService implements IBusService {

	@Autowired
	BusRepository busRepository;

	@Autowired
	LineService lineService;

	@Override
	public List<Bus> findAll() {

		return busRepository.findAll();
	}

	@Override
	public Bus getOne(Long id) {

		return busRepository.getOne(id);
	}

	@Override
	public String create(BusDTO bus) {

		Line line = checkLine(bus.getId_line());
		if (line != null) {
			String code = "";
			Transport newBus = new Bus(code, line, bus.isLate(), bus.getName());
			code = line.getName() + "_"+ "bus" + "_" + bus.getName() ;
			((Bus) newBus).setCode(code);
			busRepository.save(newBus);
			return "Bus successfully created!";
		}
		return "Bus has not been created. Some error. God knows what went wrong";
	}

	@Override
	public Bus update(Bus bus) {

		Optional<Bus> updateBus = busRepository.findById(bus.getId());
		updateBus.get().setCode(bus.getCode());
		updateBus.get().setName(bus.getName());
		updateBus.get().setLate(bus.isLate());
		updateBus.get().setLine(bus.getLine());
		updateBus.get().setTimetable(bus.getTimetable());

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

	@Override
	public boolean codeExist(String code) {
		for (Bus bus : findAll()) {
			if (bus.getCode().equals(code)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Line checkLine(Long lineId) {
		for (Line line : lineService.findAll())
			if (line.getId() == lineService.getOne(lineId).getId())
				if (line.getLine_type() == TypeTransport.bus)
					return line;
		return null;
	}

}

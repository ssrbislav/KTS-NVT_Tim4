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

		return busRepository.findById(id).orElse(null);
	}

	@Override
	public Bus create(BusDTO bus) {

		Line line = checkLine(bus.getId_line());
		if (line != null) {
			String code = "";
			Transport newBus = new Bus(code, line, bus.isLate(), bus.getName());
			code = line.getName() + "_"+ "bus" + "_" + bus.getName() ;
			((Bus) newBus).setCode(code);
			
			return busRepository.save(newBus);
		}
		return null;
	}

	@Override
	public Bus update(Bus bus) {
		
		Line line = checkLine(bus.getLine().getId());
		
		if(line != null){
			Optional<Bus> updateBus = busRepository.findById(bus.getId());
			updateBus.get().setCode(bus.getCode());
			updateBus.get().setName(bus.getName());
			updateBus.get().setLate(bus.isLate());
			updateBus.get().setLine(bus.getLine());
			//updateBus.get().setTimetable(bus.getTimetable());

			return busRepository.save(updateBus.get());
		}
		return null;
		
		
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
		
		Line line = lineService.getOne(lineId);
		if( line != null){
			if(line.getLine_type() == TypeTransport.bus){
				return line;
			}
		}
		return null;
	}

}

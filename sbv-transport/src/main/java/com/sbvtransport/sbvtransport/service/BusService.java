package com.sbvtransport.sbvtransport.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sbvtransport.sbvtransport.dto.AddLocationDTO;
import com.sbvtransport.sbvtransport.dto.BusDTO;
import com.sbvtransport.sbvtransport.dto.ChangeTransportDTO;
import com.sbvtransport.sbvtransport.enumeration.TypeTransport;
import com.sbvtransport.sbvtransport.model.Bus;
import com.sbvtransport.sbvtransport.model.Line;
import com.sbvtransport.sbvtransport.model.Location;
import com.sbvtransport.sbvtransport.model.Transport;
import com.sbvtransport.sbvtransport.repository.BusRepository;
import com.sbvtransport.sbvtransport.repository.LocationRepository;

@Service
public class BusService implements IBusService {

	@Autowired
	BusRepository busRepository;

	@Autowired
	LineService lineService;

	@Autowired
	LocationRepository locationRepository;

	@Override
	public List<Bus> findAll() {
		List<Bus> notDeleted = new ArrayList<>();
		List<Bus> findAll = busRepository.findAll();
		for (Bus bus : findAll) {
			if (!bus.isDeleted()) {
				notDeleted.add(bus);
			}
		}
		return notDeleted;
	}

	@Override
	public Bus getOne(Long id) {

		return busRepository.findById(id).orElse(null);
	}

	@Override
	public Bus create(BusDTO bus) {

		Line line = checkLine(bus.getId_line());
		if (line != null) {
			if (bus.getTime_arrive() < 5) {
				return null;
			}
			boolean late = checkIfLate(bus.getTime_arrive());
			String code = "";
			Transport newBus = new Bus(code, line, late, bus.getName(), bus.getTime_arrive(), false);
			code = line.getName() + "_" + "bus" + "_" + bus.getName();
			((Bus) newBus).setCode(code);
			return busRepository.save(newBus);
		}
		return null;
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
				bus.setDeleted(true);
				busRepository.save(bus);
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
		if (line != null) {
			if (!line.isDeleted()) {
				if (line.getLine_type() == TypeTransport.bus) {
					return line;
				}
			}

		}
		return null;
	}

	@Override
	public Bus addLocation(AddLocationDTO addLocation) {

		Location l = locationRepository.findById(addLocation.getId_location()).orElse(null);
		if (l == null || l.isDeleted()) {
			return null;
		}

		Bus b = getOne(addLocation.getId_transport());
		if (b == null || b.isDeleted()) {
			return null;
		}
		b.setLocation(l);
		return busRepository.save(b);
	}

	@Override
	public boolean checkIfLate(int time) {

		if (time > 5) {
			return true;
		} else if (time == 5) {
			return false;
		}
		return true;
	}

	@Override
	public void deleteBecauseLine(Long id_line) {
		List<Bus> buses = findAll();
		for (Bus bus : buses) {
			if (bus.getLine().getId() == id_line) {
				bus.setDeleted(true);
				busRepository.save(bus);
			}
		}
	}

	@Override
	public Bus change(ChangeTransportDTO bus) {

		Optional<Bus> updateBus = busRepository.findById(bus.getId_transport());

		updateBus.get().setName(bus.getName());
		if (bus.getTime_arrive() > 5) {
			updateBus.get().setLate(true);

		} else {
			updateBus.get().setLate(false);
		}
		updateBus.get().setTime_arrive(bus.getTime_arrive());
		updateBus.get().setTimetable(bus.getTimetable());
		updateBus.get().setLocation(bus.getCurrent_location());

		return busRepository.save(updateBus.get());

	}

}

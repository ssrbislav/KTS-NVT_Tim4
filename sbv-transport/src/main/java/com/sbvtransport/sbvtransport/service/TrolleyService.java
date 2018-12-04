package com.sbvtransport.sbvtransport.service;

import com.sbvtransport.sbvtransport.model.Line;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sbvtransport.sbvtransport.dto.TrolleyDTO;
import com.sbvtransport.sbvtransport.enumeration.TypeTransport;
import com.sbvtransport.sbvtransport.model.Trolley;
import com.sbvtransport.sbvtransport.repository.TrolleyRepository;

@Service
public class TrolleyService implements ITrolleyService {

	@Autowired
	TrolleyRepository trolleyRepository;
	@Autowired
	LineService lineService;

	@Override
	public List<Trolley> findAll() {

		return trolleyRepository.findAll();
	}

	@Override
	public Trolley getOne(Long id) {

		return trolleyRepository.getOne(id);
	}

	@Override
	public String create(TrolleyDTO trolley) {

		Line line = checkLine(trolley.getId_line());
		if (line != null) {
			String code = "";
			Trolley newTrolley = new Trolley(code, line, trolley.isLate(), trolley.getName());
			code = line.getName() + ":" + (trolleyRepository.findAll().size() + 1) + ":" + "bus";
			newTrolley.setCode(code);
			trolleyRepository.save(newTrolley);
			return "Trolley successfully created!";
		}
		return "Trolley has not been created. Some error. God knows what went wrong";
	}

	@Override
	public Trolley update(Trolley trolley) {

		Optional<Trolley> updateTrolley = trolleyRepository.findById(trolley.getId());
		updateTrolley.get().setCode(trolley.getCode());
		updateTrolley.get().setName(trolley.getName());
		updateTrolley.get().setLate(trolley.isLate());
		updateTrolley.get().setLine(trolley.getLine());
		updateTrolley.get().setTimetable(trolley.getTimetable());
		return trolleyRepository.save(updateTrolley.get());
	}

	@Override
	public boolean delete(Long id) {

		for (Trolley trolley : findAll())
			if (trolley.getId() == id) {
				trolleyRepository.delete(trolley);
				return true;
			}
		return false;
	}

	@Override
	public boolean codeExist(String code) {
		for (Trolley trolley : findAll()) {
			if (trolley.getCode().equals(code)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Line checkLine(Long lineId) {
		for (Line line : lineService.findAll())
			if (line.getId() == lineService.getOne(lineId).getId())
				if (line.getLine_type() == TypeTransport.trolley)
					return line;
		return null;
	}

}

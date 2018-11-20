package com.sbvtransport.sbvtransport.service;

import com.sbvtransport.sbvtransport.model.Line;
import com.sbvtransport.sbvtransport.model.Timetable;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sbvtransport.sbvtransport.dto.TrolleyDTO;
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
	public Trolley create(TrolleyDTO trolley) {

		Line line = lineService.getOne(trolley.getId_line());
		String code = "";
		Trolley newTrolley = new Trolley(code, line, trolley.isLate(), trolley.getName());
		code = line.getName() + ":" + newTrolley.getId() + ":" + "bus";
		newTrolley.setCode(code);
		return trolleyRepository.save(newTrolley);
	}

	@Override
	public Trolley update(Trolley trolley) {
		
		Optional<Trolley> updateTrolley = trolleyRepository.findById(trolley.getId());
		updateTrolley.get().setCode(trolley.getCode());
		updateTrolley.get().setName(trolley.getName());
		updateTrolley.get().setLate(trolley.isLate());
		updateTrolley.get().setLine(trolley.getLine());
//		updateTrolley.get().setTimetable(trolley.getTimetable());
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
			if(trolley.getCode().equals(code)){
				return true;
			}
		}
		return false;
	}

}

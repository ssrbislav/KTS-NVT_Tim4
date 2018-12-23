package com.sbvtransport.sbvtransport.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbvtransport.sbvtransport.dto.SubwayDTO;
import com.sbvtransport.sbvtransport.enumeration.TypeTransport;
import com.sbvtransport.sbvtransport.model.Line;
import com.sbvtransport.sbvtransport.model.Subway;
import com.sbvtransport.sbvtransport.model.Transport;
import com.sbvtransport.sbvtransport.repository.SubwayRepository;

@Service
public class SubwayService implements ISubwayService {

	@Autowired
	SubwayRepository subwayRepository;
	
	@Autowired
	LineService lineService;

	@Override
	public List<Subway> findAll() {
		return subwayRepository.findAll();
	}

	@Override
	public Subway getOne(Long id) {

		return subwayRepository.getOne(id);
	}

	@Override
	public String create(SubwayDTO subway) {
		
		Line line = checkLine(subway.getId_line());
		if (line != null) {
			String code = "";
			Transport newSubway = new Subway(code, line, subway.isLate(), subway.getName());
			code = line.getName() + "_"+ "subway" + "_" + subway.getName();
			((Subway) newSubway).setCode(code);
			subwayRepository.save(newSubway);
			return "Subway successfully created!";
		}
		return "Subway has not been created. Some error. God knows what went wrong";
	}

	@Override
	public Subway update(Subway subway) {

		Optional<Subway> updateSubway = subwayRepository.findById(subway.getId());
		updateSubway.get().setCode(subway.getCode());
		updateSubway.get().setName(subway.getName());
		updateSubway.get().setLate(subway.isLate());
		updateSubway.get().setLine(subway.getLine());
		updateSubway.get().setTimetable(subway.getTimetable());
		return subwayRepository.save(updateSubway.get());

	}

	@Override
	public boolean delete(Long id) {

		for (Subway subway : findAll())
			if (subway.getId() == id) {
				subwayRepository.delete(subway);
				return true;
			}
		return false;
	}

	@Override
	public boolean codeExist(String code) {
		for (Subway subway : findAll()) {
			if (subway.getCode().equals(code)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Line checkLine(Long lineId) {
		for (Line line : lineService.findAll())
			if (line.getId() == lineService.getOne(lineId).getId())
				if (line.getLine_type() == TypeTransport.subway)
					return line;
		return null;
	}

}

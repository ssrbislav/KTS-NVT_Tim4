package com.sbvtransport.sbvtransport.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbvtransport.sbvtransport.dto.AddLocationDTO;
import com.sbvtransport.sbvtransport.dto.SubwayDTO;
import com.sbvtransport.sbvtransport.enumeration.TypeTransport;
import com.sbvtransport.sbvtransport.model.Line;
import com.sbvtransport.sbvtransport.model.Location;
import com.sbvtransport.sbvtransport.model.Subway;
import com.sbvtransport.sbvtransport.model.Transport;
import com.sbvtransport.sbvtransport.repository.LineRepository;
import com.sbvtransport.sbvtransport.repository.LocationRepository;
import com.sbvtransport.sbvtransport.repository.SubwayRepository;

@Service
public class SubwayService implements ISubwayService {

	@Autowired
	SubwayRepository subwayRepository;
	
	@Autowired
	LineRepository lineRepository;
	
	@Autowired
	LocationRepository locationRepository;

	@Override
	public List<Subway> findAll() {
		
		return subwayRepository.findAll();
	}

	@Override
	public Subway getOne(Long id) {

		return subwayRepository.findById(id).orElse(null);
	}

	@Override
	public Subway create(SubwayDTO subway) {
		
		Line line = checkLine(subway.getId_line());
		if (line != null) {
			if(subway.getTime_arrive()< 5){
				return null;
			}
			boolean late = checkIfLate(subway.getTime_arrive());
			String code = "";
			Transport newSubway = new Subway(code, line, late, subway.getName(),subway.getTime_arrive());
			code = line.getName() + "_"+ "subway" + "_" + subway.getName();
			((Subway) newSubway).setCode(code);
			
			return subwayRepository.save(newSubway);
		}
		return null;
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
		
		Line line = lineRepository.findById(lineId).get();
		if( line != null){
			if(line.getLine_type() == TypeTransport.subway){
				return line;
			}
		}
		return null;
	}

	@Override
	public Subway addLocation(AddLocationDTO addLocation) {
		
		Location l = locationRepository.findById(addLocation.getId_location()).orElse(null);
		if(l==null){
			return null;
		}
		
		Subway s = subwayRepository.getOne(addLocation.getId_transport());
		if(s==null){
			return null;
		}
		s.setLocation(l);
		return subwayRepository.save(s);
	}

	@Override
	public boolean checkIfLate(int time) {
		
		if(time > 5){
			return true;
		}else if (time == 5) {
			return false;
		}
		return true;
	}

}

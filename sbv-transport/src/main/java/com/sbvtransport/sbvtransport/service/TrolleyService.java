package com.sbvtransport.sbvtransport.service;

import com.sbvtransport.sbvtransport.model.Line;
import com.sbvtransport.sbvtransport.model.Location;
import com.sbvtransport.sbvtransport.model.Transport;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sbvtransport.sbvtransport.dto.AddLocationDTO;
import com.sbvtransport.sbvtransport.dto.TrolleyDTO;
import com.sbvtransport.sbvtransport.enumeration.TypeTransport;
import com.sbvtransport.sbvtransport.model.Trolley;
import com.sbvtransport.sbvtransport.repository.LineRepository;
import com.sbvtransport.sbvtransport.repository.LocationRepository;
import com.sbvtransport.sbvtransport.repository.TrolleyRepository;

@Service
public class TrolleyService implements ITrolleyService {

	@Autowired
	TrolleyRepository trolleyRepository;
	
	@Autowired
	LineRepository lineRepository;
	
	@Autowired
	LocationRepository locationRepository;

	@Override
	public List<Trolley> findAll() {
		List <Trolley> notDeleted = new ArrayList<>();
		List<Trolley> findAll = trolleyRepository.findAll();
		for (Trolley trolley : findAll) {
			if(!trolley.isDeleted()){
				notDeleted.add(trolley);
			}
		}

		return notDeleted;
	}

	@Override
	public Trolley getOne(Long id) {

		return trolleyRepository.findById(id).orElse(null);
	}

	@Override
	public Trolley create(TrolleyDTO trolley) {

		Line line = checkLine(trolley.getId_line());
		if (line != null) {
			if(trolley.getTime_arrive()< 5){
				return null;
			}
			boolean late = checkIfLate(trolley.getTime_arrive());
			String code = "";
			Transport  newTrolley = new Trolley(code, line, late, trolley.getName(),trolley.getTime_arrive(),false);
			code = line.getName() + "_"+ "trolley" + "_" + trolley.getName();
			((Trolley) newTrolley).setCode(code);
			
			return trolleyRepository.save(newTrolley);
		}
		return null;
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
				trolley.setDeleted(true);
				trolleyRepository.save(trolley);
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
		
		Line line = lineRepository.findById(lineId).get();
		if( line != null){
			if(!line.isDeleted()){
				if(line.getLine_type() == TypeTransport.trolley){
					return line;
				}
			}	
		}
		return null;
	}

	@Override
	public Trolley addLocation(AddLocationDTO addLocation) {
		
		Location l = locationRepository.findById(addLocation.getId_location()).orElse(null);
		if(l==null || l.isDeleted()){
			return null;
		}
		
		Trolley t = getOne(addLocation.getId_transport());
		if(t==null || t.isDeleted()){
			return null;
		}
		t.setLocation(l);
		
		return trolleyRepository.save(t);
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
	@Override
	public void deleteBecauseLine(Long id_line){
		List<Trolley> trolleys = findAll();
		for (Trolley trolley :trolleys) {
			if(trolley.getLine().getId() == id_line){
				trolley.setDeleted(true);
				trolleyRepository.save(trolley);
			}
		}
	}


}

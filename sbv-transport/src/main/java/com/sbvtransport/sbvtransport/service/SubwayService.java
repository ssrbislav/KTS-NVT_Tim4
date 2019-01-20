package com.sbvtransport.sbvtransport.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sbvtransport.sbvtransport.dto.AddLocationDTO;
import com.sbvtransport.sbvtransport.dto.ChangeTransportDTO;
import com.sbvtransport.sbvtransport.dto.FilterSearchTransportDTO;
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
		List <Subway> notDeleted = new ArrayList<>();
		List<Subway> findAll = subwayRepository.findAll();
		for (Subway subway : findAll) {
			if(!subway.isDeleted()){
				notDeleted.add(subway);
			}
		}
		return notDeleted;
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
			Transport newSubway = new Subway(code, line, late, subway.getName(),subway.getTime_arrive(),false);
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
				subway.setDeleted(true);
				subwayRepository.save(subway);
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
			if(!line.isDeleted()){
				if(line.getLine_type() == TypeTransport.subway){
					return line;
				}
			}
		}
		return null;
	}

	@Override
	public Subway addLocation(AddLocationDTO addLocation) {
		
		Location l = locationRepository.findById(addLocation.getId_location()).orElse(null);
		if(l==null || l.isDeleted()){
			return null;
		}
		
		Subway s = getOne(addLocation.getId_transport());
		if(s==null || s.isDeleted()){
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
	@Override
	public void deleteBecauseLine(Long id_line){
		List<Subway> subways = findAll();
		for (Subway subway : subways) {
			if(subway.getLine().getId() == id_line){
				subway.setDeleted(true);
				subwayRepository.save(subway);
			}
		}
	}

	@Override
	public Subway change(ChangeTransportDTO subway) {
		
		Optional<Subway> updateSubway = subwayRepository.findById(subway.getId_transport());

		updateSubway.get().setName(subway.getName());
		if (subway.getTime_arrive() > 5) {
			updateSubway.get().setLate(true);

		} else {
			updateSubway.get().setLate(false);
		}
		updateSubway.get().setTime_arrive(subway.getTime_arrive());
		updateSubway.get().setTimetable(subway.getTimetable());
		updateSubway.get().setLocation(subway.getCurrent_location());

		return subwayRepository.save(updateSubway.get());
	}

	@Override
	public List<Subway> searchFilter(FilterSearchTransportDTO filterSearch) {
		
		List<Subway> allSubways = findAll();
		List<Subway> lineFilter = new ArrayList<>();
		List<Subway> lateFilter = new ArrayList<>();
		List<Subway> currentLocationFilter = new ArrayList<>();
		List<Subway> finalList = new ArrayList<>();
		
		//filter line 
		if(filterSearch.getId_line() != null){
			for (Subway subway : allSubways) {
				if(filterSearch.getId_line() == subway.getLine().getId()){
					lineFilter.add(subway);
				}
			}
			
		}else{
			lineFilter = allSubways;
		}
		
		// filter is late
		if(filterSearch.isLate()){
			for (Subway subway : lineFilter) {
				if(subway.isLate()){
					lateFilter.add(subway);
				}
			}
			
		}else{
			lateFilter = lineFilter;	
		}
		
		//filter current location
		if(filterSearch.getId_location() != null){
			for (Subway subway :lateFilter) {
				if(subway.getLocation() != null){
					if(subway.getLocation().getId() == filterSearch.getId_location()){
						currentLocationFilter.add(subway);
					}
				}
				
			}
			
		}else{
			currentLocationFilter = lateFilter;
		}
		//search by names
		if(filterSearch.getText_search() != ""){
			for (Subway subway : currentLocationFilter) {
				if(subway.getName().contains(filterSearch.getText_search())){
					finalList.add(subway);
				}
			}
			
		}else{
			finalList = currentLocationFilter;
		}
		
		return finalList;
	}

	


}

package com.sbvtransport.sbvtransport.service;

import com.sbvtransport.sbvtransport.dto.AddFirstStationDTO;
import com.sbvtransport.sbvtransport.dto.StationDTO;
import com.sbvtransport.sbvtransport.enumeration.Zone;
import com.sbvtransport.sbvtransport.model.Line;
import com.sbvtransport.sbvtransport.model.Location;
import com.sbvtransport.sbvtransport.model.Station;
import com.sbvtransport.sbvtransport.repository.StationRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StationService implements IStationService {

	@Autowired
	StationRepository stationRepository;

	@Autowired
	LineService lineService;

	@Autowired
	LocationService locationService;

	@Override
	public Station getOne(Long id) {
		return stationRepository.getOne(id);
	}

	@Override
	public List<Station> findAll() {
		return stationRepository.findAll();
	}

	@Override
	public String create(StationDTO stationDTO) {
		Location location = new Location();
		Station station = new Station();

		if (!locationService.findAll().contains(locationService.getOne(stationDTO.getLocation_id()))) {
			return "Location with ID " + stationDTO.getLocation_id() + " not found!";
		} else {
			location = locationService.getOne(stationDTO.getLocation_id());
		}
		station.setLocation(location);
		station.setZone(Zone.valueOf(stationDTO.getZone()));
		
		stationRepository.save(station);

		return "The station has been successfully created.";
	}

	@Override
	public Station update(Station station) {
		Optional<Station> updateStation = stationRepository.findById(station.getId());
		updateStation.get().setLocation(station.getLocation());

		return stationRepository.save(updateStation.get());
	}

	@Override
	public boolean delete(Long id) {
		if (stationRepository.findAll().contains(stationRepository.getOne(id))) {
			stationRepository.delete(stationRepository.getOne(id));
			return true;
		}
		return false;
	}

	@Override
	public String addFirstStation(AddFirstStationDTO addFirst) {
		
		Line l = lineService.getOne(addFirst.getId_line());
		
		if(l == null){
			return "The line doesn't exist";
		}
		Station s = stationRepository.getOne(addFirst.getId_station());
		if(s== null){
			return "The station doesn't exist";

		}
		s.setLine_first_station(l);
		stationRepository.save(s);
		
		return "First station succesfully changed";
	}
}

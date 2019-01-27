package com.sbvtransport.sbvtransport.service;

import com.sbvtransport.sbvtransport.dto.LocationDTO;
import com.sbvtransport.sbvtransport.model.Location;
import com.sbvtransport.sbvtransport.repository.LocationRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationService implements ILocationService {

	@Autowired
	LocationRepository locationRepository;

	@Autowired
	StationService stationService;

	@Override
	public Location getOne(Long id) {
		return locationRepository.findById(id).orElse(null);
	}

	@Override
	public List<Location> findAll() {
		List <Location> notDeleted = new ArrayList<>();
		List<Location> findAll = locationRepository.findAll();
		for (Location location : findAll) {
			if(!location.isDeleted()){
				notDeleted.add(location);
			}
		}
		return notDeleted;
	}

	@Override
	public Location create(LocationDTO locationDTO) {
		Location location = new Location();
		location.setType(locationDTO.getType());
		location.setLongitude(locationDTO.getLongitude());
		location.setLocation_name(locationDTO.getLocation_name());
		location.setLatitude(locationDTO.getLatitude());
		location.setAddress(locationDTO.getAddress());
		location.setDeleted(false);
//		locationRepository.save(location);
		return locationRepository.save(location);
	}

	@Override
	public Location update(Location location) {
		Optional<Location> updateLocation = locationRepository.findById(location.getId());
		updateLocation.get().setAddress(location.getAddress());
		updateLocation.get().setLatitude(location.getLatitude());
		updateLocation.get().setLocation_name(location.getLocation_name());
		updateLocation.get().setLongitude(location.getLongitude());
		updateLocation.get().setType(location.getType());
		updateLocation.get().setStation(location.getStation());
		return locationRepository.save(location);
	}

	@Override
	public boolean delete(Long id) {
		for (Location l : findAll()) {
			if (l.getId() == id) {
				l.setDeleted(true);
				locationRepository.save(l);
				return true;
			}
		}
		return false;
	}
}

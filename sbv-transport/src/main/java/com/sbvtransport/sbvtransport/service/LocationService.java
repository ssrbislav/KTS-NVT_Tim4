package com.sbvtransport.sbvtransport.service;

import com.sbvtransport.sbvtransport.dto.LocationDTO;
import com.sbvtransport.sbvtransport.model.Location;
import com.sbvtransport.sbvtransport.repository.LocationRepository;
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
    return locationRepository.getOne(id);
  }

  @Override
  public List<Location> findAll() {
    return locationRepository.findAll();
  }

  @Override
  public String create(LocationDTO locationDTO) {
    Location location = new Location();
    location.setType(locationDTO.getType());
    location.setLongitude(locationDTO.getLongitude());
    location.setLocation_name(locationDTO.getLocation_name());
    location.setLatitude(locationDTO.getLatitude());
    location.setAddress(locationDTO.getAddress());
//    if (!stationService.findAll().contains(stationService.getOne(locationDTO.getStation_id()))) {
//      return "No station with ID " + locationDTO.getStation_id() + " exists!";
//    } else {
//      location.setStation(stationService.getOne(locationDTO.getStation_id()));
//    }
    locationRepository.save(location);
    return "Location has been successfully created!";
  }

  @Override
  public Location update(Location location) {
    Optional<Location> updateLocation = locationRepository.findById(location.getId());
    updateLocation.get().setAddress(location.getAddress());
    updateLocation.get().setLatitude(location.getLatitude());
    updateLocation.get().setLocation_name(location.getLocation_name());
    updateLocation.get().setLongitude(location.getLongitude());
    updateLocation.get().setType(location.getType());
    return locationRepository.save(location);
  }

  @Override
  public boolean delete(Long id) {
    if (locationRepository.findAll().contains(locationRepository.getOne(id))) {
      locationRepository.delete(locationRepository.getOne(id));
      return true;
    }
    return false;
  }
}

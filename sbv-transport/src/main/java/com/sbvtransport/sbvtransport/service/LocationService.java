package com.sbvtransport.sbvtransport.service;

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


  @Override
  public Location getOne(Long id) {
    return locationRepository.getOne(id);
  }

  @Override
  public List<Location> findAll() {
    return locationRepository.findAll();
  }

  @Override
  public Location create(Location location) {
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

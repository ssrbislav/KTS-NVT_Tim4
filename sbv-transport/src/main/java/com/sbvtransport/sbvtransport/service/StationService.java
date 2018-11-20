package com.sbvtransport.sbvtransport.service;

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


  @Override
  public Station getOne(Long id) {
    return stationRepository.getOne(id);
  }

  @Override
  public List<Station> findAll() {
    return stationRepository.findAll();
  }

  @Override
  public Station create(Station station) {
    return stationRepository.save(station);
  }

  @Override
  public Station update(Station station) {
    Optional<Station> updateStation = stationRepository.findById(station.getId());
    updateStation.get().setLocation(station.getLocation());
    updateStation.get().setTimetable(station.getTimetable());
    updateStation.get().setLine(station.getLine());
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
}

package com.sbvtransport.sbvtransport.service;

import com.sbvtransport.sbvtransport.dto.StationDTO;
import com.sbvtransport.sbvtransport.model.Line;
import com.sbvtransport.sbvtransport.model.Location;
import com.sbvtransport.sbvtransport.model.Station;
import com.sbvtransport.sbvtransport.repository.StationRepository;
import java.util.Date;
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
    Line line = new Line();
    Location location = new Location();
    Station station = new Station();
    if (!lineService.findAll().contains(lineService.getOne(stationDTO.getLine_id()))) {
      return "Line with ID " + stationDTO.getLine_id() + " not found!";
    } else {
      line = lineService.getOne(stationDTO.getLine_id());
    }
    if (!locationService.findAll().contains(locationService.getOne(stationDTO.getLocation_id()))) {
      return "Line with ID " + stationDTO.getLine_id() + " not found!";
    } else {
      location = locationService.getOne(stationDTO.getLocation_id());
    }
    station.setLocation(location);
    station.setLine(line);
    stationRepository.save(station);
    location.setStation(station);
    locationService.update(location);
    return "The station has been successfully created.";
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

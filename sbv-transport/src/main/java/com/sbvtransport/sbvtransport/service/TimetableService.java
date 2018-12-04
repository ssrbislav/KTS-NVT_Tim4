package com.sbvtransport.sbvtransport.service;

import com.sbvtransport.sbvtransport.dto.TimetableDTO;
import com.sbvtransport.sbvtransport.model.Bus;
import com.sbvtransport.sbvtransport.model.Station;
import com.sbvtransport.sbvtransport.model.Subway;
import com.sbvtransport.sbvtransport.model.Timetable;
import com.sbvtransport.sbvtransport.model.Transport;
import com.sbvtransport.sbvtransport.model.Trolley;
import com.sbvtransport.sbvtransport.repository.StationRepository;
import com.sbvtransport.sbvtransport.repository.TimetableRepository;
import java.util.Date;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TimetableService implements ITimetableService {

  @Autowired
  TimetableRepository timetableRepository;
  @Autowired
  StationService stationService;
  @Autowired
  BusService busService;
  @Autowired
  SubwayService subwayService;
  @Autowired
  TrolleyService trolleyService;

  @Override
  public Timetable getOne(Long id) { return timetableRepository.getOne(id); }

  @Override
  public List<Timetable> findAll() {
    return timetableRepository.findAll();
  }

  @Override
  public String create(TimetableDTO timetableDTO) {
    Timetable timetable = new Timetable();
    Station station = new Station();
    Transport transport;
    Bus bus = new Bus();
    Subway subway = new Subway();
    Trolley trolley = new Trolley();
    HashMap<Station, Date> schedule = new HashMap<>();


    timetable.setCode(timetableDTO.getTransportCode());
    for (Long stationID : timetableDTO.getTimetable().keySet()) {
      if (!stationService.findAll().contains(stationService.getOne(stationID))) {
        return "Station with ID " + stationID + " doesn't exist!";
      } else {
        station = stationService.getOne(stationID);

        schedule.put(station, timetableDTO.getTimetable().get(station.getId()));
      }
    }
    timetable.setSchedule(schedule);
    Timetable tmtbl = timetableRepository.save(timetable);


    if (busService.codeExist(timetableDTO.getTransportCode())) {
      for (Bus b : busService.findAll()) {
        if (b.getCode().equals(timetableDTO.getTransportCode())) {
          bus = b;
          bus.setTimetable(tmtbl);
          busService.update(bus);
        }
      }
    } else if (subwayService.codeExist(timetableDTO.getTransportCode())) {
      for (Subway s : subwayService.findAll()) {
        if (s.getCode().equals(timetableDTO.getTransportCode())) {
          subway = s;
          subway.setTimetable(tmtbl);
          subwayService.update(subway);
        }
      }
    } else if (trolleyService.codeExist(timetableDTO.getTransportCode())) {
      for (Trolley t : trolleyService.findAll()) {
        if (t.getCode().equals(timetableDTO.getTransportCode())) {
          trolley = t;
          trolley.setTimetable(tmtbl);
          trolleyService.update(trolley);
        }
      }
    } else {
      return "The transport with code " + timetableDTO.getTransportCode() + " doesn't exist in the database.";
    }
    station.setTimetable(tmtbl);
    stationService.update(station);
    update(tmtbl);
    return "The timetable has been successfully created!\n" + timetable.toString();
  }

  @Override
  public Timetable update(Timetable timetable) {
    Optional<Timetable> updateTimetable = timetableRepository.findById(timetable.getId());
    updateTimetable.get().setCode(timetable.getCode());
    updateTimetable.get().setSchedule(timetable.getSchedule());
    return timetableRepository.save(updateTimetable.get());
  }

  @Override
  public boolean delete(Long id) {
    if (timetableRepository.findAll().contains(timetableRepository.getOne(id))) {
      timetableRepository.delete(timetableRepository.getOne(id));
      return true;
    }
    return false;
  }
}

package com.sbvtransport.sbvtransport.service;

import com.sbvtransport.sbvtransport.dto.ScheduleDTO;
import com.sbvtransport.sbvtransport.model.Schedule;
import com.sbvtransport.sbvtransport.model.Station;
import com.sbvtransport.sbvtransport.repository.ScheduleRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService implements IScheduleService {

  @Autowired
  ScheduleRepository scheduleRepository;
  
  @Autowired
  StationService stationService;

  @Override
  public Schedule getOne(Long id) {
    return scheduleRepository.findById(id).orElse(null);
  }

  @Override
  public List<Schedule> findAll() {
    List <Schedule> notDeleted = new ArrayList<>();
    List<Schedule> findAll = scheduleRepository.findAll();
    for (Schedule schedule : findAll) {
      if(!schedule.isDeleted()){
        notDeleted.add(schedule);
      }
    }
    return notDeleted;
  }

  @Override
  public Schedule create(ScheduleDTO scheduleDTO) {
    Schedule schedule = new Schedule();
    Station station = new Station();
    if (stationService.findAll().contains(stationService.getOne(scheduleDTO.getStation_id()))) {
      return null;
    } else {
      station = stationService.getOne(scheduleDTO.getStation_id());
    }
    schedule.setStation(station);
    Set<Date> dates = new HashSet<>(scheduleDTO.getDates());
    schedule.setTimes(dates);
    schedule.setDeleted(false);
    return scheduleRepository.save(schedule);
  }

  @Override
  public Schedule update(Schedule schedule) {
    Optional<Schedule> updateSchedule = scheduleRepository.findById(schedule.getId());
    updateSchedule.get().setStation(schedule.getStation());
    updateSchedule.get().setTimes(schedule.getTimes());
    updateSchedule.get().setDeleted(schedule.isDeleted());
    return scheduleRepository.save(updateSchedule.get());
  }

  @Override
  public boolean delete(Long id) {
    if (scheduleRepository.findAll().contains(scheduleRepository.getOne(id))) {
      Schedule s = getOne(id);
      s.setDeleted(true);
      scheduleRepository.save(s);
      return true;
    }
    return false;
  }
}

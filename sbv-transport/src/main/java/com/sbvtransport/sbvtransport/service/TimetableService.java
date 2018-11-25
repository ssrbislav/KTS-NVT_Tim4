package com.sbvtransport.sbvtransport.service;

import com.sbvtransport.sbvtransport.model.Timetable;
import com.sbvtransport.sbvtransport.repository.TimetableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TimetableService implements ITimetableService {

  @Autowired
  TimetableRepository timetableRepository;

  @Override
  public Timetable getOne(Long id) { return timetableRepository.getOne(id); }

  @Override
  public List<Timetable> findAll() {
    return timetableRepository.findAll();
  }

  @Override
  public Timetable create(Timetable timetable) {
    return timetableRepository.save(timetable);
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

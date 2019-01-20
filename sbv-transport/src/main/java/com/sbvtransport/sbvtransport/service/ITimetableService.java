package com.sbvtransport.sbvtransport.service;

import com.sbvtransport.sbvtransport.dto.AltTimetableDTO;
import com.sbvtransport.sbvtransport.dto.TimetableDTO;
import com.sbvtransport.sbvtransport.model.Timetable;
import java.text.ParseException;
import java.util.List;

public interface ITimetableService {

  Timetable getOne(Long id);
  List<Timetable> findAll();
  Timetable create(TimetableDTO timetableDTO) throws ParseException;
  Timetable create(AltTimetableDTO timetableDTO);
  Timetable update(Timetable timetable);
  boolean delete (Long id);
}

package com.sbvtransport.sbvtransport.service;

import com.sbvtransport.sbvtransport.model.Timetable;

import java.util.List;

public interface ITimetableService {

    List<Timetable> findAll();
    Timetable create(Timetable timetable);
    Timetable update(Timetable timetable);
    boolean delete (Long id);
}

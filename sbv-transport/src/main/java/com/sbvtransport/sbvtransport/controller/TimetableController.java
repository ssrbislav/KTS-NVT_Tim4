package com.sbvtransport.sbvtransport.controller;

import com.sbvtransport.sbvtransport.model.Timetable;
import com.sbvtransport.sbvtransport.service.ITimetableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="api/timetable")
public class TimetableController {

    @Autowired
    ITimetableService timetableService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<Timetable>> getAll(){

        List<Timetable> timetables = timetableService.findAll();

        return new ResponseEntity<>(timetables, HttpStatus.OK);
    }

    @RequestMapping(value = "/addTimetable", method = RequestMethod.POST)
    public ResponseEntity<Timetable> create(@RequestBody Timetable timetable){

        Timetable newTimetable = timetableService.create(timetable);

        return new ResponseEntity<>(newTimetable,HttpStatus.OK);

    }

    @RequestMapping(value = "/updateTimetable", method = RequestMethod.POST)
    public ResponseEntity<Timetable> update(@RequestBody Timetable timetable){

        Timetable updateTimetable = timetableService.update(timetable);

        return new ResponseEntity<>(updateTimetable,HttpStatus.OK);

    }

    @RequestMapping(value = "/deleteTimetable/{id}", method = RequestMethod.GET)
    public ResponseEntity<Boolean> delete(@PathVariable Long id){

        boolean delete = timetableService.delete(id);

        return new ResponseEntity<>(delete,HttpStatus.OK);

    }
    
}
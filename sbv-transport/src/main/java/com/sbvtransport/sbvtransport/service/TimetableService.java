package com.sbvtransport.sbvtransport.service;

import com.sbvtransport.sbvtransport.dto.AltTimetableDTO;
import com.sbvtransport.sbvtransport.dto.TimetableDTO;
import com.sbvtransport.sbvtransport.model.Bus;
import com.sbvtransport.sbvtransport.model.Line;
import com.sbvtransport.sbvtransport.model.Station;
import com.sbvtransport.sbvtransport.model.Subway;
import com.sbvtransport.sbvtransport.model.Timetable;
import com.sbvtransport.sbvtransport.model.Transport;
import com.sbvtransport.sbvtransport.model.Trolley;
import com.sbvtransport.sbvtransport.repository.StationRepository;
import com.sbvtransport.sbvtransport.repository.TimetableRepository;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class TimetableService implements ITimetableService {

	@Autowired
	TimetableRepository timetableRepository;
	@Autowired
	StationService stationService;
	@Autowired
	LineService lineService;
	@Autowired
	BusService busService;
	@Autowired
	SubwayService subwayService;
	@Autowired
	TrolleyService trolleyService;

	@Override
	public Timetable getOne(Long id) {
		return timetableRepository.getOne(id);
	}

	@Override
	public List<Timetable> findAll() {
		return timetableRepository.findAll();
	}

	@Override
	public String create(TimetableDTO timetableDTO) {
		Timetable timetable = new Timetable();
		Station station;
		Bus bus;
		Subway subway;
		Trolley trolley;
		HashMap<Station, List<Date>> schedule = new HashMap<>();

//		if(timetableDTO.getTransportType() == "bus"){
//			Bus b = busService.getOne(timetableDTO.getId_transport());
//			if(b== null){
//				return "Bus not found";
//			}
//			List<Station> stations = b.getLine().getStation_list();
//
//		}else if(timetableDTO.getTransportType() == "trolley"){
//
//		}else if(timetableDTO.getTransportType() == "subway"){
//
//		}else{
//			return "Error!";
//		}
//		timetable.setCode(timetableDTO.getTransportCode());
//		for (Long stationID : timetableDTO.getTimetable().keySet()) {
//			if (!stationService.findAll().contains(stationService.getOne(stationID))) {
//				return "Station with ID " + stationID + " doesn't exist!";
//			} else {
//				station = stationService.getOne(stationID);
//
//				schedule.put(station, timetableDTO.getTimetable().get(station.getId()));

//		if(timetableDTO.getTransportType() == "bus"){
//			Bus b = busService.getOne(timetableDTO.getId_transport());
//			if(b== null){
//				return "Bus not found";

//			}
//			Set<Station> stations = b.getLine().getStation_list();
//
//		}else if(timetableDTO.getTransportType() == "trolley"){
//
//		}else if(timetableDTO.getTransportType() == "subway"){
//
//		}else{
//			return "Error!";
//		}
		for (Long stationID : timetableDTO.getTimetable().keySet()) {
			if (!stationService.findAll().contains(stationService.getOne(stationID))) {
				return "Station with ID " + stationID + " doesn't exist!";
			} else {
				station = stationService.getOne(stationID);
				List<Date> order = new ArrayList<>();
				order.addAll(timetableDTO.getTimetable().get(stationID));
				schedule.put(station, order);
			}
		}
		timetable.setSchedule(schedule);
		timetable.setCode("blabla");
		Timetable tmtbl = timetableRepository.save(timetable);

		if (timetableDTO.getTransportType().equals("bus")) {
			bus = busService.getOne(timetableDTO.getId_transport());
			bus.setTimetable(tmtbl);
			busService.update(bus);
			bus.getLine().setTimetable(tmtbl);
		} else if (timetableDTO.getTransportType().equals("subway")) {
			subway = subwayService.getOne(timetableDTO.getId_transport());
			subway.setTimetable(timetable);
			subwayService.update(subway);
			subway.getLine().setTimetable(tmtbl);
		} else if (timetableDTO.getTransportType().equals("trolley")) {
			trolley = trolleyService.getOne(timetableDTO.getId_transport());
			trolley.setTimetable(timetable);
			trolleyService.update(trolley);
			trolley.getLine().setTimetable(tmtbl);
		} else {
			return "Transport type and/or ID do not exist. Try typing one of the following: bus/subway/trolley, or a correct ID.";
		}
		//station.setTimetable(tmtbl);
		//stationService.update(station);
		update(tmtbl);
		return "The timetable has been successfully created!\n";
	}

	public String create(AltTimetableDTO timetableDTO) {
		Timetable timetable = new Timetable();
		Station station;
		Bus bus;
		Subway subway;
		Trolley trolley;
		Timetable tmtbl = new Timetable();
		timetable.setCode("blabla");
		if (timetableDTO.getTransportType().equals("bus")) {
			bus = busService.getOne(timetableDTO.getId_transport());
			Line line = lineService.getOne(bus.getLine().getId());
			station = stationService.getOne(line.getFirst_station());
			List<Date> lst = new ArrayList<>(timetableDTO.getTimetable());
			tmtbl.setSchedule(new HashMap<>());
			tmtbl.getSchedule().put(station, lst);
			int addMins = 5;
			for (Station s : bus.getLine().getStation_list()) {
				long minInMillis = 60000;
				List<Date> order = new ArrayList<>();
				if (!s.equals(bus.getLine().getFirst_station())) {
					for (Date d : timetableDTO.getTimetable()) {
						Calendar cal = Calendar.getInstance();
						cal.setTime(d);
						long t = cal.getTimeInMillis();
						Date x = new Date(t + (addMins * minInMillis));
						order.add(x);
					}
				} else {
					order.addAll(timetableDTO.getTimetable());
				}
				tmtbl.getSchedule().put(s, order);
				addMins += 5;
			}
			bus.setTimetable(tmtbl);
//			busService(bus);
			line = lineService.getOne(bus.getLine().getId());
			line.setTimetable(tmtbl);
//      lineService.update(bus.getLine());
		} else if (timetableDTO.getTransportType().equals("subway")) {
			subway = subwayService.getOne(timetableDTO.getId_transport());
			Line line = lineService.getOne(subway.getLine().getId());
			station = stationService.getOne(line.getFirst_station());
			tmtbl.getSchedule().put(station, timetableDTO.getTimetable());
			int addMins = 5;
			for (Station s : subway.getLine().getStation_list()) {
				long minInMillis = 60000;
				List<Date> order = new ArrayList<>();
				if (!s.equals(subway.getLine().getFirst_station())) {
					for (Date d : timetableDTO.getTimetable()) {
						Calendar cal = Calendar.getInstance();
						cal.setTime(d);
						long t = cal.getTimeInMillis();
						Date x = new Date(t + (addMins * minInMillis));
						order.add(x);
					}
				}
				tmtbl.getSchedule().put(s, order);
				addMins += 5;
			}
			subway.setTimetable(tmtbl);
			subwayService.update(subway);
			subway.getLine().setTimetable(tmtbl);
			lineService.update(subway.getLine());
		} else if (timetableDTO.getTransportType().equals("trolley")) {
			trolley = trolleyService.getOne(timetableDTO.getId_transport());
			Line line = lineService.getOne(trolley.getLine().getId());
			station = stationService.getOne(line.getFirst_station());
			tmtbl.getSchedule().put(station, timetableDTO.getTimetable());
			int addMins = 5;
			for (Station s : trolley.getLine().getStation_list()) {
				long minInMillis = 60000;
				List<Date> order = new ArrayList<>();
				if (!s.equals(trolley.getLine().getFirst_station())) {
					for (Date d : timetableDTO.getTimetable()) {
						Calendar cal = Calendar.getInstance();
						cal.setTime(d);
						long t = cal.getTimeInMillis();
						Date x = new Date(t + (addMins * minInMillis));
						order.add(x);
					}
				}
				tmtbl.getSchedule().put(s, order);
				addMins += 5;
			}
			trolley.setTimetable(tmtbl);
			trolleyService.update(trolley);
			trolley.getLine().setTimetable(tmtbl);
			lineService.update(trolley.getLine());
		} else {
			return "Transport type and/or ID do not exist. Try typing one of the following: bus/subway/trolley, or a correct ID.";
		}
		timetableRepository.save(tmtbl);
		//station.setTimetable(tmtbl);
		//stationService.update(station);
//		update(tmtbl);
		return "The timetable has been successfully created!\n";
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

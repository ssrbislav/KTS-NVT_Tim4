package com.sbvtransport.sbvtransport.service;

import com.sbvtransport.sbvtransport.dto.AltTimetableDTO;
import com.sbvtransport.sbvtransport.dto.ScheduleDTO;
import com.sbvtransport.sbvtransport.dto.TimetableDTO;
import com.sbvtransport.sbvtransport.model.Bus;
import com.sbvtransport.sbvtransport.model.Line;
import com.sbvtransport.sbvtransport.model.Schedule;
import com.sbvtransport.sbvtransport.model.Station;
import com.sbvtransport.sbvtransport.model.Subway;
import com.sbvtransport.sbvtransport.model.Timetable;
import com.sbvtransport.sbvtransport.model.Transport;
import com.sbvtransport.sbvtransport.model.Trolley;
import com.sbvtransport.sbvtransport.repository.StationRepository;
import com.sbvtransport.sbvtransport.repository.TimetableRepository;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
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
	@Autowired
	ScheduleService scheduleService;

	@Override
	public Timetable getOne(Long id) {
		return timetableRepository.getOne(id);
	}

	@Override
	public List<Timetable> findAll() {
		return timetableRepository.findAll();
	}

	@Override
	public String create(TimetableDTO timetableDTO) throws ParseException {
		Timetable timetable = new Timetable();
		Station station;
		Bus bus;
		Subway subway;
		Trolley trolley;
		Set<Schedule> schedule = new HashSet<>();

		for (ScheduleDTO scheduleDTO : timetableDTO.getSchedules()) {
			Long stationID = scheduleDTO.getStation_id();
			if (!stationService.findAll().contains(stationService.getOne(stationID))) {
				return "Station with ID " + stationID + " doesn't exist!";
			} else {
				station = stationService.getOne(stationID);
				Schedule s = new Schedule();
				s.setStation(station);
				Set<Date> dts = new HashSet<>();
				for(Date d : scheduleDTO.getDates()) {
					SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
					String tm = sdf.format(d);
					Date time = sdf.parse(tm);
					dts.add(time);
				}

				s.setTimes(dts);
				s.setDeleted(false);
				Schedule ss = scheduleService.scheduleRepository.save(s);
				schedule.add(ss);
			}
		}
		timetable.setSchedule(schedule);
		timetable.setCode("blabla");
		Timetable tmtbl = timetableRepository.save(timetable);

		if (timetableDTO.getTransportType().equals("bus")) {
			bus = busService.getOne(timetableDTO.getId_transport());
      tmtbl.setLine(bus.getLine());
			bus.setTimetable(tmtbl);
			busService.update(bus);
			bus.getLine().setTimetable(tmtbl);
		} else if (timetableDTO.getTransportType().equals("subway")) {
			subway = subwayService.getOne(timetableDTO.getId_transport());
      tmtbl.setLine(subway.getLine());
			subway.setTimetable(timetable);
			subwayService.update(subway);
			subway.getLine().setTimetable(tmtbl);
		} else if (timetableDTO.getTransportType().equals("trolley")) {
			trolley = trolleyService.getOne(timetableDTO.getId_transport());
      tmtbl.setLine(trolley.getLine());
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
//		Timetable timetable = new Timetable();
		Station station;
		Bus bus;
		Subway subway;
		Trolley trolley;
		Timetable tmtbl = new Timetable();
//		timetable.setCode("blabla");
    tmtbl.setCode("blabla");
		if (timetableDTO.getTransportType().equals("bus")) {
			bus = busService.getOne(timetableDTO.getId_transport());
			Line line = lineService.getOne(bus.getLine().getId());
			station = stationService.getOne(line.getFirst_station());
			Set<Schedule> schedules = new HashSet<>();
			Schedule schedule = new Schedule();
			tmtbl.setSchedule(new HashSet<>());
			schedule.setStation(station);
			Set<Date> dts = new HashSet<>();
			dts.addAll(timetableDTO.getTimetable());
			schedule.setTimes(dts);
			schedules.add(schedule);
			int addMins = 5;
			for (Station s : bus.getLine().getStation_list()) {
				long minInMillis = 60000;
				Set<Date> order = new HashSet<>();
				if (!s.equals(bus.getLine().getFirst_station())) {
					for (Date d : timetableDTO.getTimetable()) {
						Calendar cal = Calendar.getInstance();
						cal.setTime(d);
						long t = cal.getTimeInMillis();
						Date x = new Date(t + (addMins * minInMillis));
						order.add(x);
					}
					Schedule ss = new Schedule(s, order, false);
					Schedule sss = scheduleService.scheduleRepository.save(ss);
					schedules.add(sss);
				} else {
					order.addAll(timetableDTO.getTimetable());
				}
				tmtbl.setSchedule(schedules);
				addMins += 5;
			}
			bus.setTimetable(tmtbl);
//			busService(bus);
			line = lineService.getOne(bus.getLine().getId());
			line.setTimetable(tmtbl);
      tmtbl.setLine(line);
//      lineService.update(bus.getLine());
		} else if (timetableDTO.getTransportType().equals("subway")) {
			subway = subwayService.getOne(timetableDTO.getId_transport());
			Line line = lineService.getOne(subway.getLine().getId());
			station = stationService.getOne(line.getFirst_station());
			Set<Schedule> schedules = new HashSet<>();
			Schedule schedule = new Schedule();
			tmtbl.setSchedule(new HashSet<>());
			schedule.setStation(station);
      Set<Date> dts = new HashSet<>();
      dts.addAll(timetableDTO.getTimetable());
      schedule.setTimes(dts);
			schedules.add(schedule);
			int addMins = 5;
			for (Station s : subway.getLine().getStation_list()) {
				long minInMillis = 60000;
				Set<Date> order = new HashSet<>();
				if (!s.equals(subway.getLine().getFirst_station())) {
					for (Date d : timetableDTO.getTimetable()) {
						Calendar cal = Calendar.getInstance();
						cal.setTime(d);
						long t = cal.getTimeInMillis();
						Date x = new Date(t + (addMins * minInMillis));
						order.add(x);
					}
					Schedule ss = new Schedule(s, order, false);
					Schedule sss = scheduleService.scheduleRepository.save(ss);
					schedules.add(sss);
				} else {
					order.addAll(timetableDTO.getTimetable());
				}
				tmtbl.setSchedule(schedules);
				addMins += 5;
			}
			subway.setTimetable(tmtbl);
			subwayService.update(subway);
			subway.getLine().setTimetable(tmtbl);
      tmtbl.setLine(line);
			lineService.update(subway.getLine());
		} else if (timetableDTO.getTransportType().equals("trolley")) {
			trolley = trolleyService.getOne(timetableDTO.getId_transport());
			Line line = lineService.getOne(trolley.getLine().getId());
			station = stationService.getOne(line.getFirst_station());
			Set<Schedule> schedules = new HashSet<>();
			Schedule schedule = new Schedule();
			tmtbl.setSchedule(new HashSet<>());
			schedule.setStation(station);
      Set<Date> dts = new HashSet<>();
      dts.addAll(timetableDTO.getTimetable());
      schedule.setTimes(dts);
			schedules.add(schedule);
			int addMins = 5;
			for (Station s : trolley.getLine().getStation_list()) {
				long minInMillis = 60000;
				Set<Date> order = new HashSet<>();
				if (!s.equals(trolley.getLine().getFirst_station())) {
					for (Date d : timetableDTO.getTimetable()) {
						Calendar cal = Calendar.getInstance();
						cal.setTime(d);
						long t = cal.getTimeInMillis();
						Date x = new Date(t + (addMins * minInMillis));
						order.add(x);
					}
					Schedule ss = new Schedule(s, order, false);
					Schedule sss = scheduleService.scheduleRepository.save(ss);
					schedules.add(sss);
				} else {
					order.addAll(timetableDTO.getTimetable());
				}
				tmtbl.setSchedule(schedules);
				addMins += 5;
			}
			trolley.setTimetable(tmtbl);
			trolleyService.update(trolley);
			trolley.getLine().setTimetable(tmtbl);
      tmtbl.setLine(line);
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

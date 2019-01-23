package com.sbvtransport.sbvtransport.service;

import com.sbvtransport.sbvtransport.dto.AltTimetableDTO;
import com.sbvtransport.sbvtransport.dto.ScheduleDTO;
import com.sbvtransport.sbvtransport.dto.TimetableDTO;
import com.sbvtransport.sbvtransport.enumeration.TypeTransport;
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
	public Timetable create(TimetableDTO timetableDTO) {
		Timetable timetable = new Timetable();
		Line line;
		List<Schedule> schedules = new ArrayList<>();
		Transport transport;

		if (timetableDTO.getTransportType().equals("bus")) {
			transport = busService.getOne(timetableDTO.getId_transport());
		} else if (timetableDTO.getTransportType().equals("subway")) {
			transport = subwayService.getOne(timetableDTO.getId_transport());
		} else if (timetableDTO.getTransportType().equals("trolley")) {
			transport = trolleyService.getOne(timetableDTO.getId_transport());
		} else {
			return null;
		}

		if (transport == null) {
			return null;
		}

		for (ScheduleDTO scheduleDTO : timetableDTO.getSchedules()) {
			Station station = stationService.getOne(scheduleDTO.getStation_id());
			if (station == null) {
				return null;
			} else {
				Schedule schedule = new Schedule();
				schedule.setStation(station);
				Set<Date> dates = new HashSet<>();
				for (Date date : scheduleDTO.getDates()) {
					if (!dates.contains(date)) {
						dates.add(date);
					}
				}
				schedule.setTimes(dates);
				schedule.setDeleted(false);
				scheduleService.scheduleRepository.save(schedule);
				schedules.add(schedule);
			}
		}

		timetable.setSchedule(schedules);
		if (transport.getClass().equals(Bus.class)) {
			timetable.setCode(((Bus) transport).getCode());
		} else if (transport.getClass().equals(Subway.class)) {
			timetable.setCode(((Subway) transport).getCode());
		} else if (transport.getClass().equals(Trolley.class)) {
			timetable.setCode(((Trolley) transport).getCode());
		} else {
			return null;
		}

//		setLineTimetable(transport, timetable);
    Timetable t = timetableRepository.save(timetable);
		transport.setTimetable(t);
//		if (transport.getClass().equals(Bus.class)) {
//			busService.update((Bus) transport);
//		} else if (transport.getClass().equals(Subway.class)) {
//			subwayService.update((Subway) transport);
//		} else if (transport.getClass().equals(Trolley.class)) {
//			trolleyService.update((Trolley) transport);
//		} else {
//			return null;
//		}
		updateGlobalTimetable(transport.getLine());
		return t;
	}

//	public void setLineTimetable(Transport transport, Timetable timetable) {
//		Timetable lineTimetable;
//		Line line = lineService.getOne(transport.getLine().getId());
//		Set<Schedule> schedules = new HashSet<>();
//		if (transport.getLine().getTimetable() == null) {
//			lineTimetable = timetable;
//		} else {
//			lineTimetable = line.getTimetable();
//		}
//		if (line == null) {
//			return;
//		} else {
////			for (Schedule s : timetable.getSchedule()) {
////				for (Schedule schedule : lineTimetable.getSchedule()) {
////					for (Station station : line.getStation_list()) {
////						if (s.getStation().equals(station)) {
////							for (Date date : s.getTimes()) {
////								if (!schedule.getTimes().contains(date)) {
////									schedule.getTimes().add(date);
////								}
////							}
////						}
////					}
////				}
////			}
////			for (Station station : line.getStation_list()) {
////				for (Schedule schedule : timetable.getSchedule()) {
////					for (Schedule lineSchedule : lineTimetable.getSchedule()) {
////						if (station.equals(schedule.getStation())) {
////							for (Date date : schedule.getTimes()) {
////								if (!lineSchedule.getTimes().contains(date)) {
////									lineSchedule.setStation(station);
////									lineSchedule.getTimes().add(date);
////									lineSchedule.setDeleted(false);
////								}
////							}
////							schedules.add(lineSchedule);
////						}
////					}
////				}
////			}
//
//			for (Schedule schedule : timetable.getSchedule()) {
//				for (Station station : line.getStation_list()) {
//					if (schedule.getStation().getId().equals(station.getId())) {
//						for (Schedule ss : lineTimetable.getSchedule()) {
//							if (ss.getStation().getId().equals(schedule.getStation().getId())) {
//								ss.getTimes().addAll(schedule.getTimes());
//							}
//							schedules.add(ss);
//						}
//					}
//				}
//			}
//		}
//		lineTimetable.setSchedule(schedules);
//		line.setTimetable(lineTimetable);
//		lineService.update(line);
//		timetableRepository.save(lineTimetable);
//	}
//
//
//	public Timetable make(TimetableDTO timetableDTO) throws ParseException {
//		Timetable timetable = new Timetable();
//		Station station;
//		Bus bus;
//		Subway subway;
//		Trolley trolley;
//		Set<Schedule> schedule = new HashSet<>();
//
//		for (ScheduleDTO scheduleDTO : timetableDTO.getSchedules()) {
//			Long stationID = scheduleDTO.getStation_id();
//			if (!stationService.findAll().contains(stationService.getOne(stationID))) {
//				return null;
//			} else {
//				station = stationService.getOne(stationID);
//				Schedule s = new Schedule();
//				s.setStation(station);
//				Set<Date> dts = new HashSet<>();
//				for(Date d : scheduleDTO.getDates()) {
//					SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
//					String tm = sdf.format(d);
//					Date time = sdf.parse(tm);
//					dts.add(time);
//				}
//
//				s.setTimes(dts);
//				s.setDeleted(false);
//				Schedule ss = scheduleService.scheduleRepository.save(s);
//				schedule.add(ss);
//			}
//		}
//		timetable.setSchedule(schedule);
//		timetable.setCode("blabla");
//		Timetable tmtbl = timetableRepository.save(timetable);
//
//		if (timetableDTO.getTransportType().equals("bus")) {
//			bus = busService.getOne(timetableDTO.getId_transport());
//      		tmtbl.setLine(bus.getLine());
//			bus.setTimetable(tmtbl);
//			busService.update(bus);
//			Line line = lineService.getOne(bus.getLine().getId());
//			if (line.getTimetable() != null) {
//				for (Schedule s : tmtbl.getSchedule()) {
//					for (Station st : line.getStation_list()) {
//						if (s.getStation() == st) {
//							for (Schedule ss : line.getTimetable().getSchedule()) {
//								if (ss.getStation() == s.getStation()) {
//									ss.getTimes().addAll(s.getTimes());
//								}
//							}
//						}
//					}
//				}
//			} else {
//				line.setTimetable(tmtbl);
//			}
//			lineService.update(line);
////			bus.getLine().setTimetable(t);
////			bus.getLine().setTimetable(tmtbl);
//		} else if (timetableDTO.getTransportType().equals("subway")) {
//			subway = subwayService.getOne(timetableDTO.getId_transport());
//      		tmtbl.setLine(subway.getLine());
//			subway.setTimetable(timetable);
//			subwayService.update(subway);
//			Line line = lineService.getOne(subway.getLine().getId());
//			if (line.getTimetable() != null) {
//				for (Schedule s : tmtbl.getSchedule()) {
//					for (Station st : line.getStation_list()) {
//						if (s.getStation() == st) {
//							for (Schedule ss : line.getTimetable().getSchedule()) {
//								if (ss.getStation() == s.getStation()) {
//									ss.getTimes().addAll(s.getTimes());
//								}
//							}
//						}
//					}
//				}
//			} else {
//				line.setTimetable(tmtbl);
//			}
//			lineService.update(line);
////			subway.getLine().setTimetable(tmtbl);
//		} else if (timetableDTO.getTransportType().equals("trolley")) {
//			trolley = trolleyService.getOne(timetableDTO.getId_transport());
//      		tmtbl.setLine(trolley.getLine());
//			trolley.setTimetable(timetable);
//			trolleyService.update(trolley);
//			Line line = lineService.getOne(trolley.getLine().getId());
//			if (line.getTimetable() != null) {
//				for (Schedule s : tmtbl.getSchedule()) {
//					for (Station st : line.getStation_list()) {
//						if (s.getStation() == st) {
//							for (Schedule ss : line.getTimetable().getSchedule()) {
//								if (ss.getStation() == s.getStation()) {
//									ss.getTimes().addAll(s.getTimes());
//								}
//							}
//						}
//					}
//				}
//			} else {
//				line.setTimetable(tmtbl);
//			}
//			lineService.update(line);
////			trolley.getLine().setTimetable(tmtbl);
//		} else {
//			return null;
//		}
//		//station.setTimetable(tmtbl);
//		//stationService.update(station);
////		update(tmtbl);
//		return tmtbl;
//	}

	@Override
	public Timetable create(AltTimetableDTO timetableDTO) {
		Timetable timetable = new Timetable();
		Line line;
		List<Schedule> schedules = new ArrayList<>();
		Transport transport;

		if (timetableDTO.getTransportType().equals("bus")) {
			transport = busService.getOne(timetableDTO.getId_transport());
			timetable.setCode(((Bus) transport).getCode());
		} else if (timetableDTO.getTransportType().equals("subway")) {
			transport = subwayService.getOne(timetableDTO.getId_transport());
			timetable.setCode(((Subway) transport).getCode());
		} else if (timetableDTO.getTransportType().equals("trolley")) {
			transport = trolleyService.getOne(timetableDTO.getId_transport());
			timetable.setCode(((Trolley) transport).getCode());
		} else {
			return null;
		}

		if (transport == null) {
			return null;
		}

		if (transport.getTimetable() != null && !transport.getTimetable().getSchedule().isEmpty() && transport.getTimetable().getSchedule() != null) {
      for (Schedule sch : transport.getTimetable().getSchedule()) {
        sch.setDeleted(true);
      }
      transport.getTimetable().setDeleted(true);
    }

		line = transport.getLine();
		int addMins = 5;

		for (Station station : line.getStation_list()) {
			Schedule schedule = new Schedule();
			schedule.setDeleted(false);
			schedule.setStation(station);
			schedule.setTimes(new HashSet<>());
			if (station.equals(stationService.getOne(line.getFirst_station()))) {
				schedule.getTimes().addAll(timetableDTO.getTimetable());
			} else {
				long minInMillis = 60000;
				for (Date date : timetableDTO.getTimetable()) {
					Calendar cal = Calendar.getInstance();
					cal.setTime(date);
					long t = cal.getTimeInMillis();
					Date x = new Date(t + (addMins * minInMillis));
					if (!schedule.getTimes().contains(x)) {
						schedule.getTimes().add(x);
					}
				}
				addMins += 5;
			}
			scheduleService.scheduleRepository.save(schedule);
			schedules.add(schedule);
		}
		timetable.setSchedule(schedules);
//		timetable.setLine(line);
		timetable.setDeleted(false);

//		setLineTimetable(transport, timetable);
    Timetable t = timetableRepository.save(timetable);
		transport.setTimetable(timetable);
    updateGlobalTimetable(line);
		return t;
	}

	public void updateGlobalTimetable(Line line) {
    List<Timetable> global = new ArrayList<>();
		if (line.getLine_type().equals(TypeTransport.bus)) {
			for (Bus b : busService.findAll()) {
				if (b.getLine().equals(line)) {
					if (b.getTimetable() != null) {
						global.add(b.getTimetable());
					}
				}
			}
		} else if (line.getLine_type().equals(TypeTransport.subway)) {
			for (Subway s : subwayService.findAll()) {
				if (s.getLine().equals(line)) {
					if (s.getTimetable() != null) {
						global.add(s.getTimetable());
					}
				}
			}
		} else if (line.getLine_type().equals(TypeTransport.trolley)) {
			for (Trolley t : trolleyService.findAll()) {
				if (t.getLine().equals(line)) {
					if (t.getTimetable() != null) {
						global.add(t.getTimetable());
					}
				}
			}
		} else {
			return;
		}
//		if (line.getTimetable() != null) {
//			delete(line.getTimetable().getId());
//		}
		line.setTimetable(global);
		lineService.update(line);
	}

//	public Timetable make(AltTimetableDTO timetableDTO) {
////		Timetable timetable = new Timetable();
//		Station station;
//		Bus bus;
//		Subway subway;
//		Trolley trolley;
//		Timetable tmtbl = new Timetable();
////		timetable.setCode("blabla");
//    tmtbl.setCode("blabla");
//		if (timetableDTO.getTransportType().equals("bus")) {
//			bus = busService.getOne(timetableDTO.getId_transport());
//			Line line = lineService.getOne(bus.getLine().getId());
//			station = stationService.getOne(line.getFirst_station());
//			Set<Schedule> schedules = new HashSet<>();
//			Schedule schedule = new Schedule();
//			tmtbl.setSchedule(new HashSet<>());
//			schedule.setStation(station);
//			Set<Date> dts = new HashSet<>();
//			dts.addAll(timetableDTO.getTimetable());
//			schedule.setTimes(dts);
//			schedules.add(schedule);
//			int addMins = 5;
//			for (Station s : bus.getLine().getStation_list()) {
//				long minInMillis = 60000;
//				Set<Date> order = new HashSet<>();
//				if (!s.equals(bus.getLine().getFirst_station())) {
//					for (Date d : timetableDTO.getTimetable()) {
//						Calendar cal = Calendar.getInstance();
//						cal.setTime(d);
//						long t = cal.getTimeInMillis();
//						Date x = new Date(t + (addMins * minInMillis));
//						order.add(x);
//					}
//					Schedule ss = new Schedule(s, order, false);
//					Schedule sss = scheduleService.scheduleRepository.save(ss);
//					schedules.add(sss);
//				} else {
//					order.addAll(timetableDTO.getTimetable());
//				}
//				tmtbl.setSchedule(schedules);
//				addMins += 5;
//			}
//			bus.setTimetable(tmtbl);
////			busService(bus);
//			if (line.getTimetable() != null) {
//				for (Schedule s : tmtbl.getSchedule()) {
//					for (Station st : line.getStation_list()) {
//						if (s.getStation() == st) {
//							for (Schedule ss : line.getTimetable().getSchedule()) {
//								if (ss.getStation() == s.getStation()) {
//									ss.getTimes().addAll(s.getTimes());
//								}
//							}
//						}
//					}
//				}
//			} else {
//				line.setTimetable(tmtbl);
//			}
//			lineService.update(line);
////			line.setTimetable(tmtbl);
//      tmtbl.setLine(line);
////      lineService.update(bus.getLine());
//		} else if (timetableDTO.getTransportType().equals("subway")) {
//			subway = subwayService.getOne(timetableDTO.getId_transport());
//			Line line = lineService.getOne(subway.getLine().getId());
//			station = stationService.getOne(line.getFirst_station());
//			Set<Schedule> schedules = new HashSet<>();
//			Schedule schedule = new Schedule();
//			tmtbl.setSchedule(new HashSet<>());
//			schedule.setStation(station);
//      Set<Date> dts = new HashSet<>();
//      dts.addAll(timetableDTO.getTimetable());
//      schedule.setTimes(dts);
//			schedules.add(schedule);
//			int addMins = 5;
//			for (Station s : subway.getLine().getStation_list()) {
//				long minInMillis = 60000;
//				Set<Date> order = new HashSet<>();
//				if (!s.equals(subway.getLine().getFirst_station())) {
//					for (Date d : timetableDTO.getTimetable()) {
//						Calendar cal = Calendar.getInstance();
//						cal.setTime(d);
//						long t = cal.getTimeInMillis();
//						Date x = new Date(t + (addMins * minInMillis));
//						order.add(x);
//					}
//					Schedule ss = new Schedule(s, order, false);
//					Schedule sss = scheduleService.scheduleRepository.save(ss);
//					schedules.add(sss);
//				} else {
//					order.addAll(timetableDTO.getTimetable());
//				}
//				tmtbl.setSchedule(schedules);
//				addMins += 5;
//			}
//			subway.setTimetable(tmtbl);
//			subwayService.update(subway);
//			if (line.getTimetable() != null) {
//				for (Schedule s : tmtbl.getSchedule()) {
//					for (Station st : line.getStation_list()) {
//						if (s.getStation() == st) {
//							for (Schedule ss : line.getTimetable().getSchedule()) {
//								if (ss.getStation() == s.getStation()) {
//									ss.getTimes().addAll(s.getTimes());
//								}
//							}
//						}
//					}
//				}
//			} else {
//				line.setTimetable(tmtbl);
//			}
//			lineService.update(line);
////			line.setTimetable(tmtbl);
//      tmtbl.setLine(line);
//			lineService.update(subway.getLine());
//		} else if (timetableDTO.getTransportType().equals("trolley")) {
//			trolley = trolleyService.getOne(timetableDTO.getId_transport());
//			Line line = lineService.getOne(trolley.getLine().getId());
//			station = stationService.getOne(line.getFirst_station());
//			Set<Schedule> schedules = new HashSet<>();
//			Schedule schedule = new Schedule();
//			tmtbl.setSchedule(new HashSet<>());
//			schedule.setStation(station);
//      Set<Date> dts = new HashSet<>();
//      dts.addAll(timetableDTO.getTimetable());
//      schedule.setTimes(dts);
//			schedules.add(schedule);
//			int addMins = 5;
//			for (Station s : trolley.getLine().getStation_list()) {
//				long minInMillis = 60000;
//				Set<Date> order = new HashSet<>();
//				if (!s.equals(trolley.getLine().getFirst_station())) {
//					for (Date d : timetableDTO.getTimetable()) {
//						Calendar cal = Calendar.getInstance();
//						cal.setTime(d);
//						long t = cal.getTimeInMillis();
//						Date x = new Date(t + (addMins * minInMillis));
//						order.add(x);
//					}
//					Schedule ss = new Schedule(s, order, false);
//					Schedule sss = scheduleService.scheduleRepository.save(ss);
//					schedules.add(sss);
//				} else {
//					order.addAll(timetableDTO.getTimetable());
//				}
//				tmtbl.setSchedule(schedules);
//				addMins += 5;
//			}
//			trolley.setTimetable(tmtbl);
//			trolleyService.update(trolley);
//			if (line.getTimetable() != null) {
//				for (Schedule s : tmtbl.getSchedule()) {
//					for (Station st : line.getStation_list()) {
//						if (s.getStation() == st) {
//							for (Schedule ss : line.getTimetable().getSchedule()) {
//								if (ss.getStation() == s.getStation()) {
//									ss.getTimes().addAll(s.getTimes());
//								}
//							}
//						}
//					}
//				}
//			} else {
//				line.setTimetable(tmtbl);
//			}
//			lineService.update(line);
////			line.setTimetable(tmtbl);
//      tmtbl.setLine(line);
//			lineService.update(trolley.getLine());
//		} else {
//			return null;
//		}
////		timetableRepository.save(tmtbl);
//		//station.setTimetable(tmtbl);
//		//stationService.update(station);
////		update(tmtbl);
//		return timetableRepository.save(tmtbl);
//	}

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

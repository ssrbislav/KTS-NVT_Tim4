package com.sbvtransport.sbvtransport.service;

import com.sbvtransport.sbvtransport.dto.AddFirstStationDTO;
import com.sbvtransport.sbvtransport.dto.AltTimetableDTO;
import com.sbvtransport.sbvtransport.dto.FilterSearchLineDTO;
import com.sbvtransport.sbvtransport.dto.LineDTO;
import com.sbvtransport.sbvtransport.enumeration.TypeTransport;
import com.sbvtransport.sbvtransport.model.Bus;
import com.sbvtransport.sbvtransport.model.Line;
import com.sbvtransport.sbvtransport.model.Schedule;
import com.sbvtransport.sbvtransport.model.Station;
import com.sbvtransport.sbvtransport.model.Subway;
import com.sbvtransport.sbvtransport.model.Timetable;
import com.sbvtransport.sbvtransport.model.Transport;
import com.sbvtransport.sbvtransport.model.Trolley;
import com.sbvtransport.sbvtransport.repository.LineRepository;
import com.sbvtransport.sbvtransport.repository.StationRepository;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LineService implements ILineService {

	@Autowired
	LineRepository lineRepository;

	@Autowired
	StationRepository stationRepository;

	@Autowired
	TimetableService timetableService;

	@Autowired
	ScheduleService scheduleService;

	@Autowired
	StationService stationService;

	@Autowired
	BusService busService;

	@Autowired
	SubwayService subwayService;

	@Autowired
	TrolleyService trolleyService;

	@Override
	public Line getOne(Long id) {
		return lineRepository.findById(id).orElse(null);
	}

	@Override
	public List<Line> findAll() {
		List<Line> notDeleted = new ArrayList<>();
		List<Line> findAll = lineRepository.findAll();
		for (Line line : findAll) {
			if (!line.isDeleted()) {
				notDeleted.add(line);
			}
		}
		return notDeleted;
	}

	@Override
	public Line create(LineDTO lineDTO) {
		Line line = new Line();
		line.setName(lineDTO.getName());
		if (lineDTO.getLine_type().equals("bus")) {
			line.setLine_type(TypeTransport.bus);
		} else if (lineDTO.getLine_type().equals("subway")) {
			line.setLine_type(TypeTransport.subway);
		} else if (lineDTO.getLine_type().equals("trolley")) {
			line.setLine_type(TypeTransport.trolley);
		} else {
			return null;
		}
		line.setStation_list(new ArrayList<Station>());
		line.setZone(lineDTO.getZone());
		line.setDeleted(false);
		line.setFirst_station(null);

		return lineRepository.save(line);
	}

	@Override
	public Line update(Line line) {
		Optional<Line> updateLine = lineRepository.findById(line.getId());
		updateLine.get().setName(line.getName());
		updateLine.get().setLine_type(line.getLine_type());
		updateLine.get().setStation_list(line.getStation_list());
		updateLine.get().setTimetable(line.getTimetable());
		return lineRepository.save(updateLine.get());
	}

	@Override
	public boolean delete(Long id) {
		if (lineRepository.findAll().contains(lineRepository.getOne(id))) {
			Line l = lineRepository.getOne(id);
			if (l.getLine_type().equals(TypeTransport.bus)) {
				busService.deleteBecauseLine(l.getId());
			} else if (l.getLine_type().equals(TypeTransport.subway)) {
				subwayService.deleteBecauseLine(l.getId());
			} else if (l.getLine_type().equals(TypeTransport.trolley)) {
				trolleyService.deleteBecauseLine(l.getId());
			}
			l.setDeleted(true);
			lineRepository.save(l);
			return true;
		}
		return false;
	}

	@Override
	public String addStation(AddFirstStationDTO addStation) {

		Line l = lineRepository.findById(addStation.getId_line()).orElse(null);
		if (l == null || l.isDeleted()) {
			return "";

		}
		Station s = stationRepository.findById(addStation.getId_station()).orElse(null);
		if (s == null || s.isDeleted()) {
			return "";
		}

		if (l.getStation_list().size() == 0) {
			l.setFirst_station(s.getId());
		} else {
			List<Station> stations = l.getStation_list();
			int i = 1;
			for (Station station : stations) {
				if (!station.isDeleted()) {
					i++;
					break;
				}
			}
			if (i == 1) {
				l.setFirst_station(s.getId());
			}
		}

		l.addStation(s);
		s.setLine(l);
		stationRepository.save(s);
//		if (l.getTimetable() != null && !l.getTimetable().isEmpty()) {
//			for (Timetable t : l.getTimetable()) {
//				for (Schedule sch : t.getSchedule()) {
//					sch.setDeleted(true);
//				}
//				t.setDeleted(true);
//			}
//		}
		lineRepository.save(l);

		if (l.getLine_type().equals(TypeTransport.bus)) {
			for (Bus bus : busService.findAll()) {
				if (bus.getTimetable() != null && bus.getTimetable().getSchedule() != null && !bus.getTimetable().getSchedule().isEmpty()) {
					if (bus.getLine().equals(l)) {
						AltTimetableDTO altTimetableDTO = new AltTimetableDTO();
						altTimetableDTO.setId_transport(bus.getId());
						altTimetableDTO.setTransportType("bus");
						altTimetableDTO.setTimetable(new ArrayList<>());
						for (Schedule schedule : bus.getTimetable().getSchedule()) {
							if (schedule.getStation().getId().equals(l.getFirst_station())) {
								altTimetableDTO.getTimetable().addAll(schedule.getTimes());
							}
						}
						timetableService.create(altTimetableDTO);
					}
				}
			}
		} else if (l.getLine_type().equals(TypeTransport.subway)) {
			for (Subway subway : subwayService.findAll()) {
				if (subway.getLine().equals(l)) {
					if (subway.getTimetable() != null && subway.getTimetable().getSchedule() != null && !subway.getTimetable().getSchedule().isEmpty()) {
						AltTimetableDTO altTimetableDTO = new AltTimetableDTO();
						altTimetableDTO.setId_transport(subway.getId());
						altTimetableDTO.setTransportType("subway");
						altTimetableDTO.setTimetable(new ArrayList<>());
						for (Schedule schedule : subway.getTimetable().getSchedule()) {
							if (schedule.getStation().getId().equals(l.getFirst_station())) {
								altTimetableDTO.getTimetable().addAll(schedule.getTimes());
							}
						}
						timetableService.create(altTimetableDTO);
					}
				}
			}
		} else if (l.getLine_type().equals(TypeTransport.trolley)) {
			for (Trolley trolley : trolleyService.findAll()) {
				if (trolley.getLine().equals(l)) {
					if (trolley.getTimetable() != null && trolley.getTimetable().getSchedule() != null && !trolley.getTimetable().getSchedule().isEmpty()) {
						AltTimetableDTO altTimetableDTO = new AltTimetableDTO();
						altTimetableDTO.setId_transport(trolley.getId());
						altTimetableDTO.setTransportType("trolley");
						altTimetableDTO.setTimetable(new ArrayList<>());

						for (Schedule schedule : trolley.getTimetable().getSchedule()) {
							if (schedule.getStation().getId().equals(l.getFirst_station())) {
								altTimetableDTO.getTimetable().addAll(schedule.getTimes());
							}
						}
						timetableService.create(altTimetableDTO);
					}
				}
			}
		} else {
			return "";
		}

		return "Station successfully added!";
	}

	public void recalculateTimetables(Line line) {
		if (line.getLine_type().equals(TypeTransport.bus)) {
			for (Bus b : busService.findAll()) {
				recalculateTimetables(b, line);
			}
		} else if (line.getLine_type().equals(TypeTransport.subway)) {
			for (Subway s : subwayService.findAll()) {
				recalculateTimetables(s, line);
			}
		} else if (line.getLine_type().equals(TypeTransport.trolley)) {
			for (Trolley t : trolleyService.findAll()) {
				recalculateTimetables(t, line);
			}
		} else {
			return;
		}
	}

	public void recalculateTimetables(Transport transport, Line line) {
		Timetable timetable = transport.getTimetable();
		List<Schedule> schedules = new ArrayList<>();

		int addMins = 5;

		for (Schedule schedule : timetable.getSchedule()) {
			if (schedule.getTimes().isEmpty()) {
				int i;
			}
		}

		for (Station station : line.getStation_list()) {
			Schedule schedule = new Schedule();
			schedule.setDeleted(false);
			schedule.setStation(station);
			schedule.setTimes(new HashSet<>());
			Set<Date> original = new HashSet<>();
			if (station.equals(stationService.getOne(line.getFirst_station()))) {
				for (Schedule s : transport.getTimetable().getSchedule()) {
					if (s.getStation().equals(line.getFirst_station())) {
						schedule.getTimes().addAll(s.getTimes());
						original.addAll(s.getTimes());
					}
				}
			} else {
				long minInMillis = 60000;
				for (Date date : original) {
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

//		setLineTimetable(transport, timetable);
		Timetable t = timetableService.update(timetable);
		transport.setTimetable(timetable);
		timetableService.updateGlobalTimetable(line);
	}

	public void checkFirstStation(Long idStation) {
		for (Line line : findAll()) {
			if (line.getFirst_station() != null) {
				if (line.getFirst_station().equals(idStation)) {
					List<Station> stations = line.getStation_list();
					int i = 1;
					for (Station station : stations) {
						if (!station.isDeleted()) {
							i++;
							line.setFirst_station(station.getId());
							lineRepository.save(line);
							break;
						}
					}
					if (i == 1) {
						line.setFirst_station(null);
						lineRepository.save(line);
					}

				}
			}

		}
	}

	@Override
	public Line addListStations(List<AddFirstStationDTO> list) {

		Line l = lineRepository.getOne(list.get(0).getId_line());
		if (l == null || l.isDeleted()) {
			return null;

		}
		l.setFirst_station(list.get(0).getId_station());

		List<Station> addStations = new ArrayList<>();
		for (AddFirstStationDTO addFirstStationDTO : list) {
			for (Station station : stationRepository.findAll()) {
				if (addFirstStationDTO.getId_station() == station.getId()) {
					addStations.add(station);
				}
			}
		}
		if (addStations.isEmpty()) {
			return null;
		}

		l.setStation_list(addStations);

		for (Station station : addStations) {
			station.setLine(l);
			stationRepository.save(station);
		}

		if (l.getLine_type().equals(TypeTransport.bus)) {
			for (Bus bus : busService.findAll()) {
				if (bus.getTimetable() != null && bus.getTimetable().getSchedule() != null && !bus.getTimetable().getSchedule().isEmpty()) {
					if (bus.getLine().equals(l)) {
						AltTimetableDTO altTimetableDTO = new AltTimetableDTO();
						altTimetableDTO.setId_transport(bus.getId());
						altTimetableDTO.setTransportType("bus");
						altTimetableDTO.setTimetable(new ArrayList<>());
						for (Schedule schedule : bus.getTimetable().getSchedule()) {
							if (schedule.getStation().getId().equals(l.getFirst_station())) {
								altTimetableDTO.getTimetable().addAll(schedule.getTimes());
							}
						}
						timetableService.create(altTimetableDTO);
					}
				}
			}
		} else if (l.getLine_type().equals(TypeTransport.subway)) {
			for (Subway subway : subwayService.findAll()) {
				if (subway.getLine().equals(l)) {
					if (subway.getTimetable() != null && subway.getTimetable().getSchedule() != null && !subway.getTimetable().getSchedule().isEmpty()) {
						AltTimetableDTO altTimetableDTO = new AltTimetableDTO();
						altTimetableDTO.setId_transport(subway.getId());
						altTimetableDTO.setTransportType("subway");
						altTimetableDTO.setTimetable(new ArrayList<>());
						for (Schedule schedule : subway.getTimetable().getSchedule()) {
							if (schedule.getStation().getId().equals(l.getFirst_station())) {
								altTimetableDTO.getTimetable().addAll(schedule.getTimes());
							}
						}
						timetableService.create(altTimetableDTO);
					}
				}
			}
		} else if (l.getLine_type().equals(TypeTransport.trolley)) {
			for (Trolley trolley : trolleyService.findAll()) {
				if (trolley.getLine().equals(l)) {
					if (trolley.getTimetable() != null && trolley.getTimetable().getSchedule() != null && !trolley.getTimetable().getSchedule().isEmpty()) {
						AltTimetableDTO altTimetableDTO = new AltTimetableDTO();
						altTimetableDTO.setId_transport(trolley.getId());
						altTimetableDTO.setTransportType("trolley");
						altTimetableDTO.setTimetable(new ArrayList<>());

						for (Schedule schedule : trolley.getTimetable().getSchedule()) {
							if (schedule.getStation().getId().equals(l.getFirst_station())) {
								altTimetableDTO.getTimetable().addAll(schedule.getTimes());
							}
						}
						timetableService.create(altTimetableDTO);
					}
				}
			}
		} else {
			return null;
		}

		return lineRepository.save(l);
	}

	@Override
	public Line changeListStations(List<AddFirstStationDTO> list) {

		Line l = lineRepository.getOne(list.get(0).getId_line());
		if (l == null || l.isDeleted()) {
			return null;

		}
		List<Station> oldStations = l.getStation_list();
		for (Station station : oldStations) {
			station.getLine().remove(l);
			stationRepository.save(station);

		}
		return addListStations(list);

	}

	@Override
	public List<Line> filterSearch(FilterSearchLineDTO filterSearch) {

		List<Line> allLines = findAll();
		List<Line> zoneFilter = new ArrayList<>();
		List<Line> typeFilter = new ArrayList<>();
		List<Line> stationFilter = new ArrayList<>();
		List<Line> finalList = new ArrayList<>();

		// filter zone
		if (filterSearch.getZone() != "") {
			for (Line line : allLines) {
				if (line.getZone().toString().equals(filterSearch.getZone())) {
					zoneFilter.add(line);
				}
			}

		} else {
			zoneFilter = allLines;
		}

		// filter type transport
		if (filterSearch.getType() != "") {
			for (Line line : zoneFilter) {
				if (line.getLine_type().toString().equals(filterSearch.getType())) {
					typeFilter.add(line);
				}
			}

		} else {
			typeFilter = zoneFilter;
		}
		// filter station
		if (filterSearch.getId_station() != null) {
			for (Line line : typeFilter) {
				if (!line.getStation_list().isEmpty()) {
					for (Station s : line.getStation_list()) {
						if (s.getId() == filterSearch.getId_station()) {
							stationFilter.add(line);
							break;
						}

					}
				}

			}

		} else {
			stationFilter = typeFilter;
		}
		//search by name
		if (filterSearch.getSearch_text() != "") {
			for (Line line : stationFilter) {
				if (line.getName().toLowerCase().contains(filterSearch.getSearch_text().toLowerCase())) {
					finalList.add(line);
				}
			}

		} else {
			finalList = stationFilter;
		}

		return finalList;
	}

}

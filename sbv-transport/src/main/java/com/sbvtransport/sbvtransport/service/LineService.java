package com.sbvtransport.sbvtransport.service;

import com.sbvtransport.sbvtransport.dto.AddFirstStationDTO;
import com.sbvtransport.sbvtransport.dto.LineDTO;
import com.sbvtransport.sbvtransport.enumeration.TypeTransport;
import com.sbvtransport.sbvtransport.model.Line;
import com.sbvtransport.sbvtransport.model.Station;
import com.sbvtransport.sbvtransport.repository.LineRepository;
import com.sbvtransport.sbvtransport.repository.StationRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LineService implements ILineService {

	@Autowired
	LineRepository lineRepository;

	@Autowired
	StationRepository stationRepository;

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
	public String create(LineDTO lineDTO) {
		Line line = new Line();
		line.setName(lineDTO.getName());
		if (lineDTO.getLine_type().equals("bus")) {
			line.setLine_type(TypeTransport.bus);
		} else if (lineDTO.getLine_type().equals("subway")) {
			line.setLine_type(TypeTransport.subway);
		} else if (lineDTO.getLine_type().equals("trolley")) {
			line.setLine_type(TypeTransport.trolley);
		} else {
			return "Transport type " + lineDTO.getLine_type() + " doesn't exist!";
		}
		line.setStation_list(new ArrayList<Station>());
		line.setZone(lineDTO.getZone());
		line.setDeleted(false);
		line.setFirst_station(null);
		lineRepository.save(line);
		return "The line has been successfully created!";
	}

	@Override
	public Line update(Line line) {
		Optional<Line> updateLine = lineRepository.findById(line.getId());
		updateLine.get().setName(line.getName());
		updateLine.get().setLine_type(line.getLine_type());
		updateLine.get().setStation_list(line.getStation_list());
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

		Line l = lineRepository.getOne(addStation.getId_line());
		if (l == null || l.isDeleted()) {
			return "The line doesn't exist!";

		}
		Station s = stationRepository.findById(addStation.getId_station()).orElse(null);
		if (s == null || s.isDeleted()) {
			return "The station doesn't exist!";
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
		lineRepository.save(l);

		return "Successfully station added!";
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

}

package com.sbvtransport.sbvtransport.controller;

import com.sbvtransport.sbvtransport.dto.ChangeStationDTO;
import com.sbvtransport.sbvtransport.dto.StationDTO;
import com.sbvtransport.sbvtransport.model.Station;
import com.sbvtransport.sbvtransport.service.IStationService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
@CrossOrigin
@RestController
@RequestMapping(value = "api/station")
public class StationController {

	@Autowired
	IStationService stationService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<List<Station>> getAll() {

		List<Station> stations = stationService.findAll();

		return new ResponseEntity<>(stations, HttpStatus.OK);
	}

	@RequestMapping(value = "/getStation/{id}", method = RequestMethod.GET)
	public ResponseEntity<Station> getOne(@PathVariable Long id) {

		Station station = stationService.getOne(id);

		return new ResponseEntity<>(station, HttpStatus.OK);

	}

	@RequestMapping(value = "/addStation", method = RequestMethod.POST)
	public ResponseEntity<Station> create(@RequestBody StationDTO station) {

		Station newStation = stationService.create(station);

		return new ResponseEntity<>(newStation, HttpStatus.OK);

	}

	@RequestMapping(value = "/updateStation", method = RequestMethod.POST)
	public ResponseEntity<Station> update(@RequestBody ChangeStationDTO station) {

		Station updateStation = stationService.change(station);

		return new ResponseEntity<>(updateStation, HttpStatus.OK);

	}

	@RequestMapping(value = "/deleteStation/{id}", method = RequestMethod.GET)
	public ResponseEntity<Boolean> delete(@PathVariable Long id) {

		boolean delete = stationService.delete(id);

		return new ResponseEntity<>(delete, HttpStatus.OK);

	}


}

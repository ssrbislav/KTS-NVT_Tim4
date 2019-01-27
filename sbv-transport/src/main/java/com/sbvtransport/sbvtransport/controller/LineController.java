package com.sbvtransport.sbvtransport.controller;

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
import com.sbvtransport.sbvtransport.dto.AddFirstStationDTO;
import com.sbvtransport.sbvtransport.dto.FilterSearchLineDTO;
import com.sbvtransport.sbvtransport.dto.LineDTO;
import com.sbvtransport.sbvtransport.model.Line;
import com.sbvtransport.sbvtransport.service.ILineService;

@CrossOrigin
@RestController
@RequestMapping(value = "api/line")
public class LineController {

	@Autowired
	ILineService lineService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<List<Line>> getAll() {

		List<Line> lines = lineService.findAll();

		return new ResponseEntity<>(lines, HttpStatus.OK);
	}

	@RequestMapping(value = "/getLine/{id}", method = RequestMethod.GET)
	public ResponseEntity<Line> getOne(@PathVariable Long id) {

		Line line = lineService.getOne(id);

		return new ResponseEntity<>(line, HttpStatus.OK);

	}

	@RequestMapping(value = "/addLine", method = RequestMethod.POST)
	public ResponseEntity<Line> create(@RequestBody LineDTO line) {

		Line newLine = lineService.create(line);

		if (newLine == null) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(newLine, HttpStatus.OK);

	}

	@RequestMapping(value = "/updateLine", method = RequestMethod.POST)
	public ResponseEntity<Line> update(@RequestBody List<AddFirstStationDTO> line) {

		Line updateLine = lineService.changeListStations(line);

		if (updateLine == null) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(updateLine, HttpStatus.OK);

	}

	@RequestMapping(value = "/deleteLine/{id}", method = RequestMethod.POST)
	public ResponseEntity<Boolean> delete(@PathVariable Long id) {

		boolean delete = lineService.delete(id);
		if (!delete) {
			return new ResponseEntity<>(delete, HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(delete, HttpStatus.OK);

	}

	@RequestMapping(value = "/addStation", method = RequestMethod.POST)
	public ResponseEntity<String> addStation(@RequestBody AddFirstStationDTO addStation) {

		String updateLine = lineService.addStation(addStation);

		if (updateLine.equals("")) {
			return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(updateLine, HttpStatus.OK);

	}

	@RequestMapping(value = "/addListStation", method = RequestMethod.POST)
	public ResponseEntity<Line> addListStation(@RequestBody List<AddFirstStationDTO> addStation) {

		Line updateLine = lineService.addListStations(addStation);

		if (updateLine == null) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(updateLine, HttpStatus.OK);

	}

	@RequestMapping(value = "/searchFilter", method = RequestMethod.POST)
	public ResponseEntity<List<Line>> filterSearch(@RequestBody FilterSearchLineDTO filterSearch) {

		List<Line> list = lineService.filterSearch(filterSearch);

		return new ResponseEntity<>(list, HttpStatus.OK);

	}

}

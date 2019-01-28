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
import com.sbvtransport.sbvtransport.dto.AddLocationDTO;
import com.sbvtransport.sbvtransport.dto.ChangeTransportDTO;
import com.sbvtransport.sbvtransport.dto.FilterSearchTransportDTO;
import com.sbvtransport.sbvtransport.dto.TrolleyDTO;
import com.sbvtransport.sbvtransport.model.Trolley;
import com.sbvtransport.sbvtransport.service.ITicketService;
import com.sbvtransport.sbvtransport.service.ITrolleyService;

@CrossOrigin
@RestController
@RequestMapping(value = "api/trolley")
public class TrolleyController {

	@Autowired
	ITrolleyService trolleyService;

	@Autowired
	ITicketService ticketService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<List<Trolley>> getAll() {

		List<Trolley> trolleys = trolleyService.findAll();

		return new ResponseEntity<>(trolleys, HttpStatus.OK);

	}

	@RequestMapping(value = "/getTrolley/{id}", method = RequestMethod.GET)
	public ResponseEntity<Trolley> getOne(@PathVariable Long id) {

		Trolley getTrolley = trolleyService.getOne(id);

		if (getTrolley == null) {
			return new ResponseEntity<>(getTrolley, HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(getTrolley, HttpStatus.OK);

	}

	@RequestMapping(value = "/addTrolley", method = RequestMethod.POST)
	public ResponseEntity<Trolley> create(@RequestBody TrolleyDTO trolley) {

		Trolley newTrolley = trolleyService.create(trolley);

		if (newTrolley == null) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(newTrolley, HttpStatus.OK);

	}

	@RequestMapping(value = "/updateTrolley", method = RequestMethod.POST)
	public ResponseEntity<Trolley> update(@RequestBody ChangeTransportDTO trolley) {

		Trolley updateTrolley = null;
		try {
			updateTrolley = trolleyService.change(trolley);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

		}

		return new ResponseEntity<>(updateTrolley, HttpStatus.OK);

	}

	@RequestMapping(value = "/deleteTrolley/{id}", method = RequestMethod.GET)
	public ResponseEntity<Boolean> delete(@PathVariable Long id) {

		if (trolleyService.getOne(id) != null)
			ticketService.changeBecauseTransport(trolleyService.getOne(id).getCode());

		boolean deleteTrolley = trolleyService.delete(id);

		return new ResponseEntity<>(deleteTrolley, HttpStatus.OK);

	}

	// add current location of the trolley
	@RequestMapping(value = "/addLocation", method = RequestMethod.POST)
	public ResponseEntity<Trolley> addLocation(@RequestBody AddLocationDTO trolley) {

		Trolley updateTrolley = trolleyService.addLocation(trolley);

		if (updateTrolley == null) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(updateTrolley, HttpStatus.OK);

	}

	@RequestMapping(value = "/searchFilter", method = RequestMethod.POST)
	public ResponseEntity<List<Trolley>> filterSearch(@RequestBody FilterSearchTransportDTO filterSearch) {

		List<Trolley> list = trolleyService.searchFilter(filterSearch);

		return new ResponseEntity<>(list, HttpStatus.OK);

	}

}

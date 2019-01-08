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
import com.sbvtransport.sbvtransport.dto.BusDTO;
import com.sbvtransport.sbvtransport.model.Bus;
import com.sbvtransport.sbvtransport.service.IBusService;
import com.sbvtransport.sbvtransport.service.ITicketService;
@CrossOrigin
@RestController
@RequestMapping(value = "api/bus")
public class BusController {

	@Autowired
	IBusService busService;

	@Autowired
	ITicketService ticketService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<List<Bus>> getAll() {

		List<Bus> buses = busService.findAll();

		return new ResponseEntity<>(buses, HttpStatus.OK);

	}

	@RequestMapping(value = "/getBus/{id}", method = RequestMethod.GET)
	public ResponseEntity<Bus> getOne(@PathVariable Long id) {

		Bus bus = busService.getOne(id);

		return new ResponseEntity<>(bus, HttpStatus.OK);

	}

	@RequestMapping(value = "/addBus", method = RequestMethod.POST)
	public ResponseEntity<Bus> create(@RequestBody BusDTO bus) {

		Bus newBus = busService.create(bus);

		return new ResponseEntity<>(newBus, HttpStatus.OK);

	}

	@RequestMapping(value = "/updateBus", method = RequestMethod.POST)
	public ResponseEntity<Bus> update(@RequestBody Bus bus) {

		Bus updateBus = busService.update(bus);

		return new ResponseEntity<>(updateBus, HttpStatus.OK);

	}

	@RequestMapping(value = "/deleteBus/{id}", method = RequestMethod.GET)
	public ResponseEntity<Boolean> delete(@PathVariable Long id) {

		if(busService.getOne(id)!= null)
			ticketService.changeBecauseTransport(busService.getOne(id).getCode());
		
		boolean delete = busService.delete(id);

		return new ResponseEntity<>(delete, HttpStatus.OK);

	}

}

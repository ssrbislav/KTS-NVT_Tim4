package com.sbvtransport.sbvtransport.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sbvtransport.sbvtransport.model.Subway;
import com.sbvtransport.sbvtransport.service.ISubwayService;
import com.sbvtransport.sbvtransport.service.ITicketService;

@RestController
public class SubwayController {

	@Autowired
	ISubwayService subwayService;
	
	@Autowired
	ITicketService ticketService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<List<Subway>> getAll() {

		List<Subway> subways = subwayService.findAll();

		return new ResponseEntity<>(subways, HttpStatus.OK);

	}

	@RequestMapping(value = "/getSubway/{id}", method = RequestMethod.GET)
	public ResponseEntity<Subway> getOne(@PathVariable Long id) {

		Subway getSubway = subwayService.getOne(id);

		return new ResponseEntity<>(getSubway, HttpStatus.OK);

	}

	@RequestMapping(value = "/addSubway", method = RequestMethod.POST)
	public ResponseEntity<Subway> create(@RequestBody Subway subway) {

		Subway addSubway = subwayService.create(subway);

		return new ResponseEntity<>(addSubway, HttpStatus.OK);

	}

	@RequestMapping(value = "/updateSubway", method = RequestMethod.POST)
	public ResponseEntity<Subway> update(@RequestBody Subway subway) {

		Subway updateSubway = subwayService.update(subway);

		return new ResponseEntity<>(updateSubway, HttpStatus.OK);

	}

	@RequestMapping(value = "/deleteSubway/{id}", method = RequestMethod.GET)
	public ResponseEntity<Boolean> delete(@PathVariable Long id) {

		boolean delete = subwayService.delete(id);
		
		boolean deleteTicket = ticketService.deleteBecauseTransport(subwayService.getOne(id).getCode());

		return new ResponseEntity<>(delete, HttpStatus.OK);

	}

}

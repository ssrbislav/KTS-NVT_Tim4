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

import com.sbvtransport.sbvtransport.model.Transport;
import com.sbvtransport.sbvtransport.service.ITransportService;

@RestController
@RequestMapping(value = "api/trasport")
public class TransportController {

	@Autowired
	ITransportService transportService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<List<Transport>> getAll() {

		List<Transport> transports = transportService.findAll();

		return new ResponseEntity<>(transports, HttpStatus.OK);
	}

	@RequestMapping(value = "/addTransport", method = RequestMethod.POST)
	public ResponseEntity<Transport> create(@RequestBody Transport transport) {

		Transport newTransport = transportService.create(transport);

		return new ResponseEntity<Transport>(newTransport, HttpStatus.OK);
	}

	@RequestMapping(value = "/updateTransport", method = RequestMethod.POST)
	public ResponseEntity<Transport> update(@RequestBody Transport transport) {

		Transport newTransport = transportService.update(transport);

		return new ResponseEntity<Transport>(newTransport, HttpStatus.OK);
	}

	@RequestMapping(value = "deleteTransport/{id}", method = RequestMethod.GET)
	public ResponseEntity<Boolean> delete(@PathVariable Long id) {

		boolean deleteTransport = transportService.delete(id);

		return new ResponseEntity<Boolean>(deleteTransport, HttpStatus.OK);
	}

}

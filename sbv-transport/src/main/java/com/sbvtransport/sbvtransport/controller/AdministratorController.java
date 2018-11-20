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

import com.sbvtransport.sbvtransport.dto.ValidateDocument;
import com.sbvtransport.sbvtransport.model.Administrator;
import com.sbvtransport.sbvtransport.model.Passenger;
import com.sbvtransport.sbvtransport.service.IAdministratorService;
import com.sbvtransport.sbvtransport.service.IPassengerService;

@RestController
@RequestMapping(value = "api/administrator")
public class AdministratorController {

	@Autowired
	IAdministratorService administratorService;

	@Autowired
	IPassengerService passengerService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<List<Administrator>> getAll() {

		List<Administrator> administrators = administratorService.findAll();

		return new ResponseEntity<>(administrators, HttpStatus.OK);
	}

	@RequestMapping(value = "/addAdministrator", method = RequestMethod.POST)
	public ResponseEntity<Administrator> create(@RequestBody Administrator administrator) {

		Administrator newAdministrator = administratorService.create(administrator);

		return new ResponseEntity<>(newAdministrator, HttpStatus.OK);

	}

	@RequestMapping(value = "/updateAdministrator", method = RequestMethod.POST)
	public ResponseEntity<Administrator> update(@RequestBody Administrator administrator) {

		Administrator updateAdministrator = administratorService.update(administrator);

		return new ResponseEntity<>(updateAdministrator, HttpStatus.OK);

	}

	@RequestMapping(value = "/deleteAdministrator/{id}", method = RequestMethod.GET)
	public ResponseEntity<Boolean> delete(@PathVariable Long id) {

		boolean delete = administratorService.delete(id);

		return new ResponseEntity<>(delete, HttpStatus.OK);

	}

	@RequestMapping(value = "/validateDocument", method = RequestMethod.POST)
	public ResponseEntity<Boolean> validateDocument(@RequestBody ValidateDocument validateDocument) {

		for (Passenger passenger : passengerService.findAll()) {

			if (passenger.getId() == validateDocument.getIdPassenger()) {
				Long passengerId = validateDocument.getIdPassenger();
				if (administratorService.validatePassengerDocument(passengerId)) {
					passengerService.update(passenger);
					return new ResponseEntity<>(true, HttpStatus.OK);
				}
			}
		}

		return new ResponseEntity<>(false, HttpStatus.OK);

	}

}

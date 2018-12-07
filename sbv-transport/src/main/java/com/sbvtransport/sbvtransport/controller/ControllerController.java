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

import com.sbvtransport.sbvtransport.model.Controller;
import com.sbvtransport.sbvtransport.service.IControllerService;

@RestController
@RequestMapping(value = "api/controller")
public class ControllerController {

	@Autowired
	IControllerService controllerService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<List<Controller>> getAll() {

		List<Controller> controllers = controllerService.findAll();

		return new ResponseEntity<>(controllers, HttpStatus.OK);
	}

	@RequestMapping(value = "/addController", method = RequestMethod.POST)
	public ResponseEntity<Controller> create(@RequestBody Controller controller) {

		Controller newController = controllerService.create(controller);

		return new ResponseEntity<>(newController, HttpStatus.OK);

	}

	@RequestMapping(value = "/updateController", method = RequestMethod.POST)
	public ResponseEntity<Controller> update(@RequestBody Controller controller) {

		Controller updateController = controllerService.update(controller);

		return new ResponseEntity<>(updateController, HttpStatus.OK);

	}

	@RequestMapping(value = "/deleteController/{id}", method = RequestMethod.GET)
	public ResponseEntity<Boolean> delete(@PathVariable Long id) {

		boolean delete = controllerService.delete(id);

		return new ResponseEntity<>(delete, HttpStatus.OK);

	}

}

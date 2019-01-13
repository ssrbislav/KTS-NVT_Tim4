package com.sbvtransport.sbvtransport.controller;

import com.sbvtransport.sbvtransport.dto.LocationDTO;
import com.sbvtransport.sbvtransport.model.Location;
import com.sbvtransport.sbvtransport.service.ILocationService;
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
@RequestMapping(value = "api/location")
public class LocationController {

	@Autowired
	ILocationService locationService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<List<Location>> getAll() {

		List<Location> locations = locationService.findAll();

		return new ResponseEntity<>(locations, HttpStatus.OK);
	}

	@RequestMapping(value = "/getLocation/{id}", method = RequestMethod.GET)
	public ResponseEntity<Location> getOne(@PathVariable Long id) {

		Location location = locationService.getOne(id);

		return new ResponseEntity<>(location, HttpStatus.OK);

	}

	@RequestMapping(value = "/addLocation", method = RequestMethod.POST)
	public ResponseEntity<Location> create(@RequestBody LocationDTO location) {

		Location newLocation = locationService.create(location);

		return new ResponseEntity<>(newLocation, HttpStatus.OK);

	}

	@RequestMapping(value = "/updateLocation", method = RequestMethod.POST)
	public ResponseEntity<Location> update(@RequestBody Location location) {

		Location updateLocation = locationService.update(location);

		return new ResponseEntity<>(updateLocation, HttpStatus.OK);

	}

	@RequestMapping(value = "/deleteLocation/{id}", method = RequestMethod.GET)
	public ResponseEntity<Boolean> delete(@PathVariable Long id) {

		boolean delete = locationService.delete(id);

		return new ResponseEntity<>(delete, HttpStatus.OK);

	}

}

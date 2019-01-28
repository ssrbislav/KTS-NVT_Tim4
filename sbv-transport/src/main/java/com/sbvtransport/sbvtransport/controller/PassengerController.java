package com.sbvtransport.sbvtransport.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sbvtransport.sbvtransport.dto.PassengerChangeBooleanDTO;
import com.sbvtransport.sbvtransport.dto.PassengerDTO;
import com.sbvtransport.sbvtransport.dto.UserDTO;
import com.sbvtransport.sbvtransport.model.Passenger;
import com.sbvtransport.sbvtransport.service.IPassengerService;
 import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
 
@CrossOrigin
@RestController
@RequestMapping(value = "api/passenger")
public class PassengerController {

	@Autowired
	IPassengerService passengerService;
 
	static List<String> files = new ArrayList<String>();

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<List<Passenger>> getAll() {

		List<Passenger> passengers = passengerService.findAll();

		return new ResponseEntity<>(passengers, HttpStatus.OK);
	}


/*
	@RequestMapping(value = "/addPassenger", method = RequestMethod.POST)
	public ResponseEntity<Passenger> create(@RequestBody PassengerDTO passenger) {

		Passenger newPassenger = passengerService.create(passenger);

		return new ResponseEntity<>(newPassenger, HttpStatus.OK);

	}
*/

	@RequestMapping(value = "/getPassengerByID/{id}", method = RequestMethod.GET)
	public ResponseEntity<Passenger> getPassenger(@PathVariable Long id) {
		Passenger passenger = passengerService.getOne(id);

		if(passenger == null) {
			return new ResponseEntity<Passenger>(passenger, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Passenger>(passenger, HttpStatus.OK);
	}

	@RequestMapping(value = "/getPassenger/{username}", method = RequestMethod.GET)
	public ResponseEntity<Passenger> getPassenger(@PathVariable String username) {
		Passenger passenger = passengerService.loadUserByUsername(username);

		if(passenger == null) {
			return new ResponseEntity<Passenger>(passenger, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Passenger>(passenger, HttpStatus.OK);
	}

	@RequestMapping(value = "/updatePassenger/{id}", method = RequestMethod.POST)
	public ResponseEntity<Passenger> update(@RequestBody Passenger passenger, @PathVariable Long id) {

		Passenger updatePassenger = passengerService.update(passenger, id);
		if (updatePassenger == null) {
			return new ResponseEntity<>(updatePassenger, HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(updatePassenger, HttpStatus.OK);

	}

	@RequestMapping(value = "/deletePassenger/{id}", method = RequestMethod.POST)
	public ResponseEntity<Boolean> delete(@PathVariable Long id) {

		boolean delete = passengerService.delete(id);

		if (!delete) {
			return new ResponseEntity<>(delete, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(delete, HttpStatus.OK);

	}

	@RequestMapping(value = "/changeActive", method = RequestMethod.POST)
	public ResponseEntity<String> changeActive(@RequestBody PassengerChangeBooleanDTO user) {

		String changeActive = passengerService.changeActive(user);

		if (!changeActive.equals("Successfully!")) {
			return new ResponseEntity<>(changeActive, HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(changeActive, HttpStatus.OK);

	}
	
	@RequestMapping(value = "/handleFileUpload/{id}", method = RequestMethod.POST)
	public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file, @PathVariable Long id) {
		String message = "";
		try {
			passengerService.store(file, id);
			files.add(file.getOriginalFilename());
 
			message = "You successfully uploaded " + file.getOriginalFilename() + "!";
			return ResponseEntity.status(HttpStatus.OK).body(message);
		} catch (Exception e) {
			message = "FAIL to upload " + file.getOriginalFilename() + "!";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
		}
	}
 
	
	@RequestMapping(value = "/getallfiles/{filename}", method = RequestMethod.GET)
	public ResponseEntity<String> getListFiles(@PathVariable String filename) {
		Resource file = passengerService.loadFile(filename);
		try {

			BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()),1024);
	        StringBuilder stringBuilder = new StringBuilder();
	        String line;
	        while ((line = br.readLine()) != null) {
	            stringBuilder.append(line).append('\n');
	        }
	        br.close();
	        return ResponseEntity.ok().body(stringBuilder.toString());
	        } catch (Exception e) {
	        return ResponseEntity.badRequest().body("");
	    }
		
	}
 
	
//	@GetMapping("/files/{filename:.+}")
//	@ResponseBody
//	public ResponseEntity<Resource> getFile(@PathVariable String filename) {
//		
//		return ResponseEntity.ok()
//				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
//				.body(file);
//	}

}

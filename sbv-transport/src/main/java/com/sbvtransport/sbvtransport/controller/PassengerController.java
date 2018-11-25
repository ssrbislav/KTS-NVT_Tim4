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
import com.sbvtransport.sbvtransport.dto.PassengerChangeBooleanDTO;
import com.sbvtransport.sbvtransport.dto.PassengerDTO;
import com.sbvtransport.sbvtransport.dto.UserDTO;
import com.sbvtransport.sbvtransport.model.Passenger;
import com.sbvtransport.sbvtransport.service.IPassengerService;

@RestController
@RequestMapping(value="api/passenger")
public class PassengerController {

  @Autowired
  IPassengerService passengerService;

  @RequestMapping(value="", method = RequestMethod.GET)
  public ResponseEntity<List<Passenger>> getAll(){

    List <Passenger> passengers = passengerService.findAll();

    return new ResponseEntity<>(passengers,HttpStatus.OK);
  }

  @RequestMapping(value="/addPassenger", method = RequestMethod.POST)
  public ResponseEntity<Passenger> create(@RequestBody PassengerDTO passenger){

    Passenger newPassenger = passengerService.create(passenger);

    return new ResponseEntity<>(newPassenger,HttpStatus.OK);

  }

  @RequestMapping(value="/updatePassenger", method = RequestMethod.POST)
  public ResponseEntity<Passenger> update(@RequestBody Passenger passenger){

    Passenger updatePassenger = passengerService.update(passenger);

    return new ResponseEntity<>(updatePassenger,HttpStatus.OK);

  }

  @RequestMapping(value="/deletePassenger/{id}", method = RequestMethod.GET)
  public ResponseEntity<Boolean> delete(@PathVariable Long id){

    boolean delete = passengerService.delete(id);

    return new ResponseEntity<>(delete,HttpStatus.OK);

  }

  @RequestMapping(value="/logIn", method = RequestMethod.POST)
  public ResponseEntity<Passenger> logIn(@RequestBody UserDTO user){

    Passenger singInPassenger = passengerService.logIn(user);

    return new ResponseEntity<>(singInPassenger,HttpStatus.OK);

  }

  @RequestMapping(value="/changeActive", method = RequestMethod.POST)
  public ResponseEntity<String> changeActive(@RequestBody PassengerChangeBooleanDTO user){

    String changeActive = passengerService.changeActive(user);

    return new ResponseEntity<>(changeActive,HttpStatus.OK);

  }




}

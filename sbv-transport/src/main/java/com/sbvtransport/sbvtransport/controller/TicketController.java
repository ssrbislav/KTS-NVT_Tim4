package com.sbvtransport.sbvtransport.controller;

import com.sbvtransport.sbvtransport.model.Ticket;
import com.sbvtransport.sbvtransport.service.ITicketService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="api/ticket")
public class TicketController {

  @Autowired
  ITicketService ticketService;

  @RequestMapping(value = "", method = RequestMethod.GET)
  public ResponseEntity<List<Ticket>> getAll(){

    List<Ticket> tickets = ticketService.findAll();

    return new ResponseEntity<>(tickets, HttpStatus.OK);
  }

  @RequestMapping(value = "/addTicket", method = RequestMethod.POST)
  public ResponseEntity<Ticket> create(@RequestBody Ticket ticket){

    Ticket newTicket = ticketService.create(ticket);

    return new ResponseEntity<>(newTicket,HttpStatus.OK);

  }

  @RequestMapping(value = "/updateTicket", method = RequestMethod.POST)
  public ResponseEntity<Ticket> update(@RequestBody Ticket ticket){

    Ticket updateTicket = ticketService.update(ticket);

    return new ResponseEntity<>(updateTicket,HttpStatus.OK);

  }

  @RequestMapping(value = "/deleteTicket/{id}", method = RequestMethod.GET)
  public ResponseEntity<Boolean> delete(@PathVariable Long id){

    boolean delete = ticketService.delete(id);

    return new ResponseEntity<>(delete,HttpStatus.OK);

  }
  

}

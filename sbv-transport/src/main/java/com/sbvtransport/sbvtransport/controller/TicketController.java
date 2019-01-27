package com.sbvtransport.sbvtransport.controller;

import com.sbvtransport.sbvtransport.dto.TicketDTO;
import com.sbvtransport.sbvtransport.model.Ticket;
import com.sbvtransport.sbvtransport.service.ITicketService;
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
@RequestMapping(value = "api/ticket")
public class TicketController {

	@Autowired
	ITicketService ticketService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<List<Ticket>> getAll() {

		List<Ticket> tickets = ticketService.findAll();

		return new ResponseEntity<>(tickets, HttpStatus.OK);
	}

  @RequestMapping(value = "/getTicket/{id}", method = RequestMethod.GET)
  public ResponseEntity<Ticket> getOne(@PathVariable Long id) {

    Ticket ticket = ticketService.getOne(id);
    if (ticket == null) {
      return new ResponseEntity<>(ticket, HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<>(ticket, HttpStatus.OK);
  }

	@RequestMapping(value = "/getTickets/{id}", method = RequestMethod.GET)
	public ResponseEntity<List<Ticket>> getByUserID(@PathVariable Long id) {

		List<Ticket> tickets = ticketService.findByUserID(id);
		if (tickets == null || tickets.isEmpty()) {
		  return new ResponseEntity<>(tickets, HttpStatus.NOT_FOUND);
    }

		return new ResponseEntity<>(tickets, HttpStatus.OK);
	}

	@RequestMapping(value = "/addTicket", method = RequestMethod.POST)
	public ResponseEntity<Ticket> create(@RequestBody TicketDTO ticket) {

		Ticket newTicket = ticketService.create(ticket);

		if(newTicket == null){
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(newTicket, HttpStatus.OK);

	}

	@RequestMapping(value = "/updateTicket", method = RequestMethod.POST)
	public ResponseEntity<Ticket> update(@RequestBody Ticket ticket) {

		Ticket updateTicket = ticketService.update(ticket);

    if (updateTicket == null){
      return new ResponseEntity<>(updateTicket, HttpStatus.BAD_REQUEST);
    }

		return new ResponseEntity<>(updateTicket, HttpStatus.OK);

	}

	@RequestMapping(value = "/deleteTicket/{id}", method = RequestMethod.POST)
	public ResponseEntity<Boolean> delete(@PathVariable Long id) {

		boolean delete = ticketService.delete(id);

		if (!delete) {
		  return new ResponseEntity<>(delete, HttpStatus.NOT_FOUND);
    }

		return new ResponseEntity<>(delete, HttpStatus.OK);

	}

	@RequestMapping(value = "/activate/{id}", method = RequestMethod.POST)
	public ResponseEntity<Ticket> activate(@PathVariable Long id) {

		Ticket ticket = ticketService.activate(id);

    if (ticket == null){
      return new ResponseEntity<>(ticket, HttpStatus.BAD_REQUEST);
    }

		return new ResponseEntity<>(ticket, HttpStatus.OK);

	}

}

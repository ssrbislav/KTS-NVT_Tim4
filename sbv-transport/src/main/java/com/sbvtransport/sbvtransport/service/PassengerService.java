package com.sbvtransport.sbvtransport.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sbvtransport.sbvtransport.dto.BuyTicketDTO;
import com.sbvtransport.sbvtransport.dto.UserDTO;
import com.sbvtransport.sbvtransport.enumeration.DemographicTicketType;
import com.sbvtransport.sbvtransport.enumeration.TicketType;
import com.sbvtransport.sbvtransport.enumeration.TypeTransport;
import com.sbvtransport.sbvtransport.enumeration.Zone;
import com.sbvtransport.sbvtransport.model.Passenger;
import com.sbvtransport.sbvtransport.model.Ticket;
import com.sbvtransport.sbvtransport.repository.PassengerRepository;



@Service
public class PassengerService implements IPassengerService {
	
	@Autowired
	PassengerRepository passengerRepository;
	
	@Autowired
	TicketService ticketService;
	

	@Override
	public List<Passenger> findAll() {
		
		return passengerRepository.findAll();
	}

	@Override
	public Passenger create(Passenger passenger) {
		
		for (Passenger pass : findAll()) {
			if(pass.getUsername().equals(passenger.getUsername()) || pass.getEmail().equals(passenger.getEmail())){
				return null;
			}
		}
		//need to create document and save it's path (correct this when you do front)
		
		passenger.setActive(false);
		passenger.setDocument_validated(false);
		
		return passengerRepository.save(passenger);
	}

	@Override
	public Passenger update(Passenger passenger) {
		
		for (Passenger pass : findAll()) {
			if(passenger.getUsername().equals(pass.getUsername()) || passenger.getEmail().equals(pass.getEmail())){
				return null;
			}
		}
		
		Optional<Passenger> updatePassenger = passengerRepository.findById(passenger.getId());
		updatePassenger.get().setActive(passenger.isActive());
		updatePassenger.get().setAddress(passenger.getAddress());
		updatePassenger.get().setDocument(passenger.getDocument());
		updatePassenger.get().setEmail(passenger.getEmail());
		updatePassenger.get().setPhone_number(passenger.getPhone_number());
		updatePassenger.get().setFirst_name(passenger.getFirst_name());
		updatePassenger.get().setLast_name(passenger.getLast_name());
		updatePassenger.get().setUsername(passenger.getUsername());
		updatePassenger.get().setPassword(passenger.getPassword());
		updatePassenger.get().setDocument_validated(passenger.isDocument_validated());
		updatePassenger.get().setTickets(passenger.getTickets());
		
		return passengerRepository.save(updatePassenger.get());
	}

	@Override
	public boolean delete(Long id) {
		for (Passenger passenger : findAll()) {
			if(passenger.getId() == id){
				passengerRepository.delete(passenger);
				return true;
			}
		}
		return false;
	}

	@Override
	public Passenger logIn(UserDTO user) {
		
		for (Passenger passenger : findAll()) {
			if(passenger.getUsername().equals(user.getUsername()) && passenger.getPassword().equals(user.getPassword())){
				return passenger;
			}
		}
		return null;
	}

	
	@Override
	public Passenger buyTicket(BuyTicketDTO ticket) throws ParseException {
		
		Passenger passenger = getOne(ticket.getIdPassenger());
		Date thedate = new SimpleDateFormat("dd-MM-yyyy").parse(ticket.getDate());
		Ticket newTicket = new Ticket(ticket.getId(), TypeTransport.valueOf(ticket.getType_transport()), ticket.getCost(),
				Zone.valueOf(ticket.getZone()), thedate, TicketType.valueOf(ticket.getTicket_type()),false, 
				false,false, DemographicTicketType.valueOf(ticket.getDemographic_type()), 
				false, false, ticket.getCode_transport(), passenger);
		
		ticketService.create(newTicket);		
		
		return passengerRepository.save(passenger);
	}

	@Override
	public Passenger getOne(Long id) {
		
		return passengerRepository.getOne(id);
	}
	
	
	
	
	

}

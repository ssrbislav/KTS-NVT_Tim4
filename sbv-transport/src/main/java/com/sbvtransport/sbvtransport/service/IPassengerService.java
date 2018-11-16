package com.sbvtransport.sbvtransport.service;

import java.text.ParseException;
import java.util.List;

import com.sbvtransport.sbvtransport.dto.BuyTicketDTO;
import com.sbvtransport.sbvtransport.dto.UserDTO;
import com.sbvtransport.sbvtransport.model.Passenger;

public interface IPassengerService {
	
	List<Passenger> findAll();
	Passenger create(Passenger passenger);
	Passenger update(Passenger passenger);
	boolean delete (Long id);
	Passenger logIn(UserDTO user);
	Passenger buyTicket(BuyTicketDTO ticket) throws ParseException;
	Passenger getOne(Long id);

}

package com.sbvtransport.sbvtransport.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sbvtransport.sbvtransport.dto.FilterSearchControllerDTO;
import com.sbvtransport.sbvtransport.enumeration.DemographicTicketType;
import com.sbvtransport.sbvtransport.enumeration.TicketType;
import com.sbvtransport.sbvtransport.model.Controller;
import com.sbvtransport.sbvtransport.model.Passenger;
import com.sbvtransport.sbvtransport.model.Ticket;
import com.sbvtransport.sbvtransport.repository.ControllerRepository;
import com.sbvtransport.sbvtransport.repository.PassengerRepository;
import com.sbvtransport.sbvtransport.repository.TicketRepository;

@Service
public class ControllerService implements IControllerService {

	@Autowired
	ControllerRepository controllerRepository;
	
	@Autowired
	TicketRepository ticketRepository;
	
	@Autowired
	PassengerRepository passengerRepository;

	@Override
	public List<Controller> findAll() {
		List <Controller> notDeleted = new ArrayList<>();
		List<Controller> findAll = controllerRepository.findAll();
		for (Controller controller : findAll) {
			if(!controller.isDeleted()){
				notDeleted.add(controller);
			}
		}
		return notDeleted;
	}
	
	@Override
	public Controller getOne(Long id) {
		return controllerRepository.findById(id).orElse(null);
	}
	
	public Controller loadUserByUsername(String username) {
		
		Controller controller = controllerRepository.findByUsername(username);
				
		return controller;
	}

	@Override
	public Controller create(Controller controller) {
		controller.setDeleted(false);
		return controllerRepository.save(controller);
	}

	@Override
	public Controller update(Controller controller) {

		Controller updateController = controllerRepository.findById(controller.getId()).orElse(null);
		if(updateController != null){
			updateController.setAddress(controller.getAddress());
			updateController.setEmail(controller.getEmail());
			updateController.setPhone_number(controller.getPhone_number());
			updateController.setFirst_name(controller.getFirst_name());
			updateController.setLast_name(controller.getLast_name());
			updateController.setUsername(controller.getUsername());
			updateController.setPassword(controller.getPassword());
			updateController.setDate_birth(controller.getDate_birth());

			return controllerRepository.save(updateController);
			
		}
		
		return null;
		
	}

	@Override
	public boolean delete(Long id) {
		for (Controller controller : findAll()) {
			if (controller.getId() == id) {
				controller.setDeleted(true);
				controllerRepository.save(controller);
				return true;
			}
		}
		return false;
	}

	@Override
	public List<Controller> filterSearch(FilterSearchControllerDTO filterSearch) {
		
		List<Controller> allControllers = findAll();
		List<Controller> finalControllers = new ArrayList<>();
		
		if(filterSearch.getType()!= ""){
			if(filterSearch.getType().equals("username")){
				for (Controller controller : allControllers) {
					if(controller.getUsername().toLowerCase().contains(filterSearch.getText_search().toLowerCase())){
						finalControllers.add(controller);
					}
				}
				
			}else if(filterSearch.getType().equals("email")){
				for (Controller controller : allControllers) {
					if(controller.getEmail().toLowerCase().contains(filterSearch.getText_search().toLowerCase())){
						finalControllers.add(controller);
					}
				}
				
			}else if(filterSearch.getType().equals("fname")){
				for (Controller controller : allControllers) {
					if(controller.getFirst_name().toLowerCase().contains(filterSearch.getText_search().toLowerCase())){
						finalControllers.add(controller);
					}
				}
				
			}else if(filterSearch.getType().equals("lname")){
				for (Controller controller : allControllers) {
					if(controller.getLast_name().toLowerCase().contains(filterSearch.getText_search().toLowerCase())){
						finalControllers.add(controller);
					}
				}
				
			}else if(filterSearch.getType().equals("address")){
				for (Controller controller : allControllers) {
					if(controller.getAddress().toLowerCase().contains(filterSearch.getText_search().toLowerCase())){
						finalControllers.add(controller);
					}
				}
				
			}
			
		}else{
			finalControllers = allControllers;
		}
		
		if(filterSearch.getText_search().equals("")){
			finalControllers = allControllers;
		}
		return finalControllers;
	}

	@Override
	public boolean checkTicket(Long id) {
		
		Ticket ticket = ticketRepository.getOne(id);
		if(ticket != null) {
			
			Passenger passenger = passengerRepository.getOne(ticket.getPassenger().getId());
			
			if(ticket.isActive()) {
				if(ticket.getTicket_type() == TicketType.oneUse) {
						if(checkDate(ticket.getDate(), new Date())) {
							return true;
						}
				} else if(ticket.getTicket_type() == TicketType.daily) {
					if(checkDateDaily(ticket.getDate_purchase(), new Date())) {
								return true;			
					}
				} else if(ticket.getTicket_type() == TicketType.monthly) {
					if(checkDateMonthly(ticket.getDate_purchase(), new Date())) {
						return true;
					}
				} else {
					if(ticket.getDemographic_type() != DemographicTicketType.standard) {					
						if(passenger.getDocument() != null) {
							if(passenger.isDocument_validated()) {
								return true;
							}
						}						
					} else {
						// Ako je expired svakako nece ni uci ovde, kao ni bilo gde iznad :D
							return true;
					}
				}
			}
		}
		
		return false;
	}
	
	public boolean checkDate(Date activation, Date date) {

		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(activation);
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(date);

		if (calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)
				&& calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH)
				&& calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH)) {
			if(calendar1.get(Calendar.HOUR_OF_DAY) == calendar2.get(Calendar.HOUR_OF_DAY) || 
					calendar1.get(Calendar.HOUR_OF_DAY) == calendar2.get(Calendar.HOUR_OF_DAY -2)) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean checkDateDaily(Date activation, Date date) {
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(activation);
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(date);
		
		if (calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)
				&& calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH)
				&& calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH)) {
			return true;
		}
		
		return false;
	}
	
	public boolean checkDateMonthly(Date activation, Date date) {
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(activation);
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(date);
		
		if (calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)
				&& calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH)) {
			return true;
		}
		
		return false;
	}

}

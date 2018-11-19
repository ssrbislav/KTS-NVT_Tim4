package com.sbvtransport.sbvtransport.service;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sbvtransport.sbvtransport.model.Controller;
import com.sbvtransport.sbvtransport.model.Passenger;
import com.sbvtransport.sbvtransport.repository.ControllerRepository;
import com.sbvtransport.sbvtransport.repository.PassengerRepository;

@Service
public class ControllerService implements IControllerService {

	@Autowired
	ControllerRepository controllerRepository;
	
	@Autowired
	PassengerRepository paasengerRepository;
	
	@Override
	public List<Controller> findAll() {
		
		return controllerRepository.findAll();
	}

	@Override
	public Controller create(Controller controller) {
		
		return controllerRepository.save(controller);
	}

	@Override
	public Controller update(Controller controller) {
		
		Optional<Controller> updateController = controllerRepository.findById(controller.getId());
		updateController.get().setAddress(controller.getAddress());
		updateController.get().setEmail(controller.getEmail());
		updateController.get().setPhone_number(controller.getPhone_number());
		updateController.get().setFirst_name(controller.getFirst_name());
		updateController.get().setLast_name(controller.getLast_name());
		updateController.get().setUsername(controller.getUsername());
		updateController.get().setPassword(controller .getPassword());
		return controllerRepository.save(updateController.get());
	}

	@Override
	public boolean delete(Long id) {
		for (Controller controller : findAll()) {
			if(controller.getId() == id){
				controllerRepository.delete(controller);
				return true;
			}	
		}
		return false;
	}

	@Override
	public Passenger validatePassengerDocument(Long passengerId) {
		Passenger passenger = paasengerRepository.getOne(passengerId);
		
		Date dateOfUpload = passenger.getDocument().getDateOfUpload();
		
		LocalDateTime now = LocalDateTime.now();
		int year = now.getYear();
		int month = now.getMonthValue();
		
		Date previousYear = getDate(year-1, month);
		Date thisYear = getDate(year, month);
			
		if(passenger.getDocument() != null) {
			if(month > 9) {
				if (dateOfUpload.after(thisYear))
					if (passenger.isDocument_validated() == false) {
						passenger.setDocument_validated(true);
						return passenger;
					}
			}
			if(month <= 9) {
				if (dateOfUpload.after(previousYear)) 
					if (passenger.isDocument_validated() == false) {
						passenger.setDocument_validated(true);
						return passenger;
					}
			}
			else {
				System.out.println("Dokument nije validan");;
			}
			
		}
		System.out.println("Ne postoji dokument");
		return null;
		
	}
	
	//Set date for check
	public Date getDate(int year, int month) {
		Calendar time = Calendar.getInstance();
	    time.clear();

	    time.set(Calendar.YEAR, year);
	    time.set(Calendar.MONTH, 10);
	    time.set(Calendar.DATE, 1);
		
	    Date date = time.getTime();
	    
	    return date;
	}

}

package com.sbvtransport.sbvtransport.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sbvtransport.sbvtransport.model.Passenger;
import com.sbvtransport.sbvtransport.repository.PassengerRepository;

@Service
public class PassengerService implements IPassengerService {
	
	@Autowired
	PassengerRepository passengerRepository;

	@Override
	public List<Passenger> findAll() {
		
		return passengerRepository.findAll();
	}

	@Override
	public Passenger create(Passenger passenger) {
		//need to create document and save it's path (correct this when you do front)
		passenger.setDocument(" ");
		
		passenger.setActive(false);
		passenger.setValidate_document(false);
		
		return passengerRepository.save(passenger);
	}

	@Override
	public Passenger update(Passenger passenger) {
		
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
		updatePassenger.get().setValidate_document(passenger.isValidate_document());
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
	
	
	
	
	

}

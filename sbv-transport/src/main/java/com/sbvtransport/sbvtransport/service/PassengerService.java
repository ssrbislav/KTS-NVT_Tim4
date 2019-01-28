package com.sbvtransport.sbvtransport.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter.FixedSpaceIndenter;
import com.sbvtransport.sbvtransport.dto.DocumentDTO;
import com.sbvtransport.sbvtransport.dto.PassengerChangeBooleanDTO;
import com.sbvtransport.sbvtransport.dto.PassengerDTO;
import com.sbvtransport.sbvtransport.dto.UserDTO;
import com.sbvtransport.sbvtransport.model.Passenger;
import com.sbvtransport.sbvtransport.model.Ticket;
import com.sbvtransport.sbvtransport.repository.PassengerRepository;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PassengerService implements IPassengerService {

	@Autowired
	PassengerRepository passengerRepository;

	@Autowired
	TicketService ticketService;
	
	@Autowired
	DocumentService documentService;
	
	@Autowired
	PasswordEncoder encoder;
	
	Logger log = LoggerFactory.getLogger(this.getClass().getName());
	private final Path rootLocation = Paths.get("upload-dir");
 

	@Override
	public List<Passenger> findAll() {

		return passengerRepository.findAll();
	}

	public Passenger loadUserByUsername(String username) {

		Passenger passenger = passengerRepository.findByUsername(username);

		return passenger;
	}

	@Override
	public Passenger create(PassengerDTO passenger) {

		for (Passenger pass : findAll()) {
			if (pass.getUsername().equals(passenger.getUsername()) || pass.getEmail().equals(passenger.getEmail())) {
				return null;
			}
		}
		// need to create document and save it's path (correct this when you do
		// front)

		Passenger p = new Passenger(true, false, passenger.getEmail(), passenger.getUsername(), passenger.getPassword(),
				passenger.getFirst_name(), passenger.getLast_name(), passenger.getAddress(),
				passenger.getPhone_number(), passenger.getDate_birth());

		return passengerRepository.save(p);
	}

	@Override
	public Passenger update(Passenger passenger, Long id) {

//		for (Passenger pass : findAll()) {
//			if (passenger.getUsername().equals(pass.getUsername()) || passenger.getEmail().equals(pass.getEmail())) {
//				return null;
//			}
//		}

		System.out.println(id);
		Optional<Passenger> updatePassenger = passengerRepository.findById(id);
		updatePassenger.get().setActive(passenger.isActive());
		updatePassenger.get().setAddress(passenger.getAddress());
		updatePassenger.get().setDocument(passenger.getDocument());
		updatePassenger.get().setEmail(passenger.getEmail());
		updatePassenger.get().setPhone_number(passenger.getPhone_number());
		updatePassenger.get().setFirst_name(passenger.getFirst_name());
		updatePassenger.get().setLast_name(passenger.getLast_name());
		updatePassenger.get().setUsername(passenger.getUsername());
		updatePassenger.get().setPassword(encoder.encode(passenger.getPassword()));
		updatePassenger.get().setDocument_validated(passenger.isDocument_validated());
		updatePassenger.get().setTickets(passenger.getTickets());
		updatePassenger.get().setId(id);
		passengerRepository.save(updatePassenger.get());
		return updatePassenger.get();
	}

	@Override
	public boolean delete(Long id) {
		for (Passenger passenger : findAll()) {
			if (passenger.getId() == id) {
				passengerRepository.delete(passenger);
				return true;
			}
		}
		return false;
	}

	@Override
	public Passenger logIn(UserDTO user) {

		for (Passenger passenger : findAll()) {
			if (passenger.getUsername().equals(user.getUsername())
					&& passenger.getPassword().equals(user.getPassword())) {
				return passenger;
			}
		}
		return null;
	}

	@Override
	public Passenger getOne(Long id) {

		return passengerRepository.findById(id).orElse(null);
	}

	@Override
	public String changeActive(PassengerChangeBooleanDTO pass) {
		Passenger p = getOne(pass.getIdPassenger());
		if (p == null) {
			return "Passenger with" + pass.getIdPassenger() + "doesn't exist!";
		}
		p.setActive(pass.isChange());
		passengerRepository.save(p);
		return "Successfully!";
	}

	public String activateOneTimeTicket(Long ticketId) {

		Ticket ticket;

		TimeScheduler timeScheduler = new TimeScheduler();

		for (Passenger passenger : findAll()) {
			if (passenger.getTickets().contains(ticketService.getOne(ticketId))) {
				ticket = ticketService.getOne(ticketId);
				ticket.setActive(true);
				ticketService.update(ticket);
				timeScheduler.changeActiveForOneUse(ticketId);
				return "Your ticket has been activated";
			}
		}
		return "You are not able to activate this ticket";
	}
	
	@Override
	public void store(MultipartFile file, Long id) {
		Passenger p = passengerRepository.findById(id).orElse(null);
		if(p!= null){
			try {
				System.out.println(file.getOriginalFilename());
				
				Files.copy(file.getInputStream(), this.rootLocation.resolve("document_" + id + "_" +file.getOriginalFilename()));
				DocumentDTO d = new DocumentDTO(new Date(), "document_" + id + "_" +file.getOriginalFilename(), p.getId());
				documentService.create(d);
				System.out.println("USloooooo");
			} catch (Exception e) {
				throw new RuntimeException("FAIL!");
			}
			
		}
		
	}
 
	@Override
	public Resource loadFile(String filename) {
		try {
			System.out.println(filename);
			Path file = rootLocation.resolve(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new RuntimeException("FAIL!");
			}
		} catch (MalformedURLException e) {
			throw new RuntimeException("FAIL!");
		}
	}
 
	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(rootLocation.toFile());
	}
	
	@Override
	public void init() {
		try {
			Files.createDirectory(rootLocation);
		} catch (IOException e) {
			throw new RuntimeException("Could not initialize storage!");
		}
	}
}
	
	


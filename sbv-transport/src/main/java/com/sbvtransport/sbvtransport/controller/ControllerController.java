package com.sbvtransport.sbvtransport.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sbvtransport.sbvtransport.dto.FilterSearchControllerDTO;
import com.sbvtransport.sbvtransport.dto.RegisterDTO;
import com.sbvtransport.sbvtransport.enumeration.DemographicTicketType;
import com.sbvtransport.sbvtransport.enumeration.RoleName;
import com.sbvtransport.sbvtransport.enumeration.TicketType;
import com.sbvtransport.sbvtransport.messages.ResponseMessage;
import com.sbvtransport.sbvtransport.model.Controller;
import com.sbvtransport.sbvtransport.model.Role;
import com.sbvtransport.sbvtransport.model.Ticket;
import com.sbvtransport.sbvtransport.model.User;
import com.sbvtransport.sbvtransport.repository.RoleRepository;
import com.sbvtransport.sbvtransport.repository.UserRepository;
import com.sbvtransport.sbvtransport.security.JwtProvider;
import com.sbvtransport.sbvtransport.service.IControllerService;
import com.sbvtransport.sbvtransport.service.TicketService;
@CrossOrigin
@RestController
@RequestMapping(value = "api/controller")
public class ControllerController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtProvider jwtProvider;

	@Autowired
	IControllerService controllerService;


	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<List<Controller>> getAll() {

		List<Controller> controllers = controllerService.findAll();

		return new ResponseEntity<>(controllers, HttpStatus.OK);
	}

	
	@RequestMapping(value = "/getController/{username}", method = RequestMethod.GET)
	public ResponseEntity<Controller> getController(@PathVariable String username) {
		Controller controller = controllerService.loadUserByUsername(username);
		
		if(controller == null) 
			return new ResponseEntity<Controller>(controller, HttpStatus.BAD_REQUEST);
		
		return new ResponseEntity<Controller>(controller, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/updateController", method = RequestMethod.POST)
	public ResponseEntity<Controller> update(@RequestBody Controller controller) {

		Controller updateController = controllerService.update(controller);
		
		if(updateController == null){
			return new ResponseEntity<>(updateController, HttpStatus.BAD_REQUEST);

		}

		return new ResponseEntity<>(updateController, HttpStatus.OK);

	}
	
//	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/deleteController/{id}", method = RequestMethod.GET)
	public ResponseEntity<Boolean> delete(@PathVariable Long id) {

		boolean delete = controllerService.delete(id);

		return new ResponseEntity<>(delete, HttpStatus.OK);
	}

//	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/addController")
	public ResponseEntity<?> registerController(@Valid @RequestBody RegisterDTO signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return new ResponseEntity<>(new ResponseMessage("Fail -> Username is already taken!"),
					HttpStatus.BAD_REQUEST);
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return new ResponseEntity<>(new ResponseMessage("Fail -> Email is already in use!"),
					HttpStatus.BAD_REQUEST);
		}

		// Creating controller's account
		User user = new Controller(signUpRequest.getEmail(), signUpRequest.getUsername(),
				encoder.encode(signUpRequest.getPassword()), signUpRequest.getFirst_name(),
				signUpRequest.getLast_name(), signUpRequest.getAddress(), signUpRequest.getPhone_number(),
				signUpRequest.getDate_birth());

		Role role = new Role();
		role.setName(RoleName.ROLE_CONTROLLER);

		Set<Role> roles = new HashSet<>();
		roles.add(role);

		user.setRoles(roles);
		userRepository.save(user);

		return new ResponseEntity<>(new ResponseMessage("Controller registered successfully!"), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/searchFilter", method = RequestMethod.POST)
	public ResponseEntity<List<Controller>> filterSearch(@RequestBody FilterSearchControllerDTO filterSearch) {
		
		List<Controller> list = controllerService.filterSearch(filterSearch);

		return new ResponseEntity<>(list, HttpStatus.OK);

	}
	
	@RequestMapping(value = "/checkTicket/{id}", method = RequestMethod.GET)
	public ResponseEntity<Boolean> checkTicket(@PathVariable Long id) {
		
		boolean checked = controllerService.checkTicket(id);
		
		return new ResponseEntity<>(checked, HttpStatus.OK);
	}
}

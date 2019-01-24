package com.sbvtransport.sbvtransport.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sbvtransport.sbvtransport.dto.FilterSearchControllerDTO;
import com.sbvtransport.sbvtransport.model.Controller;
import com.sbvtransport.sbvtransport.repository.ControllerRepository;

@Service
public class ControllerService implements IControllerService {

	@Autowired
	ControllerRepository controllerRepository;

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

		Optional<Controller> updateController = controllerRepository.findById(controller.getId());
		updateController.get().setAddress(controller.getAddress());
		updateController.get().setEmail(controller.getEmail());
		updateController.get().setPhone_number(controller.getPhone_number());
		updateController.get().setFirst_name(controller.getFirst_name());
		updateController.get().setLast_name(controller.getLast_name());
		updateController.get().setUsername(controller.getUsername());
		updateController.get().setPassword(controller.getPassword());
		return controllerRepository.save(updateController.get());
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

}

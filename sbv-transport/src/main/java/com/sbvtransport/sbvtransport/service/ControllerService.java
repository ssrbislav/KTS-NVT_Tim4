package com.sbvtransport.sbvtransport.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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

}

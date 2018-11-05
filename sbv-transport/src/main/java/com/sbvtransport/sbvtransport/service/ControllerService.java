package com.sbvtransport.sbvtransport.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sbvtransport.sbvtransport.model.Controller;
import com.sbvtransport.sbvtransport.repository.ControllerRepository;

@Service
public class ControllerService implements IControllerService {

	@Autowired
	ControllerRepository controllerRepository;
	
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

}

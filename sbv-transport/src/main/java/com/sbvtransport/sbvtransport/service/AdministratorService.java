package com.sbvtransport.sbvtransport.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sbvtransport.sbvtransport.model.Administrator;
import com.sbvtransport.sbvtransport.repository.AdministratorRepository;

@Service
public class AdministratorService implements IAdministratorService {

	@Autowired
	AdministratorRepository administratorRepository;

	@Override
	public List<Administrator> findAll() {

		return administratorRepository.findAll();
	}

	@Override
	public Administrator create(Administrator administrator) {

		return administratorRepository.save(administrator);
	}

	@Override
	public Administrator update(Administrator administrator) {

		Optional<Administrator> updateAdmin = administratorRepository.findById(administrator.getId());
		updateAdmin.get().setPassword(administrator.getPassword());
		updateAdmin.get().setUsername(administrator.getUsername());
		return administratorRepository.save(updateAdmin.get());
	}

	@Override
	public boolean delete(Long id) {
		for (Administrator admin : findAll()) {
			if (admin.getId() == id) {
				administratorRepository.delete(admin);
				return true;
			}
		}
		return false;
	}

}

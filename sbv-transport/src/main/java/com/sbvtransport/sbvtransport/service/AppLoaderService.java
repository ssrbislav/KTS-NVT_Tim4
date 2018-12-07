package com.sbvtransport.sbvtransport.service;

import java.util.Date;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.sbvtransport.sbvtransport.model.Administrator;
import com.sbvtransport.sbvtransport.repository.AdministratorRepository;

@Component
public class AppLoaderService implements ApplicationRunner {

	private AdministratorRepository adminRepository;

	public AppLoaderService(AdministratorRepository adminRepository) {
		this.adminRepository = adminRepository;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		if (adminRepository.findAll().size() == 0) {
			// BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

			Administrator admin = new Administrator();
			admin.setUsername("admin");
			admin.setPassword("admin");
			admin.setAddress("");
			admin.setEmail("");
			admin.setFirst_name("");
			admin.setLast_name("");
			admin.setPhone_number("");
			admin.setDate_birth(new Date());

			adminRepository.save(admin);
			System.out.println("Admin added.");
		}

	}
}

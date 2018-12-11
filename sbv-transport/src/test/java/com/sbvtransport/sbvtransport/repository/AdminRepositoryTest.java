package com.sbvtransport.sbvtransport.repository;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.sbvtransport.sbvtransport.model.Administrator;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class AdminRepositoryTest {

	@Autowired
	TestEntityManager manager;
	
	@Autowired
	private AdministratorRepository repository;
	
	@Test
	public void testIfAdminExists() {
		
		Administrator admin = new Administrator();
		admin.setUsername("admin2");
		admin.setPassword("pass");
		admin.setAddress("");
		admin.setEmail("");
		admin.setFirst_name("");
		admin.setLast_name("");
		admin.setPhone_number("");
		admin.setDate_birth(new Date());

		repository.save(admin);
		
		Optional<Administrator> findAdmin = repository.findById(admin.getId());
		
		assertEquals(admin.getId(), findAdmin.get().getId());
		
	}
	
}

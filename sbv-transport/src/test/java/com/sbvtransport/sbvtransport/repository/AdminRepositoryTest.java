package com.sbvtransport.sbvtransport.repository;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.sbvtransport.sbvtransport.model.Administrator;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
public class AdminRepositoryTest {

	@Autowired
	private AdministratorRepository repository;

	@Test
	@Transactional
	@Rollback(true)
	public void testIfAdminExists() {
		
		//DA LI DA DODAM
		//ROLE U SETER METODE KOD KORISNIKA
		// PA DA SE I KOD TESTOVA ODMAH MOZE
		//PROVERITI DA LI SU ODGOVARAJUCE ROLE PODESENE NA 
		//ODGOVARAJUCE KORISNIKE

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
		/*
		 * boolean maybe = false; for(Role r : admin.getRoles()) { if (r.getName() ==
		 * RoleName.ROLE_ADMIN) { maybe = true; } } assertEquals(true, maybe);
		 */
	}

	@Test(expected = DataIntegrityViolationException.class)
	public void testSaveEmptyAdmin() {
		repository.save(new Administrator());
	}

}

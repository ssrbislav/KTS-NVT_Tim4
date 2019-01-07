package com.sbvtransport.sbvtransport.repository;

import static org.junit.Assert.assertEquals;

import java.util.Date;

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

import com.sbvtransport.sbvtransport.model.Passenger;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
public class PassengerRepositoryTest {

	@Autowired
	private PassengerRepository repository;
	
	@Test
	@Transactional
	@Rollback(true)
	public void testIfPassengerExists() {
		
		Passenger passenger = new Passenger();
		passenger.setUsername("putnik");
		passenger.setPassword("password");
		passenger.setAddress("Adresa putnika");
		passenger.setEmail("mail@email.com");
		passenger.setFirst_name("Putnik");
		passenger.setLast_name("Prezivase");
		passenger.setPhone_number("06665452");
		passenger.setDate_birth(new Date());
		
		repository.save(passenger);
		
		Passenger findPassenger = repository.getOne(passenger.getId());
		
		assertEquals(passenger.getId(), findPassenger.getId());
	}
	
	@Test(expected = DataIntegrityViolationException.class)
	public void testSaveEmptyPassenger() {
		repository.save(new Passenger());
	}
	
}

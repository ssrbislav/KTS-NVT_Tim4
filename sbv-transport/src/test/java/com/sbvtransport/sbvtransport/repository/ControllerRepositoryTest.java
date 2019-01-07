package com.sbvtransport.sbvtransport.repository;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.sbvtransport.sbvtransport.model.Controller;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
public class ControllerRepositoryTest {

	@Autowired
	private ControllerRepository repository;
	
	@Test
	@Transactional
	@Rollback(true)
	public void testIfControllerExists() {
		
		Controller controller = new Controller();
		controller.setUsername("controller");
		controller.setEmail("email@gmail.com");
		controller.setPassword("password");
		controller.setFirst_name("ImeKontrolora");
		controller.setLast_name("Prezime");
		controller.setAddress("Neka adresa");
		controller.setPhone_number("0604566258");
		controller.setDate_birth(new Date());
		
		repository.save(controller);
		
		Controller findController = repository.getOne(controller.getId());
		
		assertEquals(controller.getId(), findController.getId());
		
	}
	
	@Test(expected = DataIntegrityViolationException.class)
	public void testSaveEmptyController() {
		repository.save(new Controller());
	}
	
	
}

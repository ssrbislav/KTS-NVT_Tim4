package com.sbvtransport.sbvtransport.repository;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.sbvtransport.sbvtransport.model.Controller;
import com.sbvtransport.sbvtransport.model.User;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
public class UserRepositoryTest {

	@Autowired
	private UserRepository repository;

	Controller user;
	
	@Before
	public void initialize() {
		user = new Controller("email@gmail.com", "kontroler", "password", "", "", "", "", new Date());
		repository.save(user);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testfindByUsername() {
		
		User findController = repository.getOne(user.getId());
		assertEquals("kontroler", findController.getUsername());
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testExistsByUsername() {

		User findController = repository.getOne(user.getId());
		assertEquals(user.getUsername(), findController.getUsername());
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testExistsByEmail() {

		User findController = repository.getOne(user.getId());
		assertEquals("email@gmail.com", findController.getEmail());
	}

}

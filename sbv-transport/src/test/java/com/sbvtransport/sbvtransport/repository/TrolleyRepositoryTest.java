package com.sbvtransport.sbvtransport.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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
import com.sbvtransport.sbvtransport.model.Transport;
import com.sbvtransport.sbvtransport.model.Trolley;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
public class TrolleyRepositoryTest {
	
	@Autowired
	private TrolleyRepository trolleyRepository;
	
	@Autowired
	private LineRepository lineRepository;

	@Test
	@Transactional
	@Rollback(true)
	public void testSaveTrolley(){
//		Transport t = new Trolley("nova_linija_trolley_12ca",lineRepository.getOne(3L), false, "12ca");
//		Trolley trolleySaved = trolleyRepository.save(t);
//		assertEquals(t.getName(), trolleySaved.getName());
//		assertEquals(t.getLine().getId(), trolleySaved.getLine().getId());
//		assertEquals(t.getLine().getLine_type(), trolleySaved.getLine().getLine_type());
//		assertEquals(t.getLine().getName(), trolleySaved.getLine().getName());
//		assertEquals("nova_linija_trolley_12ca", trolleySaved.getCode());
//		assertNotNull(trolleySaved);
	}

	//trying to save empty object
	@Test(expected = DataIntegrityViolationException.class)
	public void testSaveTrolley2(){
		trolleyRepository.save(new Trolley());
	}

}

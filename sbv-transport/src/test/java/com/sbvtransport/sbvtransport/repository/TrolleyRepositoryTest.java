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
@Rollback(value=true)
public class TrolleyRepositoryTest {
	
	@Autowired
	private TrolleyRepository trolleyRepository;
	
	@Autowired
	private LineRepository lineRepository;
	
	@Autowired
	private LocationRepository locationRepository;

	@Test
	@Transactional
	@Rollback(value=true)
	public void testSaveTrolley(){
		Transport t = new Trolley(lineRepository.getOne(1L), false, "lasta", locationRepository.getOne(1L), 5,
				"7ca_trolley_lasta");
		Trolley trolleySaved = trolleyRepository.save(t);
		assertEquals(t.getName(), trolleySaved.getName());
		assertEquals(t.getLine().getId(), trolleySaved.getLine().getId());
		assertEquals(t.getLine().getLine_type(), trolleySaved.getLine().getLine_type());
		assertEquals(t.getLine().getName(),trolleySaved.getLine().getName());
		assertEquals(t.getLine().getStation_list(), trolleySaved.getLine().getStation_list());
		assertEquals(t.getLine().getName(), trolleySaved.getLine().getName());
		assertEquals(t.getLine().getZone(), trolleySaved.getLine().getZone());
		assertEquals(t.getLocation().getId(), trolleySaved.getLocation().getId());
		assertEquals(t.getLocation().getAddress(), trolleySaved.getLocation().getAddress());
		assertEquals(t.getLocation().getLatitude(), trolleySaved.getLocation().getLatitude());
		assertEquals(t.getLocation().getLocation_name(), trolleySaved.getLocation().getLocation_name());
		assertEquals(t.getLocation().getLongitude(), trolleySaved.getLocation().getLongitude());
		assertEquals(t.getLocation().getType(), trolleySaved.getLocation().getType());
		assertEquals("7ca_trolley_lasta", trolleySaved.getCode());
		assertEquals(t.getTime_arrive(), trolleySaved.getTime_arrive());
		assertEquals(t.isLate(), trolleySaved.isLate());
		assertNotNull(trolleySaved);
	}

	//trying to save empty object
	@Test(expected = DataIntegrityViolationException.class)
	public void testSaveTrolley2(){
		trolleyRepository.save(new Trolley());
	}

}

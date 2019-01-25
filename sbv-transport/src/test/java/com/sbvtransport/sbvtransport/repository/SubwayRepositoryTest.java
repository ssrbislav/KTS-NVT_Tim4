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
import com.sbvtransport.sbvtransport.model.Subway;
import com.sbvtransport.sbvtransport.model.Transport;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
@Rollback(value=true)
public class SubwayRepositoryTest {

	@Autowired
	private SubwayRepository subwayRepository;

	@Autowired
	private LineRepository lineRepository;
	
	@Autowired
	private LocationRepository locationRepository;

	@Test
	@Transactional
	@Rollback(value=true)
	public void testSaveSubway() {
		Transport t = new Subway(lineRepository.getOne(2L), false, "lasta", locationRepository.getOne(1L), 5,
				"7ca_subway_lasta");
		Subway subwaySaved = subwayRepository.save(t);
		assertEquals(t.getName(), subwaySaved.getName());
		assertEquals(t.getLine().getId(),subwaySaved.getLine().getId());
		assertEquals(t.getLine().getLine_type(), subwaySaved.getLine().getLine_type());
		assertEquals(t.getLine().getName(), subwaySaved.getLine().getName());
		assertEquals(t.getLine().getStation_list(), subwaySaved.getLine().getStation_list());
		assertEquals(t.getLine().getName(), subwaySaved.getLine().getName());
		assertEquals(t.getLine().getZone(), subwaySaved.getLine().getZone());
		assertEquals(t.getLocation().getId(), subwaySaved.getLocation().getId());
		assertEquals(t.getLocation().getAddress(), subwaySaved.getLocation().getAddress());
		assertEquals(t.getLocation().getLatitude(), subwaySaved.getLocation().getLatitude());
		assertEquals(t.getLocation().getLocation_name(), subwaySaved.getLocation().getLocation_name());
		assertEquals(t.getLocation().getLongitude(), subwaySaved.getLocation().getLongitude());
		assertEquals(t.getLocation().getType(), subwaySaved.getLocation().getType());
		assertEquals("7ca_subway_lasta", subwaySaved.getCode());
		assertEquals(t.getTime_arrive(), subwaySaved.getTime_arrive());
		assertEquals(t.isLate(), subwaySaved.isLate());
		assertNotNull(subwaySaved);
	}

	// trying to save empty object
	@Test(expected = DataIntegrityViolationException.class)
	public void testSaveSubway2() {
		subwayRepository.save(new Subway());
	}

}

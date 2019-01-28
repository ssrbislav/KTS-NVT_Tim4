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
import com.sbvtransport.sbvtransport.model.Bus;
import com.sbvtransport.sbvtransport.model.Transport;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
@Rollback(value=true)
public class BusRepositoryTest {

	@Autowired
	private BusRepository busRepository;

	@Autowired
	private LineRepository lineRepository;

	@Autowired
	private LocationRepository locationRepository;

	@Test
	@Transactional
	@Rollback(value=true)
	public void testSaveBus() {
		Transport t = new Bus(lineRepository.getOne(1L), false, "lasta", locationRepository.getOne(1L), 5,
				"7ca_bus_lasta");
		Bus busSaved = busRepository.save(t);
		assertEquals(t.getName(), busSaved.getName());
		assertEquals(t.getLine().getId(), busSaved.getLine().getId());
		assertEquals(t.getLine().getLine_type(), busSaved.getLine().getLine_type());
		assertEquals(t.getLine().getName(), busSaved.getLine().getName());
		assertEquals(t.getLine().getStation_list(), busSaved.getLine().getStation_list());
		assertEquals(t.getLine().getName(), busSaved.getLine().getName());
		assertEquals(t.getLine().getZone(), busSaved.getLine().getZone());
		assertEquals(t.getLocation().getId(), busSaved.getLocation().getId());
		assertEquals(t.getLocation().getAddress(), busSaved.getLocation().getAddress());
		assertEquals(t.getLocation().getLatitude(), busSaved.getLocation().getLatitude());
		assertEquals(t.getLocation().getLocation_name(), busSaved.getLocation().getLocation_name());
		assertEquals(t.getLocation().getLongitude(), busSaved.getLocation().getLongitude());
		assertEquals(t.getLocation().getType(), busSaved.getLocation().getType());
		assertEquals("7ca_bus_lasta", busSaved.getCode());
		assertEquals(t.getTime_arrive(), busSaved.getTime_arrive());
		assertEquals(t.isLate(), busSaved.isLate());
		assertNotNull(busSaved);
	}

	// trying to save empty object
	@Test(expected = DataIntegrityViolationException.class)
	public void testSaveBus2() {
		busRepository.save(new Bus());
	}

}

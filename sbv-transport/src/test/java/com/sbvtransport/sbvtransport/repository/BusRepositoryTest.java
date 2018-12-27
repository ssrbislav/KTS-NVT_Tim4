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
public class BusRepositoryTest {

	@Autowired
	private BusRepository busRepository;

	@Autowired
	private LineRepository lineRepository;
	
	@Test
	@Transactional
	@Rollback(true)
	public void testSaveBus(){
		Transport t = new Bus("nova_linija_bus_7ca",lineRepository.getOne(1L), false, "7ca");
		Bus busSaved = busRepository.save(t);
		assertEquals(t.getName(), busSaved.getName());
		assertEquals(t.getLine().getId(), busSaved.getLine().getId());
		assertEquals(t.getLine().getLine_type(), busSaved.getLine().getLine_type());
		assertEquals(t.getLine().getName(), busSaved.getLine().getName());
		assertEquals("nova_linija_bus_7ca", busSaved.getCode());
		assertNotNull(busSaved);
	}

	//trying to save empty object
	@Test(expected = DataIntegrityViolationException.class)
	public void testSaveBus2(){
		busRepository.save(new Bus());
	}



}

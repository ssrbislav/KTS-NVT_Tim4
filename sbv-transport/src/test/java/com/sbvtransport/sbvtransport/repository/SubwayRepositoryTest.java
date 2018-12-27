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
public class SubwayRepositoryTest {

	@Autowired
	private SubwayRepository subwayRepository;

	@Autowired
	private LineRepository lineRepository;

	@Test
	@Transactional
	@Rollback(true)
	public void testSaveSubway() {
		Transport t = new Subway("nova_linija_subway_12ca", lineRepository.getOne(2L), false, "12ca");
		Subway subwaySaved = subwayRepository.save(t);
		assertEquals(t.getName(), subwaySaved.getName());
		assertEquals(t.getLine().getId(), subwaySaved.getLine().getId());
		assertEquals(t.getLine().getLine_type(), subwaySaved.getLine().getLine_type());
		assertEquals(t.getLine().getName(), subwaySaved.getLine().getName());
		assertEquals("nova_linija_subway_12ca", subwaySaved.getCode());
		assertNotNull(subwaySaved);
	}

	// trying to save empty object
	@Test(expected = DataIntegrityViolationException.class)
	public void testSaveSubway2() {
		subwayRepository.save(new Subway());
	}

}

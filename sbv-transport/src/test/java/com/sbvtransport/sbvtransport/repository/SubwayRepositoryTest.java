package com.sbvtransport.sbvtransport.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import com.sbvtransport.sbvtransport.enumeration.TypeTransport;
import com.sbvtransport.sbvtransport.model.Line;
import com.sbvtransport.sbvtransport.model.Subway;
import com.sbvtransport.sbvtransport.model.Transport;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class SubwayRepositoryTest {
	
	@Autowired
	SubwayRepository subwayRepository;
	
	@MockBean
	LineRepository lineRepositoryMocked;

	@Before
	public void setUp(){
		Line l = new Line("nova_linija", TypeTransport.subway);
		Optional<Line> line = Optional.of(l);
		Mockito.when(lineRepositoryMocked.findById(1L)).thenReturn(line);
	}
	
	@Test
	public void testSaveSubway(){
		Transport t = new Subway("nova_linija_subway_11ca",lineRepositoryMocked.findById(1L).get(), false, "11ca");
		Subway subwaySaved = subwayRepository.save(t);
		assertEquals(t, subwaySaved);
		assertNotNull(subwaySaved);
	}

	//trying to save empty object
	@Test(expected = DataIntegrityViolationException.class)
	public void testSaveSubway2(){
		subwayRepository.save(new Subway());
	}

}

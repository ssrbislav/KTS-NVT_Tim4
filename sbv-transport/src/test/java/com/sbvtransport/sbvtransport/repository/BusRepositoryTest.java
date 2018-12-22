package com.sbvtransport.sbvtransport.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
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
import com.sbvtransport.sbvtransport.model.Bus;
import com.sbvtransport.sbvtransport.model.Line;
import com.sbvtransport.sbvtransport.model.Transport;
import com.sbvtransport.sbvtransport.model.Trolley;


@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class BusRepositoryTest {


	@Autowired
	BusRepository busRepository;

	@MockBean
	LineRepository lineRepositoryMocked;

	@Before
	public void setUp(){
		Line l = new Line("nova_linija", TypeTransport.bus);
		Optional<Line> line = Optional.of(l);
		Mockito.when(lineRepositoryMocked.findById(1L)).thenReturn(line);
	}

	@Test
	public void testSaveBus(){
		Transport t = new Bus("nova_linija_bus",lineRepositoryMocked.findById(1L).get(), false, "7ca");
		Bus busSaved = busRepository.save(t);
		assertEquals(t, busSaved);
		assertNotNull(busSaved);
	}

	//trying to save empty object
	@Test(expected = DataIntegrityViolationException.class)
	public void testSaveBus2(){
		busRepository.save(new Bus());
	}



}

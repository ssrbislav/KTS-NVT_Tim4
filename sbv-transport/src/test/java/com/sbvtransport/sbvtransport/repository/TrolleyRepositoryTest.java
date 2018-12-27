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
import com.sbvtransport.sbvtransport.model.Transport;
import com.sbvtransport.sbvtransport.model.Trolley;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class TrolleyRepositoryTest {
	
	@Autowired
	TrolleyRepository trolleyRepository;
	
	@MockBean
	LineRepository lineRepositoryMocked;

	@Before
	public void setUp(){
		Line l = new Line("new_line_2", TypeTransport.trolley);
		Optional<Line> line = Optional.of(l);
		Mockito.when(lineRepositoryMocked.findById(1L)).thenReturn(line);
	}
	
	@Test
	public void testSaveTrolley(){
		Transport t = new Trolley("new_line_2_trolley_7ca",lineRepositoryMocked.findById(1L).get(), false, "7ca");
		Trolley trolleySaved = trolleyRepository.save(t);
		assertEquals(t, trolleySaved);
		assertNotNull(trolleySaved);
	}

	//trying to save empty object
	@Test(expected = DataIntegrityViolationException.class)
	public void testSaveTrolley2(){
		trolleyRepository.save(new Trolley());
	}

}

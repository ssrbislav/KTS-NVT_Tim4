package com.sbvtransport.sbvtransport.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.sbvtransport.sbvtransport.enumeration.TypeTransport;
import com.sbvtransport.sbvtransport.model.Bus;
import com.sbvtransport.sbvtransport.model.Line;
import com.sbvtransport.sbvtransport.model.Trolley;
import com.sbvtransport.sbvtransport.repository.BusRepository;
import com.sbvtransport.sbvtransport.repository.LineRepository;
import com.sbvtransport.sbvtransport.repository.TrolleyRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TrolleyServiceJUnitTest {

	@Autowired
	private ITrolleyService trolleyService;

	@MockBean
	private TrolleyRepository trolleyRepository;

	@MockBean
	private LineRepository lineRepository;

	@Before
	public void setUp() {

		List<Trolley> trolleys = new ArrayList<>();
		trolleys.add(new Trolley(new Line("nova_linija", TypeTransport.trolley), false, "ime", 1L, "neki_kod"));
		trolleys.add(new Trolley(new Line("nova_linija", TypeTransport.trolley), true, "ime2", 2L, "neki_kod2"));
		Mockito.when(trolleyRepository.findAll()).thenReturn(trolleys);

		Trolley t = new Trolley(new Line("nova_linija", TypeTransport.trolley), false, "6ca", 1L,
				"nova_linija_trolley_6ca");
		Optional<Trolley> oTrolley = Optional.of(t);
		Mockito.when(trolleyRepository.findById(1L)).thenReturn(oTrolley);

		Mockito.when(trolleyRepository.findById(10L)).thenReturn(null);

		Line line = new Line("nova_linija", TypeTransport.trolley);
		line.setId(1L);
//		line.setStation_list(new ArrayList<>());
		Optional<Line> oLine = Optional.of(line);
		Mockito.when(lineRepository.findById(1L)).thenReturn(oLine);
		Trolley t2 = new Trolley("nova_linija_trolley_5ca", line, false, "5ca");
		Mockito.when(trolleyRepository.save(t2)).thenReturn(t2);

	}

	@Test
	public void findAllTest() {
		List<Trolley> trolleys = trolleyService.findAll();
		assertThat(trolleys).hasSize(2);
		assertNotNull(trolleys);
		assertThat(trolleys.get(0).getCode()).isEqualTo("neki_kod");
		assertThat(trolleys.get(0).getName()).isEqualTo("ime");
		assertThat(trolleys.get(0).getLine().getName()).isEqualTo("nova_linija");
		assertThat(trolleys.get(0).getLine().getLine_type()).isEqualTo(TypeTransport.trolley);

	}

	@Test
	public void getOneTest() {

		Trolley findTrolley = trolleyService.getOne(1L);

		assertThat(findTrolley).isNotNull();
		assertThat(findTrolley.getId()).isEqualTo(1L);
		assertThat(findTrolley.getCode()).isEqualTo("nova_linija_trolley_6ca");
		assertThat(findTrolley.getName()).isEqualTo("6ca");
		assertThat(findTrolley.isLate()).isEqualTo(false);
		assertThat(findTrolley.getLine().getLine_type()).isEqualTo(TypeTransport.trolley);
		assertThat(findTrolley.getLine().getName()).isEqualTo("nova_linija");

	}

	@Test(expected = NullPointerException.class)
	public void getOneTest2() {
		// find trolley that doesn't exist
		trolleyService.getOne(10L);

	}

	@Test
	public void createTest() {
		// do this
	}

	@Test
	public void updateTest() {
		// do this when you correct update
	}

	// trolley with id that exist return true
	@Test
	public void deleteTest() {

		boolean successfull = trolleyService.delete(1L);
		assertThat(successfull).isTrue();

	}

	// trolley with id that doesn't exist return false
	@Test
	public void deleteTest2() {

		boolean successfull = trolleyService.delete(10L);
		assertThat(successfull).isFalse();

	}

	// return line
	@Test
	public void checkLineTest() {

		Line line = trolleyService.checkLine(1L);

		assertThat(line).isNotNull();
		assertThat(line.getId()).isEqualTo(1L);
		assertThat(line.getLine_type()).isEqualTo(TypeTransport.trolley);
		assertThat(line.getName()).isEqualTo("nova_linija");

	}

	// that line doesn't exist
	@Test(expected = NoSuchElementException.class)
	public void checkLineTest2() {

		trolleyService.checkLine(20L);

	}

}

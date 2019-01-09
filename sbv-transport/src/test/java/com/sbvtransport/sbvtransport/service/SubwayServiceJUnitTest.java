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
import com.sbvtransport.sbvtransport.model.Line;
import com.sbvtransport.sbvtransport.model.Subway;
import com.sbvtransport.sbvtransport.repository.LineRepository;
import com.sbvtransport.sbvtransport.repository.SubwayRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SubwayServiceJUnitTest {

	@Autowired
	private ISubwayService subwayService;

	@MockBean
	private SubwayRepository subwayRepository;

	@MockBean
	private LineRepository lineRepository;

	@Before
	public void setUp() {

//		List<Subway> subways = new ArrayList<>();
//		subways.add(new Subway(new Line("nova_linija", TypeTransport.subway), false, "ime", 1L, "neki_kod"));
//		subways.add(new Subway(new Line("nova_linija", TypeTransport.subway), true, "ime2", 2L, "neki_kod2"));
//		Mockito.when(subwayRepository.findAll()).thenReturn(subways);
//
//		Subway s = new Subway(new Line("nova_linija", TypeTransport.subway), false, "6ca", 1L,
//				"nova_linija_subway_6ca");
//		Optional<Subway> oSubway = Optional.of(s);
//		Mockito.when(subwayRepository.findById(1L)).thenReturn(oSubway);
//
//		Mockito.when(subwayRepository.findById(10L)).thenReturn(null);
//
//		Line line = new Line("nova_linija", TypeTransport.subway);
//		line.setId(1L);
////		line.setStation_list(new ArrayList<>());
//		Optional<Line> oLine = Optional.of(line);
//		Mockito.when(lineRepository.findById(1L)).thenReturn(oLine);
//		Subway s2 = new Subway("nova_linija_subway_5ca", line, false, "5ca");
//		Mockito.when(subwayRepository.save(s2)).thenReturn(s2);

	}

	@Test
	public void findAllTest() {
		List<Subway> subways = subwayService.findAll();
		assertThat(subways).hasSize(2);
		assertNotNull(subways);
		assertThat(subways.get(0).getCode()).isEqualTo("neki_kod");
		assertThat(subways.get(0).getName()).isEqualTo("ime");
		assertThat(subways.get(0).getLine().getName()).isEqualTo("nova_linija");
		assertThat(subways.get(0).getLine().getLine_type()).isEqualTo(TypeTransport.subway);

	}

	@Test
	public void getOneTest() {

		Subway findSubway = subwayService.getOne(1L);

		assertThat(findSubway).isNotNull();
		assertThat(findSubway.getId()).isEqualTo(1L);
		assertThat(findSubway.getCode()).isEqualTo("nova_linija_subway_6ca");
		assertThat(findSubway.getName()).isEqualTo("6ca");
		assertThat(findSubway.isLate()).isEqualTo(false);
		assertThat(findSubway.getLine().getLine_type()).isEqualTo(TypeTransport.subway);
		assertThat(findSubway.getLine().getName()).isEqualTo("nova_linija");

	}

	@Test(expected = NullPointerException.class)
	public void getOneTest2() {
		// find subway that doesn't exist
		subwayService.getOne(10L);

	}

	@Test
	public void createTest() {
		// do it
	}

	@Test
	public void updateTest() {
		// do this when you correct update
	}

	// subway with id that exist return true
	@Test
	public void deleteTest() {

		boolean successfull = subwayService.delete(1L);
		assertThat(successfull).isTrue();

	}

	// bus with id that doesn't exist return false
	@Test
	public void deleteTest2() {

		boolean successfull = subwayService.delete(10L);
		assertThat(successfull).isFalse();

	}

	// return line
	@Test
	public void checkLineTest() {

		Line line = subwayService.checkLine(1L);

		assertThat(line).isNotNull();
		assertThat(line.getId()).isEqualTo(1L);
		assertThat(line.getLine_type()).isEqualTo(TypeTransport.subway);
		assertThat(line.getName()).isEqualTo("nova_linija");

	}

	// that line doesn't exist
	@Test(expected = NoSuchElementException.class)
	public void checkLineTest2() {

		subwayService.checkLine(20L);

	}

}

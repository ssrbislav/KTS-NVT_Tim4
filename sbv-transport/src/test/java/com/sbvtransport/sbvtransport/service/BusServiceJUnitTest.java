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
import com.sbvtransport.sbvtransport.repository.BusRepository;
import com.sbvtransport.sbvtransport.repository.LineRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class BusServiceJUnitTest {

	@Autowired
	private IBusService busService;

	@MockBean
	private BusRepository busRepository;

	@MockBean
	private LineRepository lineRepository;

	@Before
	public void setUp() {

		List<Bus> buses = new ArrayList<>();
		buses.add(new Bus(new Line("nova_linija", TypeTransport.bus), false, "ime", 1L, "neki_kod"));
		buses.add(new Bus(new Line("nova_linija", TypeTransport.bus), true, "ime2", 2L, "neki_kod2"));
		Mockito.when(busRepository.findAll()).thenReturn(buses);

		Bus b = new Bus(new Line("nova_linija", TypeTransport.bus), false, "6ca", 1L, "nova_linija_bus_6ca");
		Optional<Bus> oBus = Optional.of(b);
		Mockito.when(busRepository.findById(1L)).thenReturn(oBus);

		Mockito.when(busRepository.findById(10L)).thenReturn(null);

		Line line = new Line("nova_linija", TypeTransport.bus);
		line.setId(1L);
//		line.setStation_list(new ArrayList<>());
		Optional<Line> oLine = Optional.of(line);
		Mockito.when(lineRepository.findById(1L)).thenReturn(oLine);
		Bus b2 = new Bus("nova_linija_bus_5ca", line, false, "5ca");
		Mockito.when(busRepository.save(b2)).thenReturn(b2);

	}

	@Test
	public void findAllTest() {
		List<Bus> buses = busService.findAll();
		assertThat(buses).hasSize(2);
		assertNotNull(buses);
		assertThat(buses.get(0).getCode()).isEqualTo("neki_kod");
		assertThat(buses.get(0).getName()).isEqualTo("ime");
		assertThat(buses.get(0).getLine().getName()).isEqualTo("nova_linija");
		assertThat(buses.get(0).getLine().getLine_type()).isEqualTo(TypeTransport.bus);

	}

	@Test
	public void getOneTest() {

		Bus findBus = busService.getOne(1L);

		assertThat(findBus).isNotNull();
		assertThat(findBus.getId()).isEqualTo(1L);
		assertThat(findBus.getCode()).isEqualTo("nova_linija_bus_6ca");
		assertThat(findBus.getName()).isEqualTo("6ca");
		assertThat(findBus.isLate()).isEqualTo(false);
		assertThat(findBus.getLine().getLine_type()).isEqualTo(TypeTransport.bus);
		assertThat(findBus.getLine().getName()).isEqualTo("nova_linija");

	}

	@Test(expected = NullPointerException.class)
	public void getOneTest2() {
		// find bus that doesn't exist
		busService.getOne(10L);

	}

	// @Test
	// public void createTest(){
	//
	// BusDTO bus = new BusDTO(false, "5ca", 1L);
	// Bus createBus = busService.create(bus);
	//
	// //assertThat(createBus).isNotNull();
	// //assertThat(createBus.getId()).isEqualTo(1L);
	// assertThat(createBus.getCode()).isEqualTo("nova_linija_bus_5ca");
	// assertThat(createBus.getName()).isEqualTo("5ca");
	// assertThat(createBus.isLate()).isEqualTo(false);
	// assertThat(createBus.getLine().getId()).isEqualTo(1L);
	// assertThat(createBus.getLine().getLine_type()).isEqualTo(TypeTransport.bus);
	// assertThat(createBus.getLine().getName()).isEqualTo("nova_linija");
	//
	// }

	@Test
	public void updateTest() {
		// do this when you correct update
	}

	// bus with id that exist return true
	@Test
	public void deleteTest() {

		boolean successfull = busService.delete(1L);
		assertThat(successfull).isTrue();

	}

	// bus with id that doesn't exist return false
	@Test
	public void deleteTest2() {

		boolean successfull = busService.delete(10L);
		assertThat(successfull).isFalse();

	}

	// return line
	@Test
	public void checkLineTest() {

		Line line = busService.checkLine(1L);

		assertThat(line).isNotNull();
		assertThat(line.getId()).isEqualTo(1L);
		assertThat(line.getLine_type()).isEqualTo(TypeTransport.bus);
		assertThat(line.getName()).isEqualTo("nova_linija");

	}

	// that line doesn't exist
	@Test(expected = NoSuchElementException.class)
	public void checkLineTest2() {

		busService.checkLine(20L);

	}

}

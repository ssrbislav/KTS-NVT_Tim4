package com.sbvtransport.sbvtransport.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import java.util.ArrayList;
import java.util.List;
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

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class BusServiceJUnitTest {

	@Autowired
	private IBusService busService;

	@MockBean
	private BusRepository busRepository;

	@Before
	public void setUp() {

		List<Bus> buses = new ArrayList<>();
		buses.add(new Bus("neki_kod", new Line("nova_linija", TypeTransport.bus), false, "ime"));
		buses.add(new Bus("neki_kod2", new Line("nova_linija", TypeTransport.bus), true, "ime2"));
		Mockito.when(busRepository.findAll()).thenReturn(buses);

		Bus b = new Bus(new Line("nova_linija", TypeTransport.bus), false, "6ca", 1L, "nova_linija_bus_6ca");
		Optional<Bus> oBus = Optional.of(b);
		Mockito.when(busRepository.findById(1L)).thenReturn(oBus);

		Mockito.when(busRepository.findById(10L)).thenReturn(null);

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

}

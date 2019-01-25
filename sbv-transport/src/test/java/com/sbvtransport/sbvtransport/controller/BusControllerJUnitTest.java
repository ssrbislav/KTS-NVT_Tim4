package com.sbvtransport.sbvtransport.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import com.sbvtransport.sbvtransport.dto.BusDTO;
import com.sbvtransport.sbvtransport.dto.ChangeTransportDTO;
import com.sbvtransport.sbvtransport.enumeration.TypeTransport;
import com.sbvtransport.sbvtransport.enumeration.Zone;
import com.sbvtransport.sbvtransport.model.Bus;
import com.sbvtransport.sbvtransport.model.Line;
import com.sbvtransport.sbvtransport.model.Station;
import com.sbvtransport.sbvtransport.service.BusService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
public class BusControllerJUnitTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@MockBean
	private BusService busService;

	@Before
	public void setUp() {
		List<Bus> buses = new ArrayList<>();
		buses.add(new Bus(new Line("8ca", TypeTransport.bus, Zone.first, 1L), false, "ime", 1L, "neki_kod", 5, false));
		buses.add(new Bus(new Line("8ca", TypeTransport.bus, Zone.first, 1L), true, "ime2", 2L, "neki_kod2", 6, false));
		Mockito.when(busService.findAll()).thenReturn(buses);

		Bus b = new Bus(new Line("8ca", TypeTransport.bus, Zone.first, 1L), false, "lasta", 1L, "8ca_bus_lasta", 5,
				false);
		Mockito.when(busService.getOne(1L)).thenReturn(b);
		Mockito.when(busService.getOne(100L)).thenReturn(null);

		List<Station> stations = new ArrayList<>();

		Bus bb = new Bus( new Line(1L, "7ca", stations, TypeTransport.bus, Zone.first), false, "Lasta", 4L, "7ca_bus_Lasta", 5, false);

		BusDTO busDTO = new BusDTO("Lasta", 1L, 5);
		Mockito.when(busService.create(busDTO)).thenReturn(bb);
		
		BusDTO busWrong = new BusDTO("Lasta", 1674574646L, 5);
		Mockito.when(busService.create(busWrong)).thenReturn(null);
		
		Bus changeBus = new Bus( new Line(1L, "7ca", stations, TypeTransport.bus, Zone.first), true, "novo ime", 2L, "7ca_bus_novo ime", 6,false);
		ChangeTransportDTO change = new ChangeTransportDTO(2L, "novo ime", 6, null,null);
		Mockito.when(busService.change(change)).thenReturn(changeBus);



	}

	@Test
	public void getAllTest() {

		ResponseEntity<Bus[]> responseEntity = restTemplate.getForEntity("/api/bus", Bus[].class);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(2, responseEntity.getBody().length);
		assertNotNull(responseEntity.getBody());

	}

	// get one-good value
	@Test
	public void getOneTest() {

		ResponseEntity<Bus> responseEntity = restTemplate.getForEntity("/api/bus/getBus/1", Bus.class);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());
		assertEquals("lasta", responseEntity.getBody().getName());
		assertEquals("8ca_bus_lasta", responseEntity.getBody().getCode());
		assertEquals(false, responseEntity.getBody().isLate());
		assertEquals(5, responseEntity.getBody().getTime_arrive());
		assertEquals("8ca", responseEntity.getBody().getLine().getName());
		assertEquals(TypeTransport.bus, responseEntity.getBody().getLine().getLine_type());
		assertEquals(Zone.first, responseEntity.getBody().getLine().getZone());

	}

	// bus doesn't exist
	@Test
	public void getOne2Test() {

		ResponseEntity<Bus> responseEntity = restTemplate.getForEntity("/api/bus/getBus/100", Bus.class);

		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
		assertNull(responseEntity.getBody());

	}

	// create bus with line that doesn't exist
	@Test
	public void createBusTest2() {
		
		BusDTO bus = new BusDTO("Lasta", 1674574646L, 5);

		ResponseEntity<Bus> responseEntity = restTemplate.postForEntity("/api/bus/addBus",bus, Bus.class);

		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
		assertNull(responseEntity.getBody());

	}
	
//	@Test
//	public void updateBusTest(){
//		List<Station> stations = new ArrayList<>();
//
//		Bus changeBus = new Bus( new Line(1L, "7ca", stations, TypeTransport.bus, Zone.first), true, "novo ime", 2L, "7ca_bus_novo ime", 6,false);
//		ChangeTransportDTO change = new ChangeTransportDTO(2L, "novo ime", 6, null,null);
//		Mockito.when(busService.change(change)).thenReturn(changeBus);
//		
//		
//		ResponseEntity<Bus> responseEntity = restTemplate.postForEntity("/api/bus/updateBus",change, Bus.class);
//
//		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//		//assertNotNull(responseEntity.getBody());
//		assertEquals("novo ime",responseEntity.getBody().getName());
//		assertEquals(6,responseEntity.getBody().getTime_arrive());
//
//
//	}

}

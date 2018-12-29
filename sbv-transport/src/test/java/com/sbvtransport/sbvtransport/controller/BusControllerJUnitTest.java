package com.sbvtransport.sbvtransport.controller;

import static org.junit.Assert.assertEquals;

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
import org.springframework.test.context.junit4.SpringRunner;

import com.sbvtransport.sbvtransport.enumeration.TypeTransport;
import com.sbvtransport.sbvtransport.model.Bus;
import com.sbvtransport.sbvtransport.model.Line;
import com.sbvtransport.sbvtransport.service.BusService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class BusControllerJUnitTest {
	
	@Autowired
    private TestRestTemplate restTemplate;
	
	@MockBean
	private BusService busService;
	
	@Before
	public void setUp(){
		List<Bus> buses = new ArrayList<>();
		buses.add(new Bus(new Line("nova_linija", TypeTransport.bus), false, "ime", 1L, "neki_kod"));
		buses.add(new Bus(new Line("nova_linija", TypeTransport.bus), true, "ime2", 2L, "neki_kod2"));
		Mockito.when(busService.findAll()).thenReturn(buses);
	}
	
	@Test
	public void getAllTest(){
//		
//		ResponseEntity<Bus[]> responseEntity =
//	            restTemplate.getForEntity("/api/bus", Bus[].class);
//		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//		//assertEquals(2, responseEntity.getBody().length);
//		
		
	}

}

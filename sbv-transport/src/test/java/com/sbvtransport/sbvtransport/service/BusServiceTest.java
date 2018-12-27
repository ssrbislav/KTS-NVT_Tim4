package com.sbvtransport.sbvtransport.service;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import com.sbvtransport.sbvtransport.dto.BusDTO;
import com.sbvtransport.sbvtransport.enumeration.TypeTransport;
import com.sbvtransport.sbvtransport.model.Bus;
import com.sbvtransport.sbvtransport.model.Line;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations="classpath:application-test.properties")
@Transactional
public class BusServiceTest {

	@Autowired
	private IBusService busService;
	
	@Autowired
	private ILineService lineService;
	
	@Test
	public void findAllTest(){
		List<Bus> buses = busService.findAll();
		assertThat(buses).hasSize(3);

	}
	
	@Test
	public void getOneTest(){
		
		Bus findBus = busService.getOne(1L);
		
		assertThat(findBus).isNotNull();
		assertThat(findBus.getId()).isEqualTo(1L);
		assertThat(findBus.getCode()).isEqualTo("nova_linija_bus_7ca" );
		assertThat(findBus.getName()).isEqualTo("7ca");
		assertThat(findBus.isLate()).isEqualTo(false);
		assertThat(findBus.getLine().getId()).isEqualTo(1L);
		assertThat(findBus.getLine().getLine_type()).isEqualTo(TypeTransport.bus);
		assertThat(findBus.getLine().getName()).isEqualTo("nova_linija");
		
		Bus findbus2 = busService.getOne(10L);
		assertThat(findbus2).isNull();

	}
	
	@Test
    @Transactional
    @Rollback(true)
	public void createTest(){
		BusDTO bus = new BusDTO(false, "67ca", 1L);
		
		int dbSizeBeforeAdd = busService.findAll().size();
		
		Bus dbBus = busService.create(bus);
		assertThat(dbBus).isNotNull();
		
		List<Bus> buses = busService.findAll();
		assertThat(buses).hasSize(dbSizeBeforeAdd + 1);
		assertThat(dbBus.getCode()).isEqualTo("nova_linija_bus_67ca");
		assertThat(dbBus.getId()).isEqualTo(4L);
		assertThat(dbBus.getName()).isEqualTo("67ca");
		assertThat(dbBus.isLate()).isEqualTo(false);
		assertThat(dbBus.getLine().getId()).isEqualTo(1L);
		assertThat(dbBus.getLine().getLine_type()).isEqualTo(TypeTransport.bus);
		assertThat(dbBus.getLine().getName()).isEqualTo("nova_linija");
		
		//create a bus with a line that doesn't exist
		BusDTO bus2 = new BusDTO(false, "67ca", 10L);
		Bus dbBus2 = busService.create(bus2);
		assertThat(dbBus2).isNull();
		
		//create a bus with a line that isn't a correct type
		BusDTO bus3 = new BusDTO(false, "67ca", 2L);
		Bus dbBus3 = busService.create(bus3);
		assertThat(dbBus3).isNull();

	}
	
	//change a test for update because you need to change a update depending the angular
	@Test
    @Transactional
    @Rollback(true)
	public void updateTest(){
		
		Bus bus = new Bus();
		bus.setId(1L);
		bus.setName("8ca");
		bus.setLate(true);
		bus.setLocation(null);
		bus.setTimetable(null);
		bus.setCode("nova_linija_bus_8ca");
		bus.setLine(lineService.getOne(1L));
		
		Bus dbBus = busService.update(bus);
		assertThat(dbBus).isNotNull();
		assertThat(dbBus.getId()).isEqualTo(1L);
		assertThat(dbBus.getCode()).isEqualTo("nova_linija_bus_8ca");
		assertThat(dbBus.getName()).isEqualTo("8ca");
		assertThat(dbBus.isLate()).isEqualTo(true);
		assertThat(dbBus.getLine().getId()).isEqualTo(1L);
		assertThat(dbBus.getLine().getLine_type()).isEqualTo(TypeTransport.bus);
		assertThat(dbBus.getLine().getName()).isEqualTo("nova_linija");	
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void deleteTest(){
		
		int dbSizeBeforeRemove = busService.findAll().size();
		boolean successful = busService.delete(2L);
		assertThat(successful).isNotNull();
		assertThat(successful).isEqualTo(true);
		
		List<Bus> buses = busService.findAll();
		assertThat(buses).hasSize(dbSizeBeforeRemove-1);
		
		Bus dbBus = busService.getOne(2L);
		assertThat(dbBus).isNull();
		
		//delete bus that doesn't exist
		boolean successful2 = busService.delete(10L);
		assertThat(successful2).isNotNull();
		assertThat(successful2).isEqualTo(false);
	}
	
	@Test
	public void checkLineTest(){
		
		Line dbLine = busService.checkLine(1L);
		assertThat(dbLine).isNotNull();
		assertThat(dbLine.getId()).isEqualTo(1L);
		assertThat(dbLine.getLine_type()).isEqualTo(TypeTransport.bus);
		assertThat(dbLine.getName()).isEqualTo("nova_linija");
		
		//line exist but it is not good type
		Line dbLine2 = busService.checkLine(2L);
		assertThat(dbLine2).isNull();
		
		//line doesn't exist
		Line dbLine3 = busService.checkLine(10L);
		assertThat(dbLine3).isNull();
		
	
	}
	

}

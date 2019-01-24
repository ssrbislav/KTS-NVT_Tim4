package com.sbvtransport.sbvtransport.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
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
import com.sbvtransport.sbvtransport.dto.AddLocationDTO;
import com.sbvtransport.sbvtransport.dto.ChangeTransportDTO;
import com.sbvtransport.sbvtransport.enumeration.TypeTransport;
import com.sbvtransport.sbvtransport.enumeration.Zone;
import com.sbvtransport.sbvtransport.model.Bus;
import com.sbvtransport.sbvtransport.model.Line;
import com.sbvtransport.sbvtransport.model.Location;
import com.sbvtransport.sbvtransport.repository.BusRepository;
import com.sbvtransport.sbvtransport.repository.LineRepository;
import com.sbvtransport.sbvtransport.repository.LocationRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class BusServiceJUnitTest {

	@Autowired
	private IBusService busService;

	@MockBean
	private BusRepository busRepository;

	@MockBean
	private LineRepository lineRepository;

	@MockBean
	private LocationRepository locationRepository;

	@Before
	public void setUp() {

		List<Bus> buses = new ArrayList<>();
		buses.add(new Bus(new Line("8ca", TypeTransport.bus, Zone.first, 1L), false, "ime", 1L, "neki_kod", 5, false));
		buses.add(new Bus(new Line("8ca", TypeTransport.bus, Zone.first, 1L), true, "ime2", 2L, "neki_kod2", 6, false));
		Mockito.when(busRepository.findAll()).thenReturn(buses);

		Bus b = new Bus(new Line("8ca", TypeTransport.bus, Zone.first, 1L), false, "lasta", 1L, "8ca_bus_lasta", 5,
				false);
		Optional<Bus> oBus = Optional.of(b);
		Mockito.when(busRepository.findById(1L)).thenReturn(oBus);

		Mockito.when(busRepository.findById(10L)).thenReturn(null);

		Line line = new Line("8ca", TypeTransport.bus, Zone.first, 1L);
		line.setId(1L);
		Optional<Line> oLine = Optional.of(line);
		Mockito.when(lineRepository.findById(1L)).thenReturn(oLine);

		Bus b2 = new Bus("nova_linija_bus_5ca", line, false, "5ca", 5, false);
		Mockito.when(busRepository.save(b2)).thenReturn(b2);
		Mockito.when(lineRepository.findById(20L)).thenReturn(null);

		Location location = new Location(1L, "Lokacija", "adresa", 75.6575f, 5646.68f, "station");
		Optional<Location> oLocation = Optional.of(location);
		Mockito.when(locationRepository.findById(1L)).thenReturn(oLocation);
		Mockito.when(locationRepository.findById(20L)).thenReturn(null);

		b.setLocation(location);
		Mockito.when(busRepository.save(b)).thenReturn(b);

		Bus b3 = new Bus(new Line("8ca", TypeTransport.bus, Zone.first, 1L), true, "novo ime", 1L, "8ca_bus_novo ime",
				6, false);
		b3.setLocation(null);
		b3.setTimetable(null);
		Mockito.when(busRepository.save(b3)).thenReturn(b3);

	}

	@Test
	public void findAllTest() {
		List<Bus> buses = busService.findAll();
		assertNotNull(buses);
		assertThat(buses).hasSize(2);
		assertThat(buses.get(0).getId()).isEqualTo(1L);
		assertThat(buses.get(0).getCode()).isEqualTo("neki_kod");
		assertThat(buses.get(0).getName()).isEqualTo("ime");
		assertThat(buses.get(0).getLine().getName()).isEqualTo("8ca");
		assertThat(buses.get(0).getLine().getLine_type()).isEqualTo(TypeTransport.bus);
		assertThat(buses.get(0).getLine().getZone()).isEqualTo(Zone.first);
		assertThat(buses.get(0).getLine().getFirst_station()).isEqualTo(1L);
		assertThat(buses.get(0).getTime_arrive()).isEqualTo(5);
		assertThat(buses.get(0).isDeleted()).isEqualTo(false);
		assertThat(buses.get(0).isLate()).isEqualTo(false);

	}

	@Test
	public void getOneTest() {

		Bus findBus = busService.getOne(1L);
		assertNotNull(findBus);
		assertThat(findBus.getId()).isEqualTo(1L);
		assertThat(findBus.getCode()).isEqualTo("8ca_bus_lasta");
		assertThat(findBus.getName()).isEqualTo("lasta");
		assertThat(findBus.isLate()).isEqualTo(false);
		assertThat(findBus.getLine().getName()).isEqualTo("8ca");
		assertThat(findBus.getLine().getLine_type()).isEqualTo(TypeTransport.bus);
		assertThat(findBus.getLine().getZone()).isEqualTo(Zone.first);
		assertThat(findBus.getLine().getFirst_station()).isEqualTo(1L);
		assertThat(findBus.getTime_arrive()).isEqualTo(5);
		assertThat(findBus.isDeleted()).isEqualTo(false);
		assertThat(findBus.isLate()).isEqualTo(false);

	}

	@Test(expected = NullPointerException.class)
	public void getOneTest2() {
		// find bus that doesn't exist
		busService.getOne(10L);

	}

	// try to change data in bus that doesn't exist
	@Test(expected = NullPointerException.class)
	public void changeTest() {
		ChangeTransportDTO changeData = new ChangeTransportDTO(10L, "novo ime", 6, null, null);
		busService.change(changeData);
	}

	@Test
	public void changeTest2() {
		// correct true
		ChangeTransportDTO changeData = new ChangeTransportDTO(1L, "novo ime", 6, null, null);
		Bus newBus = busService.change(changeData);
		assertNotNull(newBus);
		assertThat(newBus.getId()).isEqualTo(changeData.getId_transport());
		assertThat(newBus.getCode()).isEqualTo("8ca_bus_novo ime");
		assertThat(newBus.getName()).isEqualTo(changeData.getName());
		assertThat(newBus.isLate()).isEqualTo(true);
		assertThat(newBus.getLine().getName()).isEqualTo("8ca");
		assertThat(newBus.getLine().getLine_type()).isEqualTo(TypeTransport.bus);
		assertThat(newBus.getLine().getZone()).isEqualTo(Zone.first);
		assertThat(newBus.getLine().getFirst_station()).isEqualTo(1L);
		assertThat(newBus.getTime_arrive()).isEqualTo(changeData.getTime_arrive());
		assertThat(newBus.isDeleted()).isEqualTo(false);
		assertThat(newBus.isLate()).isEqualTo(true);
		assertThat(newBus.getTimetable()).isEqualTo(changeData.getTimetable());
		assertThat(newBus.getLocation()).isEqualTo(changeData.getCurrent_location());

	}

	// bus with id that exist return true
	@Test
	public void deleteTest() {

		boolean successfull = busService.delete(1L);
		assertNotNull(successfull);
		assertThat(successfull).isTrue();

	}

	// bus with id that doesn't exist return false
	@Test
	public void deleteTest2() {

		boolean successfull = busService.delete(10L);
		assertNotNull(successfull);
		assertThat(successfull).isFalse();

	}

	// return line
	@Test
	public void checkLineTest() {

		Line line = busService.checkLine(1L);

		assertThat(line).isNotNull();
		assertThat(line.getId()).isEqualTo(1L);
		assertThat(line.getLine_type()).isEqualTo(TypeTransport.bus);
		assertThat(line.getName()).isEqualTo("8ca");
		assertThat(line.getZone()).isEqualTo(Zone.first);
		assertThat(line.getFirst_station()).isEqualTo(1L);

	}

	// that line doesn't exist
	@Test(expected = NullPointerException.class)
	public void checkLineTest2() {
		Line line = busService.checkLine(20L);
		assertNull(line);

	}

	@Test
	public void checkCodeExistTest() {
		// code exist
		boolean check = busService.codeExist("neki_kod");
		assertNotNull(check);
		assertThat(check).isEqualTo(true);

		// don't exist
		boolean check2 = busService.codeExist("dfesfdefefefefefe");
		assertNotNull(check2);
		assertThat(check2).isEqualTo(false);

	}

	@Test
	public void addLocationTest() {
		AddLocationDTO newLocation = new AddLocationDTO(1L, 1L);
		Bus newBus = busService.addLocation(newLocation);
		assertNotNull(newBus);
		assertThat(newBus.getId()).isEqualTo(1L);
		assertThat(newBus.getCode()).isEqualTo("8ca_bus_lasta");
		assertThat(newBus.getName()).isEqualTo("lasta");
		assertThat(newBus.isLate()).isEqualTo(false);
		assertThat(newBus.getLine().getName()).isEqualTo("8ca");
		assertThat(newBus.getLine().getLine_type()).isEqualTo(TypeTransport.bus);
		assertThat(newBus.getLine().getZone()).isEqualTo(Zone.first);
		assertThat(newBus.getLine().getFirst_station()).isEqualTo(1L);
		assertThat(newBus.getTime_arrive()).isEqualTo(5);
		assertThat(newBus.isDeleted()).isEqualTo(false);
		assertThat(newBus.isLate()).isEqualTo(false);

	}

	// trying to add location that doesn't exist
	@Test(expected = NullPointerException.class)
	public void addLocationTest2() {
		AddLocationDTO newLocation = new AddLocationDTO(1L, 20L);
		busService.addLocation(newLocation);

	}

	// trying to add location to the bus that doesn't exist
	@Test(expected = NullPointerException.class)
	public void addLocationTest3() {
		AddLocationDTO newLocation = new AddLocationDTO(10L, 1L);
		busService.addLocation(newLocation);

	}

	@Test
	public void checkIfLateTest() {

		boolean notLate = busService.checkIfLate(5);
		assertThat(notLate).isNotNull();
		assertThat(notLate).isEqualTo(false);

		boolean Late = busService.checkIfLate(7);
		assertThat(Late).isNotNull();
		assertThat(Late).isEqualTo(true);

	}

}

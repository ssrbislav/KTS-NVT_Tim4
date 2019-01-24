package com.sbvtransport.sbvtransport.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

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

import com.sbvtransport.sbvtransport.dto.AddLocationDTO;
import com.sbvtransport.sbvtransport.dto.ChangeTransportDTO;
import com.sbvtransport.sbvtransport.enumeration.TypeTransport;
import com.sbvtransport.sbvtransport.enumeration.Zone;
import com.sbvtransport.sbvtransport.model.Bus;
import com.sbvtransport.sbvtransport.model.Line;
import com.sbvtransport.sbvtransport.model.Location;
import com.sbvtransport.sbvtransport.model.Trolley;
import com.sbvtransport.sbvtransport.repository.BusRepository;
import com.sbvtransport.sbvtransport.repository.LineRepository;
import com.sbvtransport.sbvtransport.repository.LocationRepository;
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

	@MockBean
	private LocationRepository locationRepository;

	@Before
	public void setUp() {

		List<Trolley> trolleys = new ArrayList<>();
		trolleys.add(new Trolley(new Line("8ca", TypeTransport.trolley, Zone.first, 1L), false, "ime", 1L, "neki_kod",
				5, false));
		trolleys.add(new Trolley(new Line("8ca", TypeTransport.trolley, Zone.first, 1L), true, "ime2", 2L, "neki_kod2",
				6, false));
		Mockito.when(trolleyRepository.findAll()).thenReturn(trolleys);

		Trolley t = new Trolley(new Line("8ca", TypeTransport.trolley, Zone.first, 1L), false, "lasta", 1L,
				"8ca_trolley_lasta", 5, false);
		Optional<Trolley> oTrolley = Optional.of(t);
		Mockito.when(trolleyRepository.findById(1L)).thenReturn(oTrolley);

		Mockito.when(trolleyRepository.findById(10L)).thenReturn(null);

		Line line = new Line("8ca", TypeTransport.trolley, Zone.first, 1L);
		line.setId(1L);
		Optional<Line> oLine = Optional.of(line);
		Mockito.when(lineRepository.findById(1L)).thenReturn(oLine);

		Trolley t2 = new Trolley("nova_linija_trolley_5ca", line, false, "5ca", 5, false);
		Mockito.when(trolleyRepository.save(t2)).thenReturn(t2);
		Mockito.when(lineRepository.findById(20L)).thenReturn(null);

		Location location = new Location(1L, "Lokacija", "adresa", 75.6575f, 5646.68f, "station");
		Optional<Location> oLocation = Optional.of(location);
		Mockito.when(locationRepository.findById(1L)).thenReturn(oLocation);
		Mockito.when(locationRepository.findById(20L)).thenReturn(null);

		t.setLocation(location);
		Mockito.when(trolleyRepository.save(t)).thenReturn(t);

		Trolley t3 = new Trolley(new Line("8ca", TypeTransport.trolley, Zone.first, 1L), true, "novo ime", 1L,
				"8ca_trolley_novo ime", 6, false);
		t3.setLocation(null);
		t3.setTimetable(null);
		Mockito.when(trolleyRepository.save(t3)).thenReturn(t3);
	}

	@Test
	public void findAllTest() {
		List<Trolley> trolleys = trolleyService.findAll();
		assertNotNull(trolleys);
		assertThat(trolleys).hasSize(2);
		assertThat(trolleys.get(0).getId()).isEqualTo(1L);
		assertThat(trolleys.get(0).getCode()).isEqualTo("neki_kod");
		assertThat(trolleys.get(0).getName()).isEqualTo("ime");
		assertThat(trolleys.get(0).getLine().getName()).isEqualTo("8ca");
		assertThat(trolleys.get(0).getLine().getLine_type()).isEqualTo(TypeTransport.trolley);
		assertThat(trolleys.get(0).getLine().getZone()).isEqualTo(Zone.first);
		assertThat(trolleys.get(0).getLine().getFirst_station()).isEqualTo(1L);
		assertThat(trolleys.get(0).getTime_arrive()).isEqualTo(5);
		assertThat(trolleys.get(0).isDeleted()).isEqualTo(false);
		assertThat(trolleys.get(0).isLate()).isEqualTo(false);

	}

	@Test
	public void getOneTest() {

		Trolley findTrolley = trolleyService.getOne(1L);
		assertNotNull(findTrolley);
		assertThat(findTrolley.getId()).isEqualTo(1L);
		assertThat(findTrolley.getCode()).isEqualTo("8ca_trolley_lasta");
		assertThat(findTrolley.getName()).isEqualTo("lasta");
		assertThat(findTrolley.isLate()).isEqualTo(false);
		assertThat(findTrolley.getLine().getName()).isEqualTo("8ca");
		assertThat(findTrolley.getLine().getLine_type()).isEqualTo(TypeTransport.trolley);
		assertThat(findTrolley.getLine().getZone()).isEqualTo(Zone.first);
		assertThat(findTrolley.getLine().getFirst_station()).isEqualTo(1L);
		assertThat(findTrolley.getTime_arrive()).isEqualTo(5);
		assertThat(findTrolley.isDeleted()).isEqualTo(false);
		assertThat(findTrolley.isLate()).isEqualTo(false);

	}

	@Test(expected = NullPointerException.class)
	public void getOneTest2() {
		// find bus that doesn't exist
		trolleyService.getOne(10L);

	}

	// try to change data in bus that doesn't exist
	@Test(expected = NullPointerException.class)
	public void changeTest() {
		ChangeTransportDTO changeData = new ChangeTransportDTO(10L, "novo ime", 6, null, null);
		trolleyService.change(changeData);
	}

	@Test
	public void changeTest2() {
		// correct true
		ChangeTransportDTO changeData = new ChangeTransportDTO(1L, "novo ime", 6, null, null);
		Trolley newTrolley = trolleyService.change(changeData);
		assertNotNull(newTrolley);
		assertThat(newTrolley.getId()).isEqualTo(changeData.getId_transport());
		assertThat(newTrolley.getCode()).isEqualTo("8ca_trolley_novo ime");
		assertThat(newTrolley.getName()).isEqualTo(changeData.getName());
		assertThat(newTrolley.isLate()).isEqualTo(true);
		assertThat(newTrolley.getLine().getName()).isEqualTo("8ca");
		assertThat(newTrolley.getLine().getLine_type()).isEqualTo(TypeTransport.trolley);
		assertThat(newTrolley.getLine().getZone()).isEqualTo(Zone.first);
		assertThat(newTrolley.getLine().getFirst_station()).isEqualTo(1L);
		assertThat(newTrolley.getTime_arrive()).isEqualTo(changeData.getTime_arrive());
		assertThat(newTrolley.isDeleted()).isEqualTo(false);
		assertThat(newTrolley.isLate()).isEqualTo(true);
		assertThat(newTrolley.getTimetable()).isEqualTo(changeData.getTimetable());
		assertThat(newTrolley.getLocation()).isEqualTo(changeData.getCurrent_location());

	}

	@Test
	public void deleteTest() {
		// bus with id that exist return true
		boolean successfull = trolleyService.delete(1L);
		assertNotNull(successfull);
		assertThat(successfull).isTrue();

		// bus with id that doesn't exist return false
		boolean successfull2 = trolleyService.delete(10L);
		assertNotNull(successfull2);
		assertThat(successfull2).isFalse();

	}

	// return line
	@Test
	public void checkLineTest() {

		Line line = trolleyService.checkLine(1L);

		assertThat(line).isNotNull();
		assertThat(line.getId()).isEqualTo(1L);
		assertThat(line.getLine_type()).isEqualTo(TypeTransport.trolley);
		assertThat(line.getName()).isEqualTo("8ca");
		assertThat(line.getZone()).isEqualTo(Zone.first);
		assertThat(line.getFirst_station()).isEqualTo(1L);

	}

	// that line doesn't exist
	@Test(expected = NullPointerException.class)
	public void checkLineTest2() {
		Line line = trolleyService.checkLine(20L);
		assertNull(line);

	}

	@Test
	public void checkCodeExistTest() {
		// code exist
		boolean check = trolleyService.codeExist("neki_kod");
		assertNotNull(check);
		assertThat(check).isEqualTo(true);

		// don't exist
		boolean check2 = trolleyService.codeExist("dfesfdefefefefefe");
		assertNotNull(check2);
		assertThat(check2).isEqualTo(false);

	}

	// add location to trolley
	@Test
	public void addLocationTest() {
		AddLocationDTO newLocation = new AddLocationDTO(1L, 1L);
		Trolley newTrolley = trolleyService.addLocation(newLocation);
		assertNotNull(newTrolley);
		assertThat(newTrolley.getId()).isEqualTo(1L);
		assertThat(newTrolley.getCode()).isEqualTo("8ca_trolley_lasta");
		assertThat(newTrolley.getName()).isEqualTo("lasta");
		assertThat(newTrolley.isLate()).isEqualTo(false);
		assertThat(newTrolley.getLine().getName()).isEqualTo("8ca");
		assertThat(newTrolley.getLine().getLine_type()).isEqualTo(TypeTransport.trolley);
		assertThat(newTrolley.getLine().getZone()).isEqualTo(Zone.first);
		assertThat(newTrolley.getLine().getFirst_station()).isEqualTo(1L);
		assertThat(newTrolley.getTime_arrive()).isEqualTo(5);
		assertThat(newTrolley.isDeleted()).isEqualTo(false);
		assertThat(newTrolley.isLate()).isEqualTo(false);

	}

	// trying to add location that doesn't exist
	@Test(expected = NullPointerException.class)
	public void addLocationTest2() {
		AddLocationDTO newLocation = new AddLocationDTO(1L, 20L);
		trolleyService.addLocation(newLocation);

	}

	// trying to add location to the trolley that doesn't exist
	@Test(expected = NullPointerException.class)
	public void addLocationTest3() {
		AddLocationDTO newLocation = new AddLocationDTO(10L, 1L);
		trolleyService.addLocation(newLocation);

	}
	
	@Test
	public void checkIfLateTest() {

		boolean notLate = trolleyService.checkIfLate(5);
		assertThat(notLate).isNotNull();
		assertThat(notLate).isEqualTo(false);

		boolean Late = trolleyService.checkIfLate(7);
		assertThat(Late).isNotNull();
		assertThat(Late).isEqualTo(true);

	}

}

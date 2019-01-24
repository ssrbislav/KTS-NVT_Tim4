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
import com.sbvtransport.sbvtransport.model.Line;
import com.sbvtransport.sbvtransport.model.Location;
import com.sbvtransport.sbvtransport.model.Subway;
import com.sbvtransport.sbvtransport.repository.LineRepository;
import com.sbvtransport.sbvtransport.repository.LocationRepository;
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

	@MockBean
	private LocationRepository locationRepository;

	@Before
	public void setUp() {

		List<Subway> subways = new ArrayList<>();
		subways.add(new Subway(new Line("8ca", TypeTransport.subway, Zone.first, 1L), false, "ime", 1L, "neki_kod", 5,
				false));
		subways.add(new Subway(new Line("8ca", TypeTransport.subway, Zone.first, 1L), true, "ime2", 2L, "neki_kod2", 6,
				false));
		Mockito.when(subwayRepository.findAll()).thenReturn(subways);

		Subway b = new Subway(new Line("8ca", TypeTransport.subway, Zone.first, 1L), false, "lasta", 1L,
				"8ca_subway_lasta", 5, false);
		Optional<Subway> oSubway = Optional.of(b);
		Mockito.when(subwayRepository.findById(1L)).thenReturn(oSubway);

		Mockito.when(subwayRepository.findById(10L)).thenReturn(null);

		Line line = new Line("8ca", TypeTransport.subway, Zone.first, 1L);
		line.setId(1L);
		Optional<Line> oLine = Optional.of(line);
		Mockito.when(lineRepository.findById(1L)).thenReturn(oLine);

		Subway b2 = new Subway("nova_linija_subway_5ca", line, false, "5ca", 5, false);
		Mockito.when(subwayRepository.save(b2)).thenReturn(b2);
		Mockito.when(lineRepository.findById(20L)).thenReturn(null);

		Location location = new Location(1L, "Lokacija", "adresa", 75.6575f, 5646.68f, "station");
		Optional<Location> oLocation = Optional.of(location);
		Mockito.when(locationRepository.findById(1L)).thenReturn(oLocation);
		Mockito.when(locationRepository.findById(20L)).thenReturn(null);

		b.setLocation(location);
		Mockito.when(subwayRepository.save(b)).thenReturn(b);

		Subway b3 = new Subway(new Line("8ca", TypeTransport.subway, Zone.first, 1L), true, "novo ime", 1L,
				"8ca_subway_novo ime", 6, false);
		b3.setLocation(null);
		b3.setTimetable(null);
		Mockito.when(subwayRepository.save(b3)).thenReturn(b3);

	}

	@Test
	public void findAllTest() {
		List<Subway> subways = subwayService.findAll();
		assertNotNull(subways);
		assertThat(subways).hasSize(2);
		assertThat(subways.get(0).getId()).isEqualTo(1L);
		assertThat(subways.get(0).getCode()).isEqualTo("neki_kod");
		assertThat(subways.get(0).getName()).isEqualTo("ime");
		assertThat(subways.get(0).getLine().getName()).isEqualTo("8ca");
		assertThat(subways.get(0).getLine().getLine_type()).isEqualTo(TypeTransport.subway);
		assertThat(subways.get(0).getLine().getZone()).isEqualTo(Zone.first);
		assertThat(subways.get(0).getLine().getFirst_station()).isEqualTo(1L);
		assertThat(subways.get(0).getTime_arrive()).isEqualTo(5);
		assertThat(subways.get(0).isDeleted()).isEqualTo(false);
		assertThat(subways.get(0).isLate()).isEqualTo(false);

	}

	@Test
	public void getOneTest() {

		Subway findSubway = subwayService.getOne(1L);
		assertNotNull(findSubway);
		assertThat(findSubway.getId()).isEqualTo(1L);
		assertThat(findSubway.getCode()).isEqualTo("8ca_subway_lasta");
		assertThat(findSubway.getName()).isEqualTo("lasta");
		assertThat(findSubway.isLate()).isEqualTo(false);
		assertThat(findSubway.getLine().getName()).isEqualTo("8ca");
		assertThat(findSubway.getLine().getLine_type()).isEqualTo(TypeTransport.subway);
		assertThat(findSubway.getLine().getZone()).isEqualTo(Zone.first);
		assertThat(findSubway.getLine().getFirst_station()).isEqualTo(1L);
		assertThat(findSubway.getTime_arrive()).isEqualTo(5);
		assertThat(findSubway.isDeleted()).isEqualTo(false);
		assertThat(findSubway.isLate()).isEqualTo(false);

	}

	@Test(expected = NullPointerException.class)
	public void getOneTest2() {
		// find subway that doesn't exist
		subwayService.getOne(10L);

	}

	// try to change data in subway that doesn't exist
	@Test(expected = NullPointerException.class)
	public void changeTest() {
		ChangeTransportDTO changeData = new ChangeTransportDTO(10L, "novo ime", 6, null, null);
		subwayService.change(changeData);
	}

	@Test
	public void changeTest2() {
		// correct true
		ChangeTransportDTO changeData = new ChangeTransportDTO(1L, "novo ime", 6, null, null);
		Subway newSubway = subwayService.change(changeData);
		assertNotNull(newSubway);
		assertThat(newSubway.getId()).isEqualTo(changeData.getId_transport());
		assertThat(newSubway.getCode()).isEqualTo("8ca_subway_novo ime");
		assertThat(newSubway.getName()).isEqualTo(changeData.getName());
		assertThat(newSubway.isLate()).isEqualTo(true);
		assertThat(newSubway.getLine().getName()).isEqualTo("8ca");
		assertThat(newSubway.getLine().getLine_type()).isEqualTo(TypeTransport.subway);
		assertThat(newSubway.getLine().getZone()).isEqualTo(Zone.first);
		assertThat(newSubway.getLine().getFirst_station()).isEqualTo(1L);
		assertThat(newSubway.getTime_arrive()).isEqualTo(changeData.getTime_arrive());
		assertThat(newSubway.isDeleted()).isEqualTo(false);
		assertThat(newSubway.isLate()).isEqualTo(true);
		assertThat(newSubway.getTimetable()).isEqualTo(changeData.getTimetable());
		assertThat(newSubway.getLocation()).isEqualTo(changeData.getCurrent_location());

	}

	// subway with id that exist return true
	@Test
	public void deleteTest() {

		boolean successfull = subwayService.delete(1L);
		assertNotNull(successfull);
		assertThat(successfull).isTrue();

	}

	// subway with id that doesn't exist return false
	@Test
	public void deleteTest2() {

		boolean successfull = subwayService.delete(10L);
		assertNotNull(successfull);
		assertThat(successfull).isFalse();

	}

	// return line
	@Test
	public void checkLineTest() {

		Line line = subwayService.checkLine(1L);

		assertThat(line).isNotNull();
		assertThat(line.getId()).isEqualTo(1L);
		assertThat(line.getLine_type()).isEqualTo(TypeTransport.subway);
		assertThat(line.getName()).isEqualTo("8ca");
		assertThat(line.getZone()).isEqualTo(Zone.first);
		assertThat(line.getFirst_station()).isEqualTo(1L);

	}

	// that line doesn't exist
	@Test(expected = NullPointerException.class)
	public void checkLineTest2() {
		Line line = subwayService.checkLine(20L);
		assertNull(line);

	}

	@Test
	public void checkCodeExistTest() {
		// code exist
		boolean check = subwayService.codeExist("neki_kod");
		assertNotNull(check);
		assertThat(check).isEqualTo(true);

		// don't exist
		boolean check2 = subwayService.codeExist("dfesfdefefefefefe");
		assertNotNull(check2);
		assertThat(check2).isEqualTo(false);

	}

	@Test
	public void addLocationTest() {
		AddLocationDTO newLocation = new AddLocationDTO(1L, 1L);
		Subway newSubway = subwayService.addLocation(newLocation);
		assertNotNull(newSubway);
		assertThat(newSubway.getId()).isEqualTo(1L);
		assertThat(newSubway.getCode()).isEqualTo("8ca_subway_lasta");
		assertThat(newSubway.getName()).isEqualTo("lasta");
		assertThat(newSubway.isLate()).isEqualTo(false);
		assertThat(newSubway.getLine().getName()).isEqualTo("8ca");
		assertThat(newSubway.getLine().getLine_type()).isEqualTo(TypeTransport.subway);
		assertThat(newSubway.getLine().getZone()).isEqualTo(Zone.first);
		assertThat(newSubway.getLine().getFirst_station()).isEqualTo(1L);
		assertThat(newSubway.getTime_arrive()).isEqualTo(5);
		assertThat(newSubway.isDeleted()).isEqualTo(false);
		assertThat(newSubway.isLate()).isEqualTo(false);

	}

	// trying to add location that doesn't exist
	@Test(expected = NullPointerException.class)
	public void addLocationTest2() {
		AddLocationDTO newLocation = new AddLocationDTO(1L, 20L);
		subwayService.addLocation(newLocation);

	}

	// trying to add location to the subway that doesn't exist
	@Test(expected = NullPointerException.class)
	public void addLocationTest3() {
		AddLocationDTO newLocation = new AddLocationDTO(10L, 1L);
		subwayService.addLocation(newLocation);

	}

	@Test
	public void checkIfLateTest() {

		boolean notLate = subwayService.checkIfLate(5);
		assertThat(notLate).isNotNull();
		assertThat(notLate).isEqualTo(false);

		boolean Late = subwayService.checkIfLate(7);
		assertThat(Late).isNotNull();
		assertThat(Late).isEqualTo(true);

	}

}

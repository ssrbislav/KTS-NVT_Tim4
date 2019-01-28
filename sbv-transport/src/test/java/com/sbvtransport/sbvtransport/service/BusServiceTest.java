package com.sbvtransport.sbvtransport.service;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import com.sbvtransport.sbvtransport.dto.AddLocationDTO;
import com.sbvtransport.sbvtransport.dto.BusDTO;
import com.sbvtransport.sbvtransport.dto.ChangeTransportDTO;
import com.sbvtransport.sbvtransport.dto.FilterSearchTransportDTO;
import com.sbvtransport.sbvtransport.enumeration.TypeTransport;
import com.sbvtransport.sbvtransport.enumeration.Zone;
import com.sbvtransport.sbvtransport.model.Bus;
import com.sbvtransport.sbvtransport.model.Line;
import com.sbvtransport.sbvtransport.model.Location;
import com.sbvtransport.sbvtransport.model.Schedule;
import com.sbvtransport.sbvtransport.model.Timetable;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
@Rollback(value=true)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BusServiceTest {

	@Autowired
	private IBusService busService;

	@Autowired
	private ILineService lineService;

	@Test
	public void afindAllTest() {
		List<Bus> buses = busService.findAll();
		assertThat(buses).hasSize(4);

	}

	@Test
	public void getOneTest() {

		Bus findBus = busService.getOne(1L);

		assertThat(findBus).isNotNull();
		assertThat(findBus.getId()).isEqualTo(1L);
		assertThat(findBus.getCode()).isEqualTo("7ca_bus_lasta");
		assertThat(findBus.getName()).isEqualTo("lasta");
		assertThat(findBus.isLate()).isEqualTo(false);
		assertThat(findBus.isDeleted()).isEqualTo(false);
		assertThat(findBus.getTime_arrive()).isEqualTo(5);

		assertThat(findBus.getLine().getId()).isEqualTo(1L);
		assertThat(findBus.getLine().getLine_type()).isEqualTo(TypeTransport.bus);
		assertThat(findBus.getLine().getName()).isEqualTo("7ca");
		assertThat(findBus.getLine().getZone()).isEqualTo(Zone.first);
		assertThat(findBus.getLine().getFirst_station()).isEqualTo(1L);
		assertThat(findBus.getLine().isDeleted()).isEqualTo(false);
		assertThat(findBus.getLine().getStation_list().get(0).getId()).isEqualTo(1L);
		assertThat(findBus.getLine().getStation_list().get(0).getZone()).isEqualTo(Zone.first);
		assertThat(findBus.getLine().getStation_list().get(0).getLocation().getLocation_name()).isEqualTo("Rotkvarija");
		assertThat(findBus.getLine().getStation_list().get(0).getLocation().getAddress())
				.isEqualTo("71, Bulevar oslobodjenja, Роткварија,"
						+ " Novi Sad, Novi Sad City, South Backa District, Vojvodina, 21101, Serbia");
		assertThat(findBus.getLine().getStation_list().get(0).getLocation().getLatitude()).isEqualTo(45.2559f);
		assertThat(findBus.getLine().getStation_list().get(0).getLocation().getLongitude()).isEqualTo(19.8349f);

		assertThat(findBus.getLocation().getLocation_name()).isEqualTo("Banatic");
		assertThat(findBus.getLocation().getAddress()).isEqualTo(
				"150, Rumenacka, Банатић, Novi Sad, Novi Sad City, South Backa District, Vojvodina, 21138, Serbia");
		assertThat(findBus.getLocation().getLatitude()).isEqualTo(45.2652f);
		assertThat(findBus.getLocation().getLongitude()).isEqualTo(19.8159f);
		assertThat(findBus.getLocation().getType()).isEqualTo("station");

		// find bus that doesn't exist
		Bus findbus2 = busService.getOne(10L);
		assertThat(findbus2).isNull();

	}

	@Test
	@Transactional
	@Rollback(value=true)
	public void createTest() {
		BusDTO bus = new BusDTO("lasta2", 1L, 5);

		int dbSizeBeforeAdd = busService.findAll().size();

		Bus dbBus = busService.create(bus);
		assertThat(dbBus).isNotNull();

		List<Bus> buses = busService.findAll();
		assertThat(buses).hasSize(dbSizeBeforeAdd + 1);
		assertThat(dbBus.getCode()).isEqualTo("7ca_bus_lasta2");
		assertThat(dbBus.getId()).isEqualTo(5L);
		assertThat(dbBus.getName()).isEqualTo(bus.getName());
		assertThat(dbBus.isLate()).isEqualTo(false);
		assertThat(dbBus.isDeleted()).isEqualTo(false);
		assertThat(dbBus.getTime_arrive()).isEqualTo(bus.getTime_arrive());
		assertThat(dbBus.getTimetable()).isEqualTo(null);
		assertThat(dbBus.getLocation()).isEqualTo(null);

		assertThat(dbBus.getLine().getId()).isEqualTo(1L);
		assertThat(dbBus.getLine().getLine_type()).isEqualTo(TypeTransport.bus);
		assertThat(dbBus.getLine().getName()).isEqualTo("7ca");
		assertThat(dbBus.getLine().getZone()).isEqualTo(Zone.first);
		assertThat(dbBus.getLine().getFirst_station()).isEqualTo(1L);
		assertThat(dbBus.getLine().isDeleted()).isEqualTo(false);

		// create a bus with a line that isn't a correct type
		BusDTO bus3 = new BusDTO("Nis express", 2L, 6);
		Bus dbBus3 = busService.create(bus3);
		assertThat(dbBus3).isNull();

		// create a bus with a time arrive < 5
		BusDTO bus4 = new BusDTO("Nis express", 2L, 3);
		Bus dbBus4 = busService.create(bus4);
		assertThat(dbBus4).isNull();

	}

	// create a bus with a line that doesn't exist
	@Test
	public void createTest2() {
		BusDTO bus5 = new BusDTO("Nis express", 67L, 5);
		Bus dbBus5 = busService.create(bus5);
		assertThat(dbBus5).isNull();
	}

	@Test
	@Transactional
	@Rollback(value=true)
	public void updateTest() {

		Set<Date> dates = new HashSet<>();
		dates.add(new Date());
		Schedule s = new Schedule();
		s.setTimes(dates);
		List<Schedule> schedules = new ArrayList<>();
		schedules.add(s);
		Timetable t = new Timetable("kod", schedules);

		Bus bus = new Bus();
		bus.setId(1L);
		bus.setName("Novo ime");
		bus.setLate(true);
		bus.setTimetable(t);
		bus.setCode("7ca_bus_Novo ime");
		bus.setLine(lineService.getOne(1L));

		Bus dbBus = busService.update(bus);
		assertThat(dbBus).isNotNull();
		assertThat(dbBus.getId()).isEqualTo(bus.getId());
		assertThat(dbBus.getCode()).isEqualTo(bus.getCode());
		assertThat(dbBus.getName()).isEqualTo(bus.getName());
		assertThat(dbBus.isLate()).isEqualTo(bus.isLate());

		assertThat(dbBus.getLine().getId()).isEqualTo(bus.getLine().getId());
		assertThat(dbBus.getLine().getLine_type()).isEqualTo(bus.getLine().getLine_type());
		assertThat(dbBus.getLine().getName()).isEqualTo(bus.getLine().getName());
		assertThat(dbBus.getLine().getFirst_station()).isEqualTo(bus.getLine().getFirst_station());
		assertThat(dbBus.getLine().getStation_list()).isEqualTo(bus.getLine().getStation_list());
		assertThat(dbBus.getLine().getZone()).isEqualTo(bus.getLine().getZone());

		assertThat(dbBus.getTimetable().getCode()).isEqualTo("kod");

	}

	@Test
	@Transactional
	@Rollback(value=true)
	public void deleteTest() {

		boolean successful = busService.delete(2L);
		assertThat(successful).isNotNull();
		assertThat(successful).isEqualTo(true);

		Bus dbBus = busService.getOne(2L);
		assertThat(dbBus.isDeleted()).isEqualTo(true);

		// delete bus that doesn't exist
		boolean successful2 = busService.delete(10L);
		assertThat(successful2).isNotNull();
		assertThat(successful2).isEqualTo(false);
	}

	@Test
	public void checkLineTest() {

		Line dbLine = busService.checkLine(1L);
		assertThat(dbLine).isNotNull();
		assertThat(dbLine.getId()).isEqualTo(1L);
		assertThat(dbLine.getLine_type()).isEqualTo(TypeTransport.bus);
		assertThat(dbLine.getName()).isEqualTo("7ca");
		assertThat(dbLine.getZone()).isEqualTo(Zone.first);
		assertThat(dbLine.getFirst_station()).isEqualTo(1L);
		assertThat(dbLine.isDeleted()).isEqualTo(false);
		assertThat(dbLine.getStation_list().get(0).getId()).isEqualTo(1L);
		assertThat(dbLine.getStation_list().get(0).getZone()).isEqualTo(Zone.first);
		assertThat(dbLine.getStation_list().get(0).getLocation().getLocation_name()).isEqualTo("Rotkvarija");
		assertThat(dbLine.getStation_list().get(0).getLocation().getAddress())
				.isEqualTo("71, Bulevar oslobodjenja, Роткварија,"
						+ " Novi Sad, Novi Sad City, South Backa District, Vojvodina, 21101, Serbia");
		assertThat(dbLine.getStation_list().get(0).getLocation().getLatitude()).isEqualTo(45.2559f);
		assertThat(dbLine.getStation_list().get(0).getLocation().getLongitude()).isEqualTo(19.8349f);

		// line exist but it is not good type
		Line dbLine2 = busService.checkLine(2L);
		assertThat(dbLine2).isNull();

		// line doesn't exist
		Line dbLine3 = busService.checkLine(24353253453L);
		assertThat(dbLine3).isNull();

	}

	@Test
	public void codeExistTest() {
		// exist
		boolean codeExist = busService.codeExist("7ca_bus_lasta");
		assertThat(codeExist).isNotNull();
		assertThat(codeExist).isEqualTo(true);

		// doesn't exist
		boolean codeExist2 = busService.codeExist("neki kod");
		assertThat(codeExist2).isNotNull();
		assertThat(codeExist2).isEqualTo(false);

		// send null
		boolean codeExist3 = busService.codeExist(null);
		assertThat(codeExist3).isNotNull();
		assertThat(codeExist3).isEqualTo(false);

	}

	@Test
	@Transactional
	@Rollback(value=true)
	public void addLocationTest() {
		// location doesn't exist
		AddLocationDTO newLocation = new AddLocationDTO(1L, 63456362L);
		Bus dbBus = busService.addLocation(newLocation);
		assertThat(dbBus).isNull();

		// bus doesn't exist
		AddLocationDTO newLocation2 = new AddLocationDTO(7643636436L, 1L);
		Bus dbBus2 = busService.addLocation(newLocation2);
		assertThat(dbBus2).isNull();

		// correct
		AddLocationDTO newLocation3 = new AddLocationDTO(4L, 2L);
		Bus dbBus3 = busService.addLocation(newLocation3);
		assertThat(dbBus3.getLocation().getLocation_name()).isEqualTo("Banatic");
		assertThat(dbBus3.getLocation().getAddress()).isEqualTo(
				"150, Rumenacka, Банатић, Novi Sad, Novi Sad City, South Backa District, Vojvodina, 21138, Serbia");
		assertThat(dbBus3.getLocation().getLatitude()).isEqualTo(45.2652f);
		assertThat(dbBus3.getLocation().getLongitude()).isEqualTo(19.8159f);
		assertThat(dbBus3.getLocation().getType()).isEqualTo("station");

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

	@Test
	@Transactional
	@Rollback(value=true)
	public void deleteBecauseLineTest() {

		busService.deleteBecauseLine(1L);
		Bus b1 = busService.getOne(1L);
		Bus b2 = busService.getOne(2L);
		Bus b3 = busService.getOne(3L);
		Bus b4 = busService.getOne(4L);
		assertThat(b1.isDeleted()).isEqualTo(true);
		assertThat(b2.isDeleted()).isEqualTo(true);
		assertThat(b3.isDeleted()).isEqualTo(true);
		assertThat(b4.isDeleted()).isEqualTo(true);

	}

	@Test
	@Transactional
	@Rollback(value=true)
	public void changeTest() {

		Location l = new Location(4L, "nova lokacija", "adresa", 67.46f, 54.654f, "transport");
		Set<Date> dates = new HashSet<>();
		dates.add(new Date());
		Schedule s = new Schedule(dates);
		List<Schedule> schedules = new ArrayList<>();
		schedules.add(s);
		Timetable t = new Timetable("kod", schedules);

		// late is true
		ChangeTransportDTO changeData = new ChangeTransportDTO(1L, "novo ime", 7, l, t);
		Bus changedBus = busService.change(changeData);
		assertThat(changedBus).isNotNull();
		assertThat(changedBus.getId()).isEqualTo(changeData.getId_transport());
		assertThat(changedBus.getName()).isEqualTo(changeData.getName());
		assertThat(changedBus.isLate()).isEqualTo(true);
		assertThat(changedBus.getTime_arrive()).isEqualTo(changeData.getTime_arrive());
		assertThat(changedBus.getTimetable().getCode()).isEqualTo(changeData.getTimetable().getCode());
		assertThat(changedBus.getLocation().getAddress()).isEqualTo(changeData.getCurrent_location().getAddress());
		assertThat(changedBus.getLocation().getId()).isEqualTo(changeData.getCurrent_location().getId());
		assertThat(changedBus.getLocation().getLatitude()).isEqualTo(changeData.getCurrent_location().getLatitude());
		assertThat(changedBus.getLocation().getLocation_name())
				.isEqualTo(changeData.getCurrent_location().getLocation_name());
		assertThat(changedBus.getLocation().getLongitude()).isEqualTo(changeData.getCurrent_location().getLongitude());
		assertThat(changedBus.getLocation().getType()).isEqualTo(changeData.getCurrent_location().getType());
		assertThat(changedBus.getCode()).isEqualTo("7ca_bus_novo ime");


		// late is false
		ChangeTransportDTO changeData2 = new ChangeTransportDTO(1L, "novo ime", 5, l, t);
		Bus changedBus2 = busService.change(changeData2);
		assertThat(changedBus2).isNotNull();
		assertThat(changedBus2.getId()).isEqualTo(changeData2.getId_transport());
		assertThat(changedBus2.getName()).isEqualTo(changeData2.getName());
		assertThat(changedBus2.isLate()).isEqualTo(false);
		assertThat(changedBus2.getTime_arrive()).isEqualTo(changeData2.getTime_arrive());
		assertThat(changedBus2.getTimetable().getCode()).isEqualTo(changeData2.getTimetable().getCode());
		assertThat(changedBus2.getLocation().getAddress()).isEqualTo(changeData2.getCurrent_location().getAddress());
		assertThat(changedBus2.getLocation().getId()).isEqualTo(changeData2.getCurrent_location().getId());
		assertThat(changedBus2.getLocation().getLatitude()).isEqualTo(changeData2.getCurrent_location().getLatitude());
		assertThat(changedBus2.getLocation().getLocation_name())
				.isEqualTo(changeData2.getCurrent_location().getLocation_name());
		assertThat(changedBus2.getLocation().getLongitude())
				.isEqualTo(changeData2.getCurrent_location().getLongitude());
		assertThat(changedBus2.getLocation().getType()).isEqualTo(changeData2.getCurrent_location().getType());
		assertThat(changedBus2.getCode()).isEqualTo("7ca_bus_novo ime");

	}

	@Test(expected = NoSuchElementException.class)
	public void changeTest2() {
		// bus doesn't exist
		Location l = new Location(4L, "nova lokacija", "adresa", 67.46f, 54.654f, "transport");
		Set<Date> dates = new HashSet<>();
		dates.add(new Date());
		Schedule s = new Schedule(dates);
		List<Schedule> schedules = new ArrayList<>();
		schedules.add(s);
		Timetable t = new Timetable("kod", schedules);

		ChangeTransportDTO changeData = new ChangeTransportDTO(45464646464L, "novo ime", 5, l, t);
		busService.change(changeData);
	}

	@Test
	public void asearchFilterTest() {
		// filter only line
		FilterSearchTransportDTO filterSearch = new FilterSearchTransportDTO(1L, false, null, "");
		List<Bus> listBus = busService.searchFilter(filterSearch);
		assertThat(listBus.size()).isEqualTo(4);

		// filter only late
		FilterSearchTransportDTO filterSearch2 = new FilterSearchTransportDTO(null, true, null, "");
		List<Bus> listBus2 = busService.searchFilter(filterSearch2);
		assertThat(listBus2.size()).isEqualTo(1);

		// filter only current location
		FilterSearchTransportDTO filterSearch3 = new FilterSearchTransportDTO(null, false, 2L, "");
		List<Bus> listBus3 = busService.searchFilter(filterSearch3);
		assertThat(listBus3.size()).isEqualTo(2);

		// search only name
		FilterSearchTransportDTO filterSearch4 = new FilterSearchTransportDTO(null, false, null, "n");
		List<Bus> listBus4 = busService.searchFilter(filterSearch4);
		assertThat(listBus4.size()).isEqualTo(2);

		// filter and search combine
		FilterSearchTransportDTO filterSearch5 = new FilterSearchTransportDTO(1L, false, 2L, "la");
		List<Bus> listBus5 = busService.searchFilter(filterSearch5);
		assertThat(listBus5.size()).isEqualTo(1);

	}

}

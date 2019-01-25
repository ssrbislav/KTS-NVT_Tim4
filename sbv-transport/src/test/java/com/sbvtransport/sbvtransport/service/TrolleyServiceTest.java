package com.sbvtransport.sbvtransport.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import com.sbvtransport.sbvtransport.dto.AddLocationDTO;
import com.sbvtransport.sbvtransport.dto.ChangeTransportDTO;
import com.sbvtransport.sbvtransport.dto.FilterSearchTransportDTO;
import com.sbvtransport.sbvtransport.dto.TrolleyDTO;
import com.sbvtransport.sbvtransport.enumeration.TypeTransport;
import com.sbvtransport.sbvtransport.enumeration.Zone;
import com.sbvtransport.sbvtransport.model.Line;
import com.sbvtransport.sbvtransport.model.Location;
import com.sbvtransport.sbvtransport.model.Schedule;
import com.sbvtransport.sbvtransport.model.Timetable;
import com.sbvtransport.sbvtransport.model.Trolley;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
@Rollback(value=true)
public class TrolleyServiceTest {

	@Autowired
	private ITrolleyService trolleyService;

	@Autowired
	private ILineService lineService;

	@Test
	public void findAllTest() {
		List<Trolley> trolleys = trolleyService.findAll();
		assertThat(trolleys).hasSize(4);

	}

	@Test
	public void getOneTest() {

		Trolley findTrolley = trolleyService.getOne(1L);

		assertThat(findTrolley).isNotNull();
		assertThat(findTrolley.getId()).isEqualTo(1L);
		assertThat(findTrolley.getCode()).isEqualTo("7ca_trolley_lasta");
		assertThat(findTrolley.getName()).isEqualTo("lasta");
		assertThat(findTrolley.isLate()).isEqualTo(false);
		assertThat(findTrolley.isDeleted()).isEqualTo(false);
		assertThat(findTrolley.getTime_arrive()).isEqualTo(5);

		assertThat(findTrolley.getLine().getId()).isEqualTo(3L);
		assertThat(findTrolley.getLine().getLine_type()).isEqualTo(TypeTransport.trolley);
		assertThat(findTrolley.getLine().getName()).isEqualTo("7ca");
		assertThat(findTrolley.getLine().getZone()).isEqualTo(Zone.first);
		assertThat(findTrolley.getLine().getFirst_station()).isEqualTo(3L);
		assertThat(findTrolley.getLine().isDeleted()).isEqualTo(false);
		assertThat(findTrolley.getLine().getStation_list().get(0).getId()).isEqualTo(2L);
		assertThat(findTrolley.getLine().getStation_list().get(0).getZone()).isEqualTo(Zone.first);
		assertThat(findTrolley.getLine().getStation_list().get(0).getLocation().getLocation_name())
				.isEqualTo("Banatic");
		assertThat(findTrolley.getLine().getStation_list().get(0).getLocation().getAddress()).isEqualTo(
				"150, Rumenacka, Банатић, Novi Sad, Novi Sad City, South Backa District, Vojvodina, 21138, Serbia");
		assertThat(findTrolley.getLine().getStation_list().get(0).getLocation().getLatitude()).isEqualTo(45.2652f);
		assertThat(findTrolley.getLine().getStation_list().get(0).getLocation().getLongitude()).isEqualTo(19.8159f);

		assertThat(findTrolley.getLocation().getLocation_name()).isEqualTo("Banatic");
		assertThat(findTrolley.getLocation().getAddress()).isEqualTo(
				"150, Rumenacka, Банатић, Novi Sad, Novi Sad City, South Backa District, Vojvodina, 21138, Serbia");
		assertThat(findTrolley.getLocation().getLatitude()).isEqualTo(45.2652f);
		assertThat(findTrolley.getLocation().getLongitude()).isEqualTo(19.8159f);
		assertThat(findTrolley.getLocation().getType()).isEqualTo("station");

		// find bus that doesn't exist
		Trolley findtrolley2 = trolleyService.getOne(10L);
		assertThat(findtrolley2).isNull();

	}

	@Test
	@Transactional
	@Rollback(value=true)
	public void createTest() {

		TrolleyDTO trolley = new TrolleyDTO("lasta2", 3L, 5);

		int dbSizeBeforeAdd = trolleyService.findAll().size();

		Trolley dbTrolley = trolleyService.create(trolley);
		assertThat(dbTrolley).isNotNull();

		List<Trolley> trolleys = trolleyService.findAll();
		assertThat(trolleys).hasSize(dbSizeBeforeAdd + 1);
		assertThat(dbTrolley.getCode()).isEqualTo("7ca_trolley_lasta2");
		assertThat(dbTrolley.getId()).isEqualTo(5L);
		assertThat(dbTrolley.getName()).isEqualTo(trolley.getName());
		assertThat(dbTrolley.isLate()).isEqualTo(false);
		assertThat(dbTrolley.isDeleted()).isEqualTo(false);
		assertThat(dbTrolley.getTime_arrive()).isEqualTo(trolley.getTime_arrive());
		assertThat(dbTrolley.getTimetable()).isEqualTo(null);
		assertThat(dbTrolley.getLocation()).isEqualTo(null);

		assertThat(dbTrolley.getLine().getId()).isEqualTo(3L);
		assertThat(dbTrolley.getLine().getLine_type()).isEqualTo(TypeTransport.trolley);
		assertThat(dbTrolley.getLine().getName()).isEqualTo("7ca");
		assertThat(dbTrolley.getLine().getZone()).isEqualTo(Zone.first);
		assertThat(dbTrolley.getLine().getFirst_station()).isEqualTo(2L);
		assertThat(dbTrolley.getLine().isDeleted()).isEqualTo(false);

		// create a trolley with a line that isn't a correct type
		TrolleyDTO trolley3 = new TrolleyDTO("Nis express", 2L, 6);
		Trolley dbTrolley3 = trolleyService.create(trolley3);
		assertThat(dbTrolley3).isNull();

		// create a trolley with a time arrive < 5
		TrolleyDTO trolley4 = new TrolleyDTO("Nis express", 2L, 3);
		Trolley dbTrolley4 = trolleyService.create(trolley4);
		assertThat(dbTrolley4).isNull();

	}

	// create a trolley with a line that doesn't exist
	@Test
	public void createTest2() {
		TrolleyDTO trolley2 = new TrolleyDTO("novi", 35353253L, 7);
		Trolley newTrolley = trolleyService.create(trolley2);
		assertThat(newTrolley).isNull();

	}

	// change a test for update because you need to change a update depending
	// the angular
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

		Trolley trolley = new Trolley();
		trolley.setId(1L);
		trolley.setName("Novo ime");
		trolley.setLate(true);
		trolley.setTimetable(t);
		trolley.setCode("7ca_trolley_Novo ime");
		trolley.setLine(lineService.getOne(3L));

		Trolley dbTrolley = trolleyService.update(trolley);
		assertThat(dbTrolley).isNotNull();
		assertThat(dbTrolley.getId()).isEqualTo(trolley.getId());
		assertThat(dbTrolley.getCode()).isEqualTo(trolley.getCode());
		assertThat(dbTrolley.getName()).isEqualTo(trolley.getName());
		assertThat(dbTrolley.isLate()).isEqualTo(trolley.isLate());

		assertThat(dbTrolley.getLine().getId()).isEqualTo(trolley.getLine().getId());
		assertThat(dbTrolley.getLine().getLine_type()).isEqualTo(trolley.getLine().getLine_type());
		assertThat(dbTrolley.getLine().getName()).isEqualTo(trolley.getLine().getName());
		assertThat(dbTrolley.getLine().getFirst_station()).isEqualTo(trolley.getLine().getFirst_station());
		assertThat(dbTrolley.getLine().getStation_list()).isEqualTo(trolley.getLine().getStation_list());
		assertThat(dbTrolley.getLine().getZone()).isEqualTo(trolley.getLine().getZone());

		assertThat(dbTrolley.getTimetable().getCode()).isEqualTo("kod");

	}

	@Test
	@Transactional
	@Rollback(value=true)
	public void deleteTest() {

		boolean successful = trolleyService.delete(2L);
		assertThat(successful).isNotNull();
		assertThat(successful).isEqualTo(true);

		Trolley dbTrolley = trolleyService.getOne(2L);
		assertThat(dbTrolley.isDeleted()).isEqualTo(true);

		// delete trolley that doesn't exist
		boolean successful2 = trolleyService.delete(10L);
		assertThat(successful2).isNotNull();
		assertThat(successful2).isEqualTo(false);
	}

	@Test
	public void checkLineTest() {

		Line dbLine = trolleyService.checkLine(3L);
		assertThat(dbLine).isNotNull();
		assertThat(dbLine.getId()).isEqualTo(3L);
		assertThat(dbLine.getLine_type()).isEqualTo(TypeTransport.trolley);
		assertThat(dbLine.getName()).isEqualTo("7ca");
		assertThat(dbLine.getZone()).isEqualTo(Zone.first);
		assertThat(dbLine.getFirst_station()).isEqualTo(2L);
		assertThat(dbLine.isDeleted()).isEqualTo(false);
		assertThat(dbLine.getStation_list().get(0).getId()).isEqualTo(2L);
		assertThat(dbLine.getStation_list().get(0).getZone()).isEqualTo(Zone.first);
		assertThat(dbLine.getStation_list().get(0).getLocation().getLocation_name()).isEqualTo("Banatic");
		assertThat(dbLine.getStation_list().get(0).getLocation().getAddress()).isEqualTo(
				"150, Rumenacka, Банатић, Novi Sad, Novi Sad City, South Backa District, Vojvodina, 21138, Serbia");
		assertThat(dbLine.getStation_list().get(0).getLocation().getLatitude()).isEqualTo(45.2652f);
		assertThat(dbLine.getStation_list().get(0).getLocation().getLongitude()).isEqualTo(19.8159f);

		// line exist but it is not good type
		Line dbLine2 = trolleyService.checkLine(2L);
		assertThat(dbLine2).isNull();

		// line doesn't exist
		Line dbLine3 = trolleyService.checkLine(325353252L);
		assertThat(dbLine3).isNull();

	}

	@Test
	public void codeExistTest() {
		// exist
		boolean codeExist = trolleyService.codeExist("7ca_trolley_lasta");
		assertThat(codeExist).isNotNull();
		assertThat(codeExist).isEqualTo(true);

		// doesn't exist
		boolean codeExist2 = trolleyService.codeExist("neki kod");
		assertThat(codeExist2).isNotNull();
		assertThat(codeExist2).isEqualTo(false);

		// send null
		boolean codeExist3 = trolleyService.codeExist(null);
		assertThat(codeExist3).isNotNull();
		assertThat(codeExist3).isEqualTo(false);

	}

	@Test
	@Transactional
	@Rollback(value=true)
	public void addLocationTest() {
		// location doesn't exist
		AddLocationDTO newLocation = new AddLocationDTO(1L, 63456362L);
		Trolley dbTrolley = trolleyService.addLocation(newLocation);
		assertThat(dbTrolley).isNull();

		// bus doesn't exist
		AddLocationDTO newLocation2 = new AddLocationDTO(7643636436L, 1L);
		Trolley dbTrolley2 = trolleyService.addLocation(newLocation2);
		assertThat(dbTrolley2).isNull();

		// correct
		AddLocationDTO newLocation3 = new AddLocationDTO(4L, 2L);
		Trolley dbTrolley3 = trolleyService.addLocation(newLocation3);
		assertThat(dbTrolley3.getLocation().getLocation_name()).isEqualTo("Banatic");
		assertThat(dbTrolley3.getLocation().getAddress()).isEqualTo(
				"150, Rumenacka, Банатић, Novi Sad, Novi Sad City, South Backa District, Vojvodina, 21138, Serbia");
		assertThat(dbTrolley3.getLocation().getLatitude()).isEqualTo(45.2652f);
		assertThat(dbTrolley3.getLocation().getLongitude()).isEqualTo(19.8159f);
		assertThat(dbTrolley3.getLocation().getType()).isEqualTo("station");

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

	@Test
	@Transactional
	@Rollback(value=true)
	public void deleteBecauseLineTest() {

		trolleyService.deleteBecauseLine(3L);
		Trolley t1 = trolleyService.getOne(1L);
		Trolley t2 = trolleyService.getOne(2L);
		Trolley t3 = trolleyService.getOne(3L);
		Trolley t4 = trolleyService.getOne(4L);
		assertThat(t1.isDeleted()).isEqualTo(true);
		assertThat(t2.isDeleted()).isEqualTo(true);
		assertThat(t3.isDeleted()).isEqualTo(true);
		assertThat(t4.isDeleted()).isEqualTo(true);

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
		Trolley changedTrolley = trolleyService.change(changeData);
		assertThat(changedTrolley).isNotNull();
		assertThat(changedTrolley.getId()).isEqualTo(changeData.getId_transport());
		assertThat(changedTrolley.getName()).isEqualTo(changeData.getName());
		assertThat(changedTrolley.isLate()).isEqualTo(true);
		assertThat(changedTrolley.getTime_arrive()).isEqualTo(changeData.getTime_arrive());
		assertThat(changedTrolley.getTimetable().getCode()).isEqualTo(changeData.getTimetable().getCode());
		assertThat(changedTrolley.getLocation().getAddress()).isEqualTo(changeData.getCurrent_location().getAddress());
		assertThat(changedTrolley.getLocation().getId()).isEqualTo(changeData.getCurrent_location().getId());
		assertThat(changedTrolley.getLocation().getLatitude())
				.isEqualTo(changeData.getCurrent_location().getLatitude());
		assertThat(changedTrolley.getLocation().getLocation_name())
				.isEqualTo(changeData.getCurrent_location().getLocation_name());
		assertThat(changedTrolley.getLocation().getLongitude())
				.isEqualTo(changeData.getCurrent_location().getLongitude());
		assertThat(changedTrolley.getLocation().getType()).isEqualTo(changeData.getCurrent_location().getType());
		assertThat(changedTrolley.getCode()).isEqualTo("7ca_trolley_novo ime");

		// late is false
		ChangeTransportDTO changeData2 = new ChangeTransportDTO(1L, "novo ime", 5, l, t);
		Trolley changedTrolley2 = trolleyService.change(changeData2);
		assertThat(changedTrolley2).isNotNull();
		assertThat(changedTrolley2.getId()).isEqualTo(changeData2.getId_transport());
		assertThat(changedTrolley2.getName()).isEqualTo(changeData2.getName());
		assertThat(changedTrolley2.isLate()).isEqualTo(false);
		assertThat(changedTrolley2.getTime_arrive()).isEqualTo(changeData2.getTime_arrive());
		assertThat(changedTrolley2.getTimetable().getCode()).isEqualTo(changeData2.getTimetable().getCode());
		assertThat(changedTrolley2.getLocation().getAddress())
				.isEqualTo(changeData2.getCurrent_location().getAddress());
		assertThat(changedTrolley2.getLocation().getId()).isEqualTo(changeData2.getCurrent_location().getId());
		assertThat(changedTrolley2.getLocation().getLatitude())
				.isEqualTo(changeData2.getCurrent_location().getLatitude());
		assertThat(changedTrolley2.getLocation().getLocation_name())
				.isEqualTo(changeData2.getCurrent_location().getLocation_name());
		assertThat(changedTrolley2.getLocation().getLongitude())
				.isEqualTo(changeData2.getCurrent_location().getLongitude());
		assertThat(changedTrolley2.getLocation().getType()).isEqualTo(changeData2.getCurrent_location().getType());
		assertThat(changedTrolley2.getCode()).isEqualTo("7ca_trolley_novo ime");

	}

	@Test(expected = NoSuchElementException.class)
	public void changeTest2() {
		// trolley doesn't exist
		Location l = new Location(4L, "nova lokacija", "adresa", 67.46f, 54.654f, "transport");
		Set<Date> dates = new HashSet<>();
		dates.add(new Date());
		Schedule s = new Schedule(dates);
		List<Schedule> schedules = new ArrayList<>();
		schedules.add(s);
		Timetable t = new Timetable("kod", schedules);

		ChangeTransportDTO changeData = new ChangeTransportDTO(45464646464L, "novo ime", 5, l, t);
		trolleyService.change(changeData);
	}
	
	@Test
	public void searchFilterTest() {
		// filter only line
		FilterSearchTransportDTO filterSearch = new FilterSearchTransportDTO(3L, false, null, "");
		List<Trolley> listTrolley = trolleyService.searchFilter(filterSearch);
		assertThat(listTrolley.size()).isEqualTo(4);

		// filter only late
		FilterSearchTransportDTO filterSearch2 = new FilterSearchTransportDTO(null, true, null, "");
		List<Trolley> listTrolley2 = trolleyService.searchFilter(filterSearch2);
		assertThat(listTrolley2.size()).isEqualTo(1);

		// filter only current location
		FilterSearchTransportDTO filterSearch3 = new FilterSearchTransportDTO(null, false, 2L, "");
		List<Trolley> listTrolley3 = trolleyService.searchFilter(filterSearch3);
		assertThat(listTrolley3.size()).isEqualTo(2);

		// search only name
		FilterSearchTransportDTO filterSearch4 = new FilterSearchTransportDTO(null, false, null, "s");
		List<Trolley> listTrolley4 = trolleyService.searchFilter(filterSearch4);
		assertThat(listTrolley4.size()).isEqualTo(2);

		// filter and search combine
		FilterSearchTransportDTO filterSearch5 = new FilterSearchTransportDTO(3L, false, 2L, "du");
		List<Trolley> listTrolley5 = trolleyService.searchFilter(filterSearch5);
		assertThat(listTrolley5.size()).isEqualTo(1);

	}

}

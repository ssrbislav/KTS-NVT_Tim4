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
import com.sbvtransport.sbvtransport.dto.SubwayDTO;
import com.sbvtransport.sbvtransport.enumeration.TypeTransport;
import com.sbvtransport.sbvtransport.enumeration.Zone;
import com.sbvtransport.sbvtransport.model.Line;
import com.sbvtransport.sbvtransport.model.Location;
import com.sbvtransport.sbvtransport.model.Schedule;
import com.sbvtransport.sbvtransport.model.Subway;
import com.sbvtransport.sbvtransport.model.Timetable;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
@Rollback(value=true)
public class SubwayServiceTest {

	@Autowired
	private ISubwayService subwayService;

	@Autowired
	private ILineService lineService;

	@Test
	public void findAllTest() {
		List<Subway> subways = subwayService.findAll();
		assertThat(subways).hasSize(4);

	}

	@Test
	public void getOneTest() {

		Subway findSubway = subwayService.getOne(1L);

		assertThat(findSubway).isNotNull();
		assertThat(findSubway.getId()).isEqualTo(1L);
		assertThat(findSubway.getCode()).isEqualTo("7ca_subway_Nis_expres");
		assertThat(findSubway.getName()).isEqualTo("Nis_expres");
		assertThat(findSubway.isLate()).isEqualTo(false);
		assertThat(findSubway.isDeleted()).isEqualTo(false);
		assertThat(findSubway.getTime_arrive()).isEqualTo(5);

		assertThat(findSubway.getLine().getId()).isEqualTo(2L);
		assertThat(findSubway.getLine().getLine_type()).isEqualTo(TypeTransport.subway);
		assertThat(findSubway.getLine().getName()).isEqualTo("7ca");
		assertThat(findSubway.getLine().getZone()).isEqualTo(Zone.first);
		assertThat(findSubway.getLine().getFirst_station()).isEqualTo(2L);
		assertThat(findSubway.getLine().isDeleted()).isEqualTo(false);
		assertThat(findSubway.getLine().getStation_list().get(0).getId()).isEqualTo(2L);
		assertThat(findSubway.getLine().getStation_list().get(0).getZone()).isEqualTo(Zone.first);
		assertThat(findSubway.getLine().getStation_list().get(0).getLocation().getLocation_name()).isEqualTo("Banatic");
		assertThat(findSubway.getLine().getStation_list().get(0).getLocation().getAddress()).isEqualTo(
				"150, Rumenacka, Банатић, Novi Sad, Novi Sad City, South Backa District, Vojvodina, 21138, Serbia");
		assertThat(findSubway.getLine().getStation_list().get(0).getLocation().getLatitude()).isEqualTo(45.2652f);
		assertThat(findSubway.getLine().getStation_list().get(0).getLocation().getLongitude()).isEqualTo(19.8159f);

		assertThat(findSubway.getLocation().getLocation_name()).isEqualTo("Banatic");
		assertThat(findSubway.getLocation().getAddress()).isEqualTo(
				"150, Rumenacka, Банатић, Novi Sad, Novi Sad City, South Backa District, Vojvodina, 21138, Serbia");
		assertThat(findSubway.getLocation().getLatitude()).isEqualTo(45.2652f);
		assertThat(findSubway.getLocation().getLongitude()).isEqualTo(19.8159f);
		assertThat(findSubway.getLocation().getType()).isEqualTo("station");

		// find bus that doesn't exist
		Subway findsubway2 = subwayService.getOne(10L);
		assertThat(findsubway2).isNull();

	}

	@Test
	@Transactional
	@Rollback(value=true)
	public void createTest() {
		SubwayDTO subway = new SubwayDTO("lasta2", 2L, 5);

		int dbSizeBeforeAdd = subwayService.findAll().size();

		Subway dbSubway = subwayService.create(subway);
		assertThat(dbSubway).isNotNull();

		List<Subway> subways = subwayService.findAll();
		assertThat(subways).hasSize(dbSizeBeforeAdd + 1);
		assertThat(dbSubway.getCode()).isEqualTo("7ca_subway_lasta2");
		assertThat(dbSubway.getId()).isEqualTo(5L);
		assertThat(dbSubway.getName()).isEqualTo(subway.getName());
		assertThat(dbSubway.isLate()).isEqualTo(false);
		assertThat(dbSubway.isDeleted()).isEqualTo(false);
		assertThat(dbSubway.getTime_arrive()).isEqualTo(subway.getTime_arrive());
		assertThat(dbSubway.getTimetable()).isEqualTo(null);
		assertThat(dbSubway.getLocation()).isEqualTo(null);

		assertThat(dbSubway.getLine().getId()).isEqualTo(2L);
		assertThat(dbSubway.getLine().getLine_type()).isEqualTo(TypeTransport.subway);
		assertThat(dbSubway.getLine().getName()).isEqualTo("7ca");
		assertThat(dbSubway.getLine().getZone()).isEqualTo(Zone.first);
		assertThat(dbSubway.getLine().getFirst_station()).isEqualTo(2L);
		assertThat(dbSubway.getLine().isDeleted()).isEqualTo(false);

		// create a subway with a line that isn't a correct type
		SubwayDTO subway3 = new SubwayDTO("Nis express", 1L, 6);
		Subway dbSubway3 = subwayService.create(subway3);
		assertThat(dbSubway3).isNull();

		// create a bus with a time arrive < 5
		SubwayDTO subway4 = new SubwayDTO("Nis express", 2L, 3);
		Subway dbSubway4 = subwayService.create(subway4);
		assertThat(dbSubway4).isNull();

	}

	// create a subway with a line that doesn't exist
	@Test
	public void createTest2() {
		SubwayDTO subway5 = new SubwayDTO("Nis express", 67L, 5);
		Subway dbSubway5 = subwayService.create(subway5);
		assertThat(dbSubway5).isNull();
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

		Subway subway = new Subway();
		subway.setId(1L);
		subway.setName("Novo ime");
		subway.setLate(true);
		subway.setTimetable(t);
		subway.setCode("7ca_subway_Novo ime");
		subway.setLine(lineService.getOne(2L));

		Subway dbSubway = subwayService.update(subway);
		assertThat(dbSubway).isNotNull();
		assertThat(dbSubway.getId()).isEqualTo(subway.getId());
		assertThat(dbSubway.getCode()).isEqualTo(subway.getCode());
		assertThat(dbSubway.getName()).isEqualTo(subway.getName());
		assertThat(dbSubway.isLate()).isEqualTo(subway.isLate());

		assertThat(dbSubway.getLine().getId()).isEqualTo(subway.getLine().getId());
		assertThat(dbSubway.getLine().getLine_type()).isEqualTo(subway.getLine().getLine_type());
		assertThat(dbSubway.getLine().getName()).isEqualTo(subway.getLine().getName());
		assertThat(dbSubway.getLine().getFirst_station()).isEqualTo(subway.getLine().getFirst_station());
		assertThat(dbSubway.getLine().getStation_list()).isEqualTo(subway.getLine().getStation_list());
		assertThat(dbSubway.getLine().getZone()).isEqualTo(subway.getLine().getZone());

		assertThat(dbSubway.getTimetable().getCode()).isEqualTo("kod");

	}

	@Test
	@Transactional
	@Rollback(value=true)
	public void deleteTest() {

		boolean successful = subwayService.delete(2L);
		assertThat(successful).isNotNull();
		assertThat(successful).isEqualTo(true);

		Subway dbSubway = subwayService.getOne(2L);
		assertThat(dbSubway.isDeleted()).isEqualTo(true);

		// delete subway that doesn't exist
		boolean successful2 = subwayService.delete(10L);
		assertThat(successful2).isNotNull();
		assertThat(successful2).isEqualTo(false);
	}

	@Test
	public void checkLineTest() {

		Line dbLine = subwayService.checkLine(2L);
		assertThat(dbLine).isNotNull();
		assertThat(dbLine.getId()).isEqualTo(2L);
		assertThat(dbLine.getLine_type()).isEqualTo(TypeTransport.subway);
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
		Line dbLine2 = subwayService.checkLine(1L);
		assertThat(dbLine2).isNull();

		// line doesn't exist
		Line dbLine3 = subwayService.checkLine(24353253453L);
		assertThat(dbLine3).isNull();

	}

	@Test
	public void codeExistTest() {
		// exist
		boolean codeExist = subwayService.codeExist("7ca_subway_Nis_expres");
		assertThat(codeExist).isNotNull();
		assertThat(codeExist).isEqualTo(true);

		// doesn't exist
		boolean codeExist2 = subwayService.codeExist("neki kod");
		assertThat(codeExist2).isNotNull();
		assertThat(codeExist2).isEqualTo(false);

		// send null
		boolean codeExist3 = subwayService.codeExist(null);
		assertThat(codeExist3).isNotNull();
		assertThat(codeExist3).isEqualTo(false);

	}

	@Test
	@Transactional
	@Rollback(value=true)
	public void addLocationTest() {
		// location doesn't exist
		AddLocationDTO newLocation = new AddLocationDTO(1L, 63456362L);
		Subway dbSubway = subwayService.addLocation(newLocation);
		assertThat(dbSubway).isNull();

		// bus doesn't exist
		AddLocationDTO newLocation2 = new AddLocationDTO(7643636436L, 1L);
		Subway dbSubway2 = subwayService.addLocation(newLocation2);
		assertThat(dbSubway2).isNull();

		// correct
		AddLocationDTO newLocation3 = new AddLocationDTO(4L, 2L);
		Subway dbSubway3 = subwayService.addLocation(newLocation3);
		assertThat(dbSubway3.getLocation().getLocation_name()).isEqualTo("Banatic");
		assertThat(dbSubway3.getLocation().getAddress()).isEqualTo(
				"150, Rumenacka, Банатић, Novi Sad, Novi Sad City, South Backa District, Vojvodina, 21138, Serbia");
		assertThat(dbSubway3.getLocation().getLatitude()).isEqualTo(45.2652f);
		assertThat(dbSubway3.getLocation().getLongitude()).isEqualTo(19.8159f);
		assertThat(dbSubway3.getLocation().getType()).isEqualTo("station");

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

	@Test
	@Transactional
	@Rollback(value=true)
	public void deleteBecauseLineTest() {

		subwayService.deleteBecauseLine(2L);
		Subway s1 = subwayService.getOne(1L);
		Subway s2 = subwayService.getOne(2L);
		Subway s3 = subwayService.getOne(3L);
		Subway s4 = subwayService.getOne(4L);
		assertThat(s1.isDeleted()).isEqualTo(true);
		assertThat(s2.isDeleted()).isEqualTo(true);
		assertThat(s3.isDeleted()).isEqualTo(true);
		assertThat(s4.isDeleted()).isEqualTo(true);

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
		Subway changedSubway = subwayService.change(changeData);
		assertThat(changedSubway).isNotNull();
		assertThat(changedSubway.getId()).isEqualTo(changeData.getId_transport());
		assertThat(changedSubway.getName()).isEqualTo(changeData.getName());
		assertThat(changedSubway.isLate()).isEqualTo(true);
		assertThat(changedSubway.getTime_arrive()).isEqualTo(changeData.getTime_arrive());
		assertThat(changedSubway.getTimetable().getCode()).isEqualTo(changeData.getTimetable().getCode());
		assertThat(changedSubway.getLocation().getAddress()).isEqualTo(changeData.getCurrent_location().getAddress());
		assertThat(changedSubway.getLocation().getId()).isEqualTo(changeData.getCurrent_location().getId());
		assertThat(changedSubway.getLocation().getLatitude()).isEqualTo(changeData.getCurrent_location().getLatitude());
		assertThat(changedSubway.getLocation().getLocation_name())
				.isEqualTo(changeData.getCurrent_location().getLocation_name());
		assertThat(changedSubway.getLocation().getLongitude())
				.isEqualTo(changeData.getCurrent_location().getLongitude());
		assertThat(changedSubway.getLocation().getType()).isEqualTo(changeData.getCurrent_location().getType());
		assertThat(changedSubway.getCode()).isEqualTo("7ca_subway_novo ime");

		// late is false
		ChangeTransportDTO changeData2 = new ChangeTransportDTO(1L, "novo ime", 5, l, t);
		Subway changedSubway2 = subwayService.change(changeData2);
		assertThat(changedSubway2).isNotNull();
		assertThat(changedSubway2.getId()).isEqualTo(changeData2.getId_transport());
		assertThat(changedSubway2.getName()).isEqualTo(changeData2.getName());
		assertThat(changedSubway2.isLate()).isEqualTo(false);
		assertThat(changedSubway2.getTime_arrive()).isEqualTo(changeData2.getTime_arrive());
		assertThat(changedSubway2.getTimetable().getCode()).isEqualTo(changeData2.getTimetable().getCode());
		assertThat(changedSubway2.getLocation().getAddress()).isEqualTo(changeData2.getCurrent_location().getAddress());
		assertThat(changedSubway2.getLocation().getId()).isEqualTo(changeData2.getCurrent_location().getId());
		assertThat(changedSubway2.getLocation().getLatitude())
				.isEqualTo(changeData2.getCurrent_location().getLatitude());
		assertThat(changedSubway2.getLocation().getLocation_name())
				.isEqualTo(changeData2.getCurrent_location().getLocation_name());
		assertThat(changedSubway2.getLocation().getLongitude())
				.isEqualTo(changeData2.getCurrent_location().getLongitude());
		assertThat(changedSubway2.getLocation().getType()).isEqualTo(changeData2.getCurrent_location().getType());
		assertThat(changedSubway2.getCode()).isEqualTo("7ca_subway_novo ime");

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
		subwayService.change(changeData);
	}

	@Test
	public void searchFilterTest() {
		// filter only line
		FilterSearchTransportDTO filterSearch = new FilterSearchTransportDTO(2L, false, null, "");
		List<Subway> listBus = subwayService.searchFilter(filterSearch);
		assertThat(listBus.size()).isEqualTo(4);

		// filter only late
		FilterSearchTransportDTO filterSearch2 = new FilterSearchTransportDTO(null, true, null, "");
		List<Subway> listBus2 = subwayService.searchFilter(filterSearch2);
		assertThat(listBus2.size()).isEqualTo(1);

		// filter only current location
		FilterSearchTransportDTO filterSearch3 = new FilterSearchTransportDTO(null, false, 2L, "");
		List<Subway> listBus3 = subwayService.searchFilter(filterSearch3);
		assertThat(listBus3.size()).isEqualTo(2);

		// search only name
		FilterSearchTransportDTO filterSearch4 = new FilterSearchTransportDTO(null, false, null, "n");
		List<Subway> listBus4 = subwayService.searchFilter(filterSearch4);
		assertThat(listBus4.size()).isEqualTo(3);

		// filter and search combine
		FilterSearchTransportDTO filterSearch5 = new FilterSearchTransportDTO(2L, false, 2L, "z");
		List<Subway> listBus5 = subwayService.searchFilter(filterSearch5);
		assertThat(listBus5.size()).isEqualTo(1);

	}

}

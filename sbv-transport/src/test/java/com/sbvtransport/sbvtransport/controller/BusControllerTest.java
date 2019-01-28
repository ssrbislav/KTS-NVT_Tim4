package com.sbvtransport.sbvtransport.controller;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import com.sbvtransport.sbvtransport.TestUtil;
import com.sbvtransport.sbvtransport.dto.AddLocationDTO;
import com.sbvtransport.sbvtransport.dto.BusDTO;
import com.sbvtransport.sbvtransport.dto.ChangeTransportDTO;
import com.sbvtransport.sbvtransport.dto.FilterSearchTransportDTO;
import com.sbvtransport.sbvtransport.model.Location;
import com.sbvtransport.sbvtransport.model.Schedule;
import com.sbvtransport.sbvtransport.model.Timetable;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
@Rollback(value=true)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BusControllerTest {

	private static final String URL_PREFIX = "/api/bus";

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@PostConstruct
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void agetAll() throws Exception {
		mockMvc.perform(get(URL_PREFIX)).andExpect(status().isOk()).andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$", hasSize(4))).andExpect(jsonPath("$.[*].id").value(hasItem(1)))
				.andExpect(jsonPath("$.[*].code").value(hasItem("7ca_bus_lasta")))
				.andExpect(jsonPath("$.[*].late").value(hasItem(false)))
				.andExpect(jsonPath("$.[*].line.id").value(hasItem(1)))
				.andExpect(jsonPath("$.[*].line.name").value(hasItem("7ca")))
				.andExpect(jsonPath("$.[*].line.line_type").value(hasItem("bus")))
				.andExpect(jsonPath("$.[*].line.zone").value(hasItem("first")))
				.andExpect(jsonPath("$.[*].line.first_station").value(hasItem(1)))
				.andExpect(jsonPath("$.[*].location.location_name").value(hasItem("Banatic")))
				.andExpect(jsonPath("$.[*].name").value(hasItem("lasta")));

	}

	// good value
	@Test
	public void getOneTest() throws Exception {
		mockMvc.perform(get(URL_PREFIX + "/getBus/3")).andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$.id").value(3L)).andExpect(jsonPath("$.code").value("7ca_bus_Nis_expres"))
				.andExpect(jsonPath("$.late").value(false)).andExpect(jsonPath("$.line.id").value(1))
				.andExpect(jsonPath("$.line.name").value("7ca")).andExpect(jsonPath("$.line.zone").value("first"))
				.andExpect(jsonPath("$.line.first_station").value(1))
				.andExpect(jsonPath("$.location.location_name").value("Banatic"))
				.andExpect(jsonPath("$.location.address")
						.value("150, Rumenacka, Банатић, Novi Sad, Novi Sad City, South Backa District, Vojvodina, 21138, Serbia"))
				.andExpect(jsonPath("$.location.latitude").value(45.2652f))
				.andExpect(jsonPath("$.location.longitude").value(19.8159f))
				.andExpect(jsonPath("$.location.type").value("station"))
				.andExpect(jsonPath("$.line.line_type").value("bus")).andExpect(jsonPath("$.name").value("Nis_expres"))
				.andExpect(status().isOk());

	}

	// bus with id-doesn't exist
	@Test
	public void getOneTest2() throws Exception {
		mockMvc.perform(get(URL_PREFIX + "/getBus/204536362")).andExpect(status().isBadRequest());

	}

	// good values to create bus
	@Test
	@Transactional
	@Rollback(value=true)
	public void wcreateTest() throws Exception {
		BusDTO bus = new BusDTO("Lasta", 1L, 5);
		String json = TestUtil.json(bus);
		this.mockMvc.perform(post(URL_PREFIX + "/addBus").contentType(contentType).content(json))
				.andExpect(jsonPath("$.id").value(5L)).andExpect(jsonPath("$.name").value("Lasta"))
				.andExpect(jsonPath("$.code").value("7ca_bus_Lasta")).andExpect(jsonPath("$.late").value(false))
				.andExpect(jsonPath("$.time_arrive").value(5)).andExpect(jsonPath("$.line.id").value(1))
				.andExpect(status().isOk());

	}

	// bad values to create bus(line doesn't exist)
	@Test
	@Transactional
	@Rollback(value=true)
	public void wcreateTest2() throws Exception {
		BusDTO bus = new BusDTO("Lasta", 464643L, 5);
		String json = TestUtil.json(bus);
		this.mockMvc.perform(post(URL_PREFIX + "/addBus").contentType(contentType).content(json))
				.andExpect(status().isBadRequest());

	}

	// change bus
	@Test
	@Transactional
	@Rollback(value=true)
	public void updateTest() throws Exception {

		Location l = new Location(4L, "nova lokacija", "adresa", 67.46f, 54.654f, "transport");
		Set<Date> dates = new HashSet<>();
		dates.add(new Date());
		Schedule s = new Schedule(dates);
		List<Schedule> schedules = new ArrayList<>();
		schedules.add(s);
		Timetable t = new Timetable("kod", schedules);

		ChangeTransportDTO change = new ChangeTransportDTO(1L, "Novo ime", 7, l, t);

		String json = TestUtil.json(change);
		this.mockMvc.perform(post(URL_PREFIX + "/updateBus").contentType(contentType).content(json))
				.andExpect(jsonPath("$.id").value(1L)).andExpect(jsonPath("$.name").value("Novo ime"))
				.andExpect(jsonPath("$.code").value("7ca_bus_Novo ime")).andExpect(jsonPath("$.late").value(true))
				.andExpect(jsonPath("$.time_arrive").value(7)).andExpect(jsonPath("$.line.id").value(1))
				.andExpect(status().isOk());

	}

	// update bus that doesn't exist
	@Test
	@Transactional
	@Rollback(value=true)
	public void updateTest2() throws Exception {

		Location l = new Location(4L, "nova lokacija", "adresa", 67.46f, 54.654f, "transport");
		Set<Date> dates = new HashSet<>();
		dates.add(new Date());
		Schedule s = new Schedule(dates);
		List<Schedule> schedules = new ArrayList<>();
		schedules.add(s);
		Timetable t = new Timetable("kod", schedules);

		ChangeTransportDTO change = new ChangeTransportDTO(145646453435465753L, "Novo ime", 7, l, t);

		String json = TestUtil.json(change);
		this.mockMvc.perform(post(URL_PREFIX + "/updateBus").contentType(contentType).content(json))
				.andExpect(status().isBadRequest());

	}

	// test bus delete if bus exist,return:true
	@Test
	@Transactional
	@Rollback(value=true)
	public void deleteTest() throws Exception {
		this.mockMvc.perform(get(URL_PREFIX + "/deleteBus/1")).andExpect(status().isOk())
				.andExpect(content().contentType(contentType)).andExpect(content().string("true"));

	}

	// test bus delete if bus doesn't exist,return:false
	@Test
	@Transactional
	@Rollback(value=true)
	public void deleteTest2() throws Exception {
		this.mockMvc.perform(get(URL_PREFIX + "/deleteBus/1046456343")).andExpect(status().isOk())
				.andExpect(content().contentType(contentType)).andExpect(content().string("false"));

	}

	// add location to bus-good value
	@Test
	@Transactional
	@Rollback(value=true)
	public void addLocationTest() throws Exception {

		AddLocationDTO addlocation = new AddLocationDTO(4L, 2L);
		String json = TestUtil.json(addlocation);
		this.mockMvc.perform(post(URL_PREFIX + "/addLocation").contentType(contentType).content(json))
				.andExpect(status().isOk());

	}

	// add location to bus-bad value(bus doesn't exist)
	@Test
	@Transactional
	@Rollback(value=true)
	public void addLocationTest2() throws Exception {

		AddLocationDTO addlocation = new AddLocationDTO(4454364363643L, 2L);
		String json = TestUtil.json(addlocation);
		this.mockMvc.perform(post(URL_PREFIX + "/addLocation").contentType(contentType).content(json))
				.andExpect(status().isBadRequest());

	}

	// add location to bus-bad value(location doesn't exist)
	@Test
	@Transactional
	@Rollback(value=true)
	public void addLocationTest3() throws Exception {

		AddLocationDTO addlocation = new AddLocationDTO(1L, 345545353L);
		String json = TestUtil.json(addlocation);
		this.mockMvc.perform(post(URL_PREFIX + "/addLocation").contentType(contentType).content(json))
				.andExpect(status().isBadRequest());

	}

	// search and filter
	@Test
	public void asearchFilterTest() throws Exception {

		FilterSearchTransportDTO searchFilter = new FilterSearchTransportDTO(1L, false, 1L, "");
		String json = TestUtil.json(searchFilter);
		this.mockMvc.perform(post(URL_PREFIX + "/searchFilter").contentType(contentType).content(json))
				.andExpect(status().isOk());

	}

}

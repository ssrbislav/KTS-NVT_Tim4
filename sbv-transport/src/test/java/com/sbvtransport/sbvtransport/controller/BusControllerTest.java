package com.sbvtransport.sbvtransport.controller;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.nio.charset.Charset;
import javax.annotation.PostConstruct;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import com.sbvtransport.sbvtransport.dto.BusDTO;
import com.sbvtransport.sbvtransport.enumeration.TypeTransport;
import com.sbvtransport.sbvtransport.model.Bus;
import com.sbvtransport.sbvtransport.model.Line;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
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
	public void getAll() throws Exception {
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
	//
	// @Test
	// @Transactional
	// @Rollback(true)
	// public void createTest() throws Exception {
	//// BusDTO bus = new BusDTO(false, "67ca", 1L);
	////
	//// String json = TestUtil.json(bus);
	//// this.mockMvc.perform(post(URL_PREFIX +
	// "/addBus").contentType(contentType).content(json))
	//// .andExpect(status().isOk());
	//
	// }
	//
	// @Test
	// @Transactional
	// @Rollback(true)
	// public void updateTest() throws Exception {
	//
	// Bus bus = new Bus();
	// bus.setId(1L);
	// bus.setName("8ca");
	// bus.setLate(true);
	// bus.setLocation(null);
	// bus.setTimetable(null);
	// bus.setCode("nova_linija_bus_8ca");
	// Line line = new Line("nova_linija", TypeTransport.bus);
	// line.setId(1L);
	// bus.setLine(line);
	//
	// // when changing update, change this, because we can turn object in
	// // object into the json
	//
	// // String json = TestUtil.json(bus);
	// // this.mockMvc.perform(post(URL_PREFIX + "/updateBus")
	// // .contentType(contentType)
	// // .content(json))
	// // .andExpect(status().isOk());
	// //
	//
	// }
	//
	// // test bus delete if bus exist,return:true
	// @Test
	// @Transactional
	// @Rollback(true)
	// public void deleteTest() throws Exception {
	// this.mockMvc.perform(get(URL_PREFIX +
	// "/deleteBus/1")).andExpect(status().isOk())
	// .andExpect(content().contentType(contentType)).andExpect(content().string("true"));
	//
	// }
	//
	// // test bus delete if bus doesn't exist,return:false
	// @Test
	// @Transactional
	// @Rollback(true)
	// public void deleteTest2() throws Exception {
	// this.mockMvc.perform(get(URL_PREFIX +
	// "/deleteBus/10")).andExpect(status().isOk())
	// .andExpect(content().contentType(contentType)).andExpect(content().string("false"));
	//
	// }

}

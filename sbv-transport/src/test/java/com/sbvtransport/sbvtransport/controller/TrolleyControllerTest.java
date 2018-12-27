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
import com.sbvtransport.sbvtransport.dto.TrolleyDTO;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
public class TrolleyControllerTest {

	private static final String URL_PREFIX = "/api/trolley";

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
				.andExpect(jsonPath("$", hasSize(3))).andExpect(jsonPath("$.[*].id").value(hasItem(2)))
				.andExpect(jsonPath("$.[*].code").value(hasItem("nova_linija3_trolley_12ka")))
				.andExpect(jsonPath("$.[*].late").value(hasItem(false)))
				.andExpect(jsonPath("$.[*].line.id").value(hasItem(3)))
				.andExpect(jsonPath("$.[*].line.name").value(hasItem("nova_linija3")))
				.andExpect(jsonPath("$.[*].line.line_type").value(hasItem("trolley")))
				.andExpect(jsonPath("$.[*].name").value(hasItem("12ka")));

	}

	@Test
	public void getOneTest() throws Exception {
		mockMvc.perform(get(URL_PREFIX + "/getTrolley/3")).andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$.id").value(3L)).andExpect(jsonPath("$.code").value("nova_linija3_trolley_17ca"))
				.andExpect(jsonPath("$.late").value(false)).andExpect(jsonPath("$.line.id").value(3))
				.andExpect(jsonPath("$.line.name").value("nova_linija3"))
				.andExpect(jsonPath("$.line.line_type").value("trolley")).andExpect(jsonPath("$.name").value("17ca"))
				.andExpect(status().isOk());

	}

	@Test
	@Transactional
	@Rollback(true)
	public void createTest() throws Exception {
		TrolleyDTO trolley = new TrolleyDTO(false, "55ca", 3L);

		String json = TestUtil.json(trolley);
		this.mockMvc.perform(post(URL_PREFIX + "/createTrolley").contentType(contentType).content(json))
				.andExpect(status().isOk());

	}

	@Test
	@Transactional
	@Rollback(true)
	public void updateTest() throws Exception {

		// when changing update, change this, because we can turn object in
		// object into the json
	}

	// test trolley delete if trolley exist,return:true
	@Test
	@Transactional
	@Rollback(true)
	public void deleteTest() throws Exception {
		this.mockMvc.perform(get(URL_PREFIX + "/deleteTrolley/1")).andExpect(status().isOk())
				.andExpect(content().contentType(contentType)).andExpect(content().string("true"));

	}

	// test trolley delete if trolley doesn't exist,return:false
	@Test
	@Transactional
	@Rollback(true)
	public void deleteTest2() throws Exception {
		this.mockMvc.perform(get(URL_PREFIX + "/deleteTrolley/10")).andExpect(status().isOk())
				.andExpect(content().contentType(contentType)).andExpect(content().string("false"));

	}

}

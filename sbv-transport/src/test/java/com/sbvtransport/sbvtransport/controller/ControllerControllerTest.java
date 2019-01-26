package com.sbvtransport.sbvtransport.controller;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.nio.charset.Charset;
import java.util.Date;
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
import com.sbvtransport.sbvtransport.dto.RegisterDTO;
import com.sbvtransport.sbvtransport.model.Controller;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
@Rollback(value = true)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ControllerControllerTest {

	private static final String URL_PREFIX = "/api/controller";

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
	@Rollback(value = true)
	public void agetAllTest() throws Exception {
		mockMvc.perform(get(URL_PREFIX)).andExpect(status().isOk()).andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$", hasSize(2))).andExpect(jsonPath("$.[*].id").value(hasItem(1)))
				.andExpect(jsonPath("$.[*].email").value(hasItem("bokaa@gmail.com")))
				.andExpect(jsonPath("$.[*].username").value(hasItem("bokaKontroler")))
				.andExpect(jsonPath("$.[*].date_birth").value(hasItem("1995-03-30")))
				.andExpect(jsonPath("$.[*].phone_number").value(hasItem("0897346576")))
				.andExpect(jsonPath("$.[*].address").value(hasItem("Balzakova 28")))
				.andExpect(jsonPath("$.[*].last_name").value(hasItem("Corilic")))
				.andExpect(jsonPath("$.[*].first_name").value(hasItem("Bojana")))
				.andExpect(jsonPath("$.[*].password").value(hasItem("lozinka")));

	}

	@Test
	public void getControllerTest() throws Exception {
		mockMvc.perform(get(URL_PREFIX + "/getController/bokaKontroler")).andExpect(status().isOk())
				.andExpect(content().contentType(contentType)).andExpect(jsonPath("$.id").value(1L))
				.andExpect(jsonPath("$.email").value("bokaa@gmail.com"))
				.andExpect(jsonPath("$.username").value("bokaKontroler"))
				.andExpect(jsonPath("$.date_birth").value("1995-03-30"))
				.andExpect(jsonPath("$.phone_number").value("0897346576"))
				.andExpect(jsonPath("$.address").value("Balzakova 28"))
				.andExpect(jsonPath("$.last_name").value("Corilic")).andExpect(jsonPath("$.first_name").value("Bojana"))
				.andExpect(jsonPath("$.password").value("lozinka"));

	}

	// get controller that doesn't exist
	@Test
	public void getControllerTest2() throws Exception {
		mockMvc.perform(get(URL_PREFIX + "/getController/dfswfw")).andExpect(status().isBadRequest());

	}

	// update controller
	@Test
	@Transactional
	@Rollback(value = true)
	public void updateControllerTest() throws Exception {
		Controller updateController = new Controller("izmenjen@gmail.com", "izmenjen", "izmenjenasifra",
				"izmenjeno ime", "izmenjeno prezime", "adresa", "3543265443", new Date());
		updateController.setId(1L);
		String json = TestUtil.json(updateController);
		this.mockMvc.perform(post(URL_PREFIX + "/updateController").contentType(contentType).content(json))
				.andExpect(status().isOk());

	}

	// update controller , wrong id
	@Test
	@Transactional
	@Rollback(value = true)
	public void updateControllerTest2() throws Exception {
		Controller updateController = new Controller("izmenjen@gmail.com", "izmenjen", "izmenjenasifra",
				"izmenjeno ime", "izmenjeno prezime", "adresa", "3543265443", new Date());
		updateController.setId(35325325325L);
		String json = TestUtil.json(updateController);
		this.mockMvc.perform(post(URL_PREFIX + "/updateController").contentType(contentType).content(json))
				.andExpect(status().isBadRequest());

	}

	// test controller delete if controller exist,return:true
	@Test
	@Transactional
	@Rollback(value = true)
	public void deleteTest() throws Exception {
		this.mockMvc.perform(get(URL_PREFIX + "/deleteController/1")).andExpect(status().isOk())
				.andExpect(content().contentType(contentType)).andExpect(content().string("true"));

	}

	// test controller delete if controller doesn't exist,return:false
	@Test
	@Transactional
	@Rollback(value = true)
	public void deleteTest2() throws Exception {
		this.mockMvc.perform(get(URL_PREFIX + "/deleteController/1046456343")).andExpect(status().isOk())
				.andExpect(content().contentType(contentType)).andExpect(content().string("false"));

	}

	// create controller
	@Test
	@Transactional
	@Rollback(value = true)
	public void wregisterTest() throws Exception {
		RegisterDTO controller = new RegisterDTO();
		controller.setUsername("controller");
		controller.setEmail("email@gmail.com");
		controller.setPassword("password");
		controller.setFirst_name("ImeKontrolora");
		controller.setLast_name("Prezime");
		controller.setAddress("Neka adresa");
		controller.setPhone_number("0604566258");
		controller.setDate_birth(new Date());
		String json = TestUtil.json(controller);
		this.mockMvc.perform(post(URL_PREFIX + "/addController").contentType(contentType).content(json))
				.andExpect(status().isOk());

	}

	// create controller with username that exists
	@Test
	@Transactional
	@Rollback(value = true)
	public void wregisterTest2() throws Exception {
		RegisterDTO controller = new RegisterDTO();
		controller.setUsername("bokaKontroler");
		controller.setEmail("email@gmail.com");
		controller.setPassword("password");
		controller.setFirst_name("ImeKontrolora");
		controller.setLast_name("Prezime");
		controller.setAddress("Neka adresa");
		controller.setPhone_number("0604566258");
		controller.setDate_birth(new Date());
		String json = TestUtil.json(controller);
		this.mockMvc.perform(post(URL_PREFIX + "/addController").contentType(contentType).content(json))
				.andExpect(status().isBadRequest());

	}

	// create controller with email that exists
	@Test
	@Transactional
	@Rollback(value = true)
	public void wregisterTest3() throws Exception {
		RegisterDTO controller = new RegisterDTO();
		controller.setUsername("novi");
		controller.setEmail("bokaa@gmail.com");
		controller.setPassword("password");
		controller.setFirst_name("ImeKontrolora");
		controller.setLast_name("Prezime");
		controller.setAddress("Neka adresa");
		controller.setPhone_number("0604566258");
		controller.setDate_birth(new Date());
		String json = TestUtil.json(controller);
		this.mockMvc.perform(post(URL_PREFIX + "/addController").contentType(contentType).content(json))
				.andExpect(status().isBadRequest());

	}

}

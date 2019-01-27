package com.sbvtransport.sbvtransport.controller;

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
import com.sbvtransport.sbvtransport.dto.DocumentDTO;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
@Rollback(value = true)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DocumentControllerTest {

	private static final String URL_PREFIX = "/api/document";

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
		this.mockMvc.perform(get(URL_PREFIX)).andExpect(status().isOk()).andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$", hasSize(2)));

	}

	// get one document by id
	@Test
	public void agetDocumentTest() throws Exception {
		this.mockMvc.perform(get(URL_PREFIX + "/getDocument/1")).andExpect(status().isOk())
				.andExpect(content().contentType(contentType)).andExpect(jsonPath("$.id").value(1L))
				.andExpect(jsonPath("$.date_of_upload").value("2019-01-10"))
				.andExpect(jsonPath("$.image_location").value("lokacija"));
	}

	// get document that doesn't exist
	@Test
	public void getDocumentTest2() throws Exception {
		this.mockMvc.perform(get(URL_PREFIX + "/getDocument/45325")).andExpect(status().isNotFound());

	}

	// create document that with id passenger that doesn't exist
	@Test
	@Transactional
	@Rollback(value = true)
	public void wcreateTest() throws Exception {
		DocumentDTO newDocument = new DocumentDTO(new Date(), "lokacijaa", 6L);
		String json = TestUtil.json(newDocument);
		this.mockMvc.perform(post(URL_PREFIX + "/addDoc").contentType(contentType).content(json))
				.andExpect(status().isOk());

	}

	// create document that with id passenger that doesn't exist
	@Test
	@Transactional
	@Rollback(value = true)
	public void wcreateTest2() throws Exception {
		DocumentDTO newDocument = new DocumentDTO(new Date(), "lokacijaa", 6343535L);
		String json = TestUtil.json(newDocument);
		this.mockMvc.perform(post(URL_PREFIX + "/addDoc").contentType(contentType).content(json))
				.andExpect(status().isBadRequest());

	}

	// update document
	@Test
	@Transactional
	@Rollback(value = true)
	public void updateTest() throws Exception {
		DocumentDTO newDocument = new DocumentDTO(1L, new Date(), "nova lokacija", 3L);
		String json = TestUtil.json(newDocument);
		this.mockMvc.perform(post(URL_PREFIX + "/updateDoc").contentType(contentType).content(json))
				.andExpect(status().isOk());

	}

	// update document that doesn;t exist
	@Test
	@Transactional
	@Rollback(value = true)
	public void updateTest2() throws Exception {
		DocumentDTO newDocument = new DocumentDTO(134535353535L, new Date(), "nova lokacija", 3L);
		String json = TestUtil.json(newDocument);
		this.mockMvc.perform(post(URL_PREFIX + "/updateDoc").contentType(contentType).content(json))
				.andExpect(status().isBadRequest());

	}

	// update document with wrong id passenger
	@Test
	@Transactional
	@Rollback(value = true)
	public void updateTest3() throws Exception {
		DocumentDTO newDocument = new DocumentDTO(1L, new Date(), "nova lokacija", 4L);
		String json = TestUtil.json(newDocument);
		this.mockMvc.perform(post(URL_PREFIX + "/updateDoc").contentType(contentType).content(json))
				.andExpect(status().isBadRequest());

	}

	// delete document
	@Test
	@Transactional
	@Rollback(value = true)
	public void wwdeleteTest() throws Exception {
		this.mockMvc.perform(get(URL_PREFIX + "/deleteDoc/1")).andExpect(status().isOk()).andExpect(content().contentType(contentType));

	}

}

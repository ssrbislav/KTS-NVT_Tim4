package com.sbvtransport.sbvtransport.controller;
import com.mysql.cj.util.TestUtils;
import com.sbvtransport.sbvtransport.TestUtil;
import com.sbvtransport.sbvtransport.dto.AddFirstStationDTO;
import com.sbvtransport.sbvtransport.dto.LineDTO;
import com.sbvtransport.sbvtransport.dto.PassengerChangeBooleanDTO;
import com.sbvtransport.sbvtransport.enumeration.TypeTransport;
import com.sbvtransport.sbvtransport.enumeration.Zone;
import com.sbvtransport.sbvtransport.model.Passenger;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;
import java.nio.charset.Charset;
import org.springframework.web.util.NestedServletException;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
@Rollback(value=true)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PassengerControllerTest {

  private static final String URL_PREFIX = "/api/passenger";
  private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
  private MockMvc mockMvc;

  @Autowired
  private WebApplicationContext webApplicationContext;

  @PostConstruct
  public void setup() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }

  @Test
  public void aaagetAllTest() throws Exception {
    mockMvc.perform(get(URL_PREFIX)).andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(4)))
        .andExpect(jsonPath("$.[*].id").value(hasItem(4)))
        .andExpect(jsonPath("$.[*].active").value(hasItem(true)))
        .andExpect(jsonPath("$.[*].email").value(hasItem("putnik2@gmail.com")));
  }

  @Test
  public void getOne_OK() throws Exception {
    mockMvc.perform(get(URL_PREFIX + "/getPassengerByID/4")).andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(4))
        .andExpect(jsonPath("$.active").value(true))
        .andExpect(jsonPath("$.email").value("putnik2@gmail.com"));
  }

  @Test
  public void getOne_NotFound() throws Exception {
    mockMvc.perform(get(URL_PREFIX + "/getPassengerByID/433")).andExpect(status().isNotFound());
  }

  @Test
  public void getOneByUsername_OK() throws Exception {
    mockMvc.perform(get(URL_PREFIX + "/getPassenger/putnik2")).andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(4))
        .andExpect(jsonPath("$.active").value(true))
        .andExpect(jsonPath("$.email").value("putnik2@gmail.com"));
  }

  @Test
  public void getOneByUsername_NotFound() throws Exception {
    mockMvc.perform(get(URL_PREFIX + "/getPassenger/afjakdfsdjh")).andExpect(status().isNotFound());
  }

  @Test
  @Transactional
  @Rollback(true)
  public void deleteTest_OK() throws Exception {
    this.mockMvc.perform(post(URL_PREFIX + "/deletePassenger/4")).andExpect(status().isOk())
        .andExpect(content().string("true"));
  }

  @Test
  @Transactional
  @Rollback(true)
  public void deleteTest_NoPassenger() throws Exception {
    this.mockMvc.perform(post(URL_PREFIX + "/deletePassenger/1000")).andExpect(status().isNotFound())
        .andExpect(content().string("false"));
  }

  @Test
  @Transactional
  @Rollback(true)
  public void changeActive_OK() throws Exception {
    PassengerChangeBooleanDTO dto = new PassengerChangeBooleanDTO();
    dto.setChange(false);
    dto.setIdPassenger(4L);
    String json = TestUtil.json(dto);
    this.mockMvc.perform(post(URL_PREFIX + "/changeActive").contentType(contentType).content(json)).andExpect(status().isOk())
        .andExpect(content().string("Successfully!"));
  }

  @Test
  @Transactional
  @Rollback(true)
  public void changeActive_NotFound() throws Exception {
    PassengerChangeBooleanDTO dto = new PassengerChangeBooleanDTO();
    dto.setChange(false);
    dto.setIdPassenger(455L);
    String json = TestUtil.json(dto);
    this.mockMvc.perform(post(URL_PREFIX + "/changeActive").contentType(contentType).content(json)).andExpect(status().isBadRequest());
  }

}

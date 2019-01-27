package com.sbvtransport.sbvtransport.controller;

import com.sbvtransport.sbvtransport.TestUtil;
import com.sbvtransport.sbvtransport.dto.AddFirstStationDTO;
import com.sbvtransport.sbvtransport.dto.LineDTO;
import com.sbvtransport.sbvtransport.dto.TicketDTO;
import com.sbvtransport.sbvtransport.enumeration.TypeTransport;
import com.sbvtransport.sbvtransport.enumeration.Zone;
import java.util.ArrayList;
import java.util.Date;
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
public class TicketControllerTest {

  private static final String URL_PREFIX = "/api/ticket";
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
        .andExpect(jsonPath("$", hasSize(3)))
        .andExpect(jsonPath("$.[*].id").value(hasItem(2)))
        .andExpect(jsonPath("$.[*].code_transport").value(hasItem("kec_bus_devetka")))
        .andExpect(jsonPath("$.[*].cost").value(hasItem(3118.5)));
  }

  @Test
  public void getOneTest_OK() throws Exception {
    mockMvc.perform(get(URL_PREFIX + "/getTicket/2")).andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(2))
        .andExpect(jsonPath("$.code_transport").value("kec_bus_devetka"))
        .andExpect(jsonPath("$.cost").value(3118.5));
  }

  @Test
  public void getOneTest_NotFound() throws Exception {
    mockMvc.perform(get(URL_PREFIX + "/getTicket/265")).andExpect(status().isNotFound());
  }

  @Test
  @Transactional
  @Rollback(true)
  public void delete_OK() throws Exception {
    mockMvc.perform(post(URL_PREFIX + "/deleteTicket/2")).andExpect(status().isOk())
        .andExpect(content().string("true"));
  }

  @Test
  @Transactional
  @Rollback(true)
  public void delete_NotFound() throws Exception {
    mockMvc.perform(post(URL_PREFIX + "/deleteTicket/265")).andExpect(status().isNotFound())
        .andExpect(content().string("false"));
  }

  @Test
  public void aagetAllByUser_OK() throws Exception {
    mockMvc.perform(get(URL_PREFIX + "/getTickets/4")).andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(3)))
        .andExpect(jsonPath("$.[*].id").value(hasItem(2)))
        .andExpect(jsonPath("$.[*].code_transport").value(hasItem("kec_bus_devetka")))
        .andExpect(jsonPath("$.[*].cost").value(hasItem(3118.5)));
  }

  @Test
  public void getAllByUser_UserNotFound() throws Exception {
    mockMvc.perform(get(URL_PREFIX + "/getTickets/55")).andExpect(status().isNotFound())
        .andExpect(content().string(""));
  }

  @Test
  public void getAllByUser_NoTickets() throws Exception {
    mockMvc.perform(get(URL_PREFIX + "/getTickets/6")).andExpect(status().isNotFound())
        .andExpect(content().string(""));
  }

  @Test
  @Transactional
  @Rollback(true)
  public void addTicketTest_OK() throws Exception {
    TicketDTO dto = new TicketDTO();
    dto.setCode_transport("bla");
    dto.setDate(new Date());
    dto.setDemographic_type("standard");
    dto.setIdPassenger(4L);
    dto.setString("first");
    dto.setTicket_type("oneUse");
    dto.setType_transport("bus");
    String json = TestUtil.json(dto);
    this.mockMvc.perform(post(URL_PREFIX + "/addTicket").contentType(contentType).content(json))
        .andExpect(status().isOk());
//            .andExpect(content().string("Station successfully added!"));
  }

  @Test
  @Transactional
  @Rollback(true)
  public void addTicketTest_DemoTypeMismatch() throws Exception {
    TicketDTO dto = new TicketDTO();
    dto.setCode_transport("bla");
    dto.setDate(new Date());
    dto.setDemographic_type("senior");
    dto.setIdPassenger(4L);
    dto.setString("first");
    dto.setTicket_type("oneUse");
    dto.setType_transport("bus");
    String json = TestUtil.json(dto);
    this.mockMvc.perform(post(URL_PREFIX + "/addTicket").contentType(contentType).content(json))
        .andExpect(status().isBadRequest());
//            .andExpect(content().string("Station successfully added!"));
  }

  @Test
  @Transactional
  @Rollback(true)
  public void addTicketTest_NoUser() throws Exception {
    TicketDTO dto = new TicketDTO();
    dto.setCode_transport("bla");
    dto.setDate(new Date());
    dto.setDemographic_type("senior");
    dto.setIdPassenger(45L);
    dto.setString("first");
    dto.setTicket_type("oneUse");
    dto.setType_transport("bus");
    String json = TestUtil.json(dto);
    this.mockMvc.perform(post(URL_PREFIX + "/addTicket").contentType(contentType).content(json))
        .andExpect(status().isBadRequest());
//            .andExpect(content().string("Station successfully added!"));
  }

  @Test
  @Transactional
  @Rollback(true)
  public void addTicketTest_EmptyFields() throws Exception {
    TicketDTO dto = new TicketDTO();
    dto.setCode_transport("");
    dto.setDate(new Date());
    dto.setDemographic_type("senior");
    dto.setIdPassenger(45L);
    dto.setTicket_type("oneUse");
    dto.setType_transport("bus");
    String json = TestUtil.json(dto);
    this.mockMvc.perform(post(URL_PREFIX + "/addTicket").contentType(contentType).content(json))
        .andExpect(status().isBadRequest());
//            .andExpect(content().string("Station successfully added!"));
  }

  @Test
  @Transactional
  @Rollback(true)
  public void activateTicketTest_OK() throws Exception {
    mockMvc.perform(post(URL_PREFIX + "/activate/1")).andExpect(status().isOk())
        .andExpect(jsonPath("$.active").value(true));
  }

  @Test
  @Transactional
  @Rollback(true)
  public void activateTicketTest_NoTicket() throws Exception {
    mockMvc.perform(post(URL_PREFIX + "/activate/265")).andExpect(status().isBadRequest());
  }


}

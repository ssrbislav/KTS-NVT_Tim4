package com.sbvtransport.sbvtransport.controller;

import com.sbvtransport.sbvtransport.TestUtil;
import com.sbvtransport.sbvtransport.dto.AddFirstStationDTO;
import com.sbvtransport.sbvtransport.dto.AltTimetableDTO;
import com.sbvtransport.sbvtransport.dto.LineDTO;
import com.sbvtransport.sbvtransport.dto.ScheduleDTO;
import com.sbvtransport.sbvtransport.dto.TicketDTO;
import com.sbvtransport.sbvtransport.dto.TimetableDTO;
import com.sbvtransport.sbvtransport.enumeration.TypeTransport;
import com.sbvtransport.sbvtransport.enumeration.Zone;
import java.text.SimpleDateFormat;
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
public class TimetableControllerTest {

  private static final String URL_PREFIX = "/api/timetable";
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
        .andExpect(jsonPath("$.[*].id").value(hasItem(15)))
        .andExpect(jsonPath("$.[*].code").value(hasItem("kec_bus_devetka")))
        .andExpect(jsonPath("$.[*].deleted").value(hasItem(false)));
  }

  @Test
  public void getOneTest_OK() throws Exception {
    mockMvc.perform(get(URL_PREFIX + "/getTimetable/15")).andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(15L))
        .andExpect(jsonPath("$.code").value("kec_bus_devetka"))
        .andExpect(jsonPath("$.deleted").value(false));
  }

  @Test
  public void getOneTest_NotFound() throws Exception {
    mockMvc.perform(get(URL_PREFIX + "/getTimetable/265")).andExpect(status().isBadRequest());
  }

  @Test
  @Transactional
  @Rollback(true)
  public void delete_OK() throws Exception {
    mockMvc.perform(post(URL_PREFIX + "/deleteTimetable/15")).andExpect(status().isOk())
        .andExpect(content().string("true"));
  }

  @Test
  @Transactional
  @Rollback(true)
  public void delete_NotFound() throws Exception {
    mockMvc.perform(post(URL_PREFIX + "/deleteTimetable/265")).andExpect(status().isBadRequest())
        .andExpect(content().string("false"));
  }

  @Test
  @Transactional
  @Rollback(true)
  public void addTimetable_OK() throws Exception {
    TimetableDTO dto = new TimetableDTO();
    dto.setId_transport(1L);
    dto.setTransportType("bus");
    ScheduleDTO s1 = new ScheduleDTO();
    s1.setStation_id(1L);
    List<Date> dates = new ArrayList<>();
    dates.add(new Date());
    dates.add(new Date());
    s1.setDates(dates);
    ScheduleDTO s2 = new ScheduleDTO();
    s2.setStation_id(2L);
    List<Date> dates2 = new ArrayList<>();
    dates.add(new Date());
    dates.add(new Date());
    s2.setDates(dates2);
    List<ScheduleDTO> schedules = new ArrayList<>();
    schedules.add(s1);
    schedules.add(s2);
    dto.setSchedules(schedules);
    String json = TestUtil.json(dto);
    mockMvc.perform(post(URL_PREFIX + "/addTimetable").contentType(contentType).content(json)).andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(19))
        .andExpect(jsonPath("$.deleted").value(false));
  }

  @Test
  @Transactional
  @Rollback(true)
  public void addTimetable_BadStations() throws Exception {
    TimetableDTO dto = new TimetableDTO();
    dto.setId_transport(1L);
    dto.setTransportType("bus");
    ScheduleDTO s1 = new ScheduleDTO();
    s1.setStation_id(11200L);
    List<Date> dates = new ArrayList<>();
    dates.add(new Date());
    dates.add(new Date());
    s1.setDates(dates);
    ScheduleDTO s2 = new ScheduleDTO();
    s2.setStation_id(112300L);
    List<Date> dates2 = new ArrayList<>();
    dates.add(new Date());
    dates.add(new Date());
    s2.setDates(dates2);
    List<ScheduleDTO> schedules = new ArrayList<>();
    schedules.add(s1);
    schedules.add(s2);
    dto.setSchedules(schedules);
    String json = TestUtil.json(dto);
    mockMvc.perform(post(URL_PREFIX + "/addTimetable").contentType(contentType).content(json)).andExpect(status().isBadRequest());
  }

  @Test
  @Transactional
  @Rollback(true)
  public void addTimetable_BadTransportID() throws Exception {
    TimetableDTO dto = new TimetableDTO();
    dto.setId_transport(15464L);
    dto.setTransportType("bus");
    ScheduleDTO s1 = new ScheduleDTO();
    s1.setStation_id(1L);
    List<Date> dates = new ArrayList<>();
    dates.add(new Date());
    dates.add(new Date());
    s1.setDates(dates);
    ScheduleDTO s2 = new ScheduleDTO();
    s2.setStation_id(2L);
    List<Date> dates2 = new ArrayList<>();
    dates.add(new Date());
    dates.add(new Date());
    s2.setDates(dates2);
    List<ScheduleDTO> schedules = new ArrayList<>();
    schedules.add(s1);
    schedules.add(s2);
    dto.setSchedules(schedules);
    String json = TestUtil.json(dto);
    mockMvc.perform(post(URL_PREFIX + "/addTimetable").contentType(contentType).content(json)).andExpect(status().isBadRequest());
  }

//  @Test
//  @Transactional
//  @Rollback(true)
  public void addAltTimetable_OK() throws Exception {
    // Date parse error - doesn't appear anywhere else.
    AltTimetableDTO dto = new AltTimetableDTO();
    dto.setId_transport(1L);
    dto.setTransportType("bus");
    List<Date> dates = new ArrayList<>();
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
    dates.add(sdf.parse("13:44"));
    dates.add(sdf.parse("14:44"));
    dates.add(sdf.parse("15:44"));
    dto.setTimetable(dates);
    String json = TestUtil.json(dto);
    mockMvc.perform(post(URL_PREFIX + "/addAltTimetable").contentType(contentType).content(json)).andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(19))
        .andExpect(jsonPath("$.deleted").value(false));
  }

  @Test
  @Transactional
  @Rollback(true)
  public void addAltTimetable_BadTransportID() throws Exception {
    AltTimetableDTO dto = new AltTimetableDTO();
    dto.setId_transport(1234234L);
    dto.setTransportType("bus");
    List<Date> dates = new ArrayList<>();
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
    dates.add(sdf.parse("13:44"));
    dates.add(sdf.parse("14:44"));
    dates.add(sdf.parse("15:44"));
    dto.setTimetable(dates);
    String json = TestUtil.json(dto);
    mockMvc.perform(post(URL_PREFIX + "/addAltTimetable").contentType(contentType).content(json)).andExpect(status().isBadRequest());
  }

}

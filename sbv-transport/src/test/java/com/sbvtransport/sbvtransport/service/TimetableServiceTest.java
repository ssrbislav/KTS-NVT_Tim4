package com.sbvtransport.sbvtransport.service;

import com.sbvtransport.sbvtransport.TestUtil;
import com.sbvtransport.sbvtransport.dto.AltTimetableDTO;
import com.sbvtransport.sbvtransport.dto.PassengerDTO;
import com.sbvtransport.sbvtransport.dto.ScheduleDTO;
import com.sbvtransport.sbvtransport.dto.TimetableDTO;
import com.sbvtransport.sbvtransport.model.Bus;
import com.sbvtransport.sbvtransport.model.Passenger;
import com.sbvtransport.sbvtransport.model.Timetable;
import com.sbvtransport.sbvtransport.model.Role;
import com.sbvtransport.sbvtransport.repository.PassengerRepository;
import com.sbvtransport.sbvtransport.repository.TimetableRepository;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.Null;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.springframework.test.util.AssertionErrors.assertTrue;
import static org.springframework.test.util.AssertionErrors.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
@Rollback(value=true)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TimetableServiceTest {

  @Autowired
  private ITimetableService timetableService;

  @Autowired
  IPassengerService passengerService;

  @Autowired
  IBusService busService;

  @Test
  public void aafindAllTest() {
    List<Timetable> timetables = timetableService.findAll();
    assertThat(timetables).hasSize(4);
  }

  @Test
  public void getOneTest() {
    Timetable timetable = timetableService.getOne(17L);

    assertThat(timetable).isNotNull();
    assertThat(timetable.getId()).isEqualTo(17L);
  }

  @Test
  public void getNOneTest() {
    Timetable timetable = timetableService.getOne(10L);
    assertThat(timetable).isNull();
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
    Timetable timetable = timetableService.create(dto);
    Bus bus = busService.getOne(1L);
    assertEquals(timetable, bus.getTimetable());
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
    Timetable timetable = timetableService.create(dto);
    assertThat(timetable).isNull();
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
    Timetable timetable = timetableService.create(dto);
    assertThat(timetable).isNull();
  }

  @Test
  @Transactional
  @Rollback(true)
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
    Timetable timetable = timetableService.create(dto);
    Bus bus = busService.getOne(1L);
    assertEquals(timetable, bus.getTimetable());
  }

  @Test(expected = NullPointerException.class)
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
    Timetable timetable = timetableService.create(dto);
    assertThat(timetable).isNull();
  }

  @Test
  @Transactional
  @Rollback(true)
  public void deleteTest() {
    int dbSizeBefore = timetableService.findAll().size();
    boolean success = timetableService.delete(16L);
    assertThat(success).isTrue();
    assertThat(dbSizeBefore).isEqualTo(timetableService.findAll().size() + 1);
    try {
      Timetable loc = timetableService.getOne(16L);
      assertThat(loc).isNull();
    } catch (Exception ex) {
      assertThat(ex.getClass()).isEqualTo(EntityNotFoundException.class);
    }
  }

  @Test
  @Transactional
  @Rollback(true)
  public void deleteTest2() {
    int dbSizeBefore = timetableService.findAll().size();
    boolean success = timetableService.delete(33L);
    assertThat(success).isFalse();
    assertThat(dbSizeBefore).isEqualTo(timetableService.findAll().size());
  }


}

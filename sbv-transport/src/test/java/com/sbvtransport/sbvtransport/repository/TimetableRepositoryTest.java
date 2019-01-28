package com.sbvtransport.sbvtransport.repository;

import static org.junit.Assert.assertEquals;

import com.sbvtransport.sbvtransport.enumeration.TypeTransport;
import com.sbvtransport.sbvtransport.enumeration.Zone;
import com.sbvtransport.sbvtransport.model.Line;
import com.sbvtransport.sbvtransport.model.Location;
import com.sbvtransport.sbvtransport.model.Schedule;
import com.sbvtransport.sbvtransport.model.Station;
import com.sbvtransport.sbvtransport.model.Timetable;
import java.util.ArrayList;
import java.util.Date;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.sbvtransport.sbvtransport.model.Passenger;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
public class TimetableRepositoryTest {

  @Autowired
  private TimetableRepository timetableRepository;

  @Autowired
  private ScheduleRepository scheduleRepository;

  @Autowired
  private LineRepository lineRepository;

  @Test
  @Transactional
  @Rollback(true)
  public void saveTimetableTest() {
    Line line = new Line();
    line.setFirst_station(1L);
    line.setLine_type(TypeTransport.bus);
    line.setDeleted(false);
    line.setName("linija");
    line.setTimetable(new ArrayList<>());
    line.setZone(Zone.second);
    Location l1 = new Location("Stanica1", "Vojvode Supljikca 50", 30.40f, 32.02f, "Station");
    Station s1 = new Station();
    s1.setDeleted(false);
    s1.setLine(line);
    s1.setLocation(l1);
    s1.setZone(Zone.first);
    Location l2 = new Location("Stanica2", "Vojvode Supljikca 1", 30.42f, 32.02f, "Station");
    Station s2 = new Station();
    s2.setDeleted(false);
    s2.setLine(line);
    s2.setLocation(l2);
    s2.setZone(Zone.second);
    Line savedLine = lineRepository.save(line);

    Timetable timetable = new Timetable();
    timetable.setDeleted(false);
    timetable.setCode(line.getName());
    List<Schedule> schedules = new ArrayList<>();
    Set<Date> dates = new HashSet<>();
    dates.add(new Date());
    dates.add(new Date());
    Set<Date> dates2 = new HashSet<>();
    dates.add(new Date());
    dates.add(new Date());
    Schedule sch1 = new Schedule();
    sch1.setDeleted(false);
    sch1.setStation(s1);
    sch1.setTimes(dates);
    Schedule sch2 = new Schedule();
    sch2.setDeleted(false);
    sch2.setStation(s2);
    sch2.setTimes(dates);

    schedules.add(sch1);
    schedules.add(sch2);
    timetable.setSchedule(schedules);

    Timetable newTimetable = timetableRepository.save(timetable);

    assertEquals(timetable.getSchedule(), newTimetable.getSchedule());
    assertEquals(timetable.getCode(), newTimetable.getCode());
    assertEquals(timetable.isDeleted(), newTimetable.isDeleted());

  }

  @Test(expected = DataIntegrityViolationException.class)
  public void saveTimetableTest2() { timetableRepository.save(new Timetable()); }

}

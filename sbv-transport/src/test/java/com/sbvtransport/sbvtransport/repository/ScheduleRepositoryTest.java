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
public class ScheduleRepositoryTest {

  @Autowired
  private ScheduleRepository scheduleRepository;

  @Test
  @Transactional
  @Rollback(true)
  public void saveScheduleTest() {
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

    Schedule schedule = new Schedule();
    Set<Date> dates = new HashSet<>();
    dates.add(new Date());
    dates.add(new Date());
    schedule.setDeleted(false);
    schedule.setStation(s1);
    schedule.setTimes(dates);

    Schedule newSchedule = scheduleRepository.save(schedule);

    assertEquals(schedule.getTimes(), newSchedule.getTimes());
    assertEquals(schedule.getStation(), newSchedule.getStation());
    assertEquals(schedule.isDeleted(), newSchedule.isDeleted());
  }

  //@Test(expected = DataIntegrityViolationException.class)
  public void saveScheduleTest2() { scheduleRepository.save(new Schedule()); }

}

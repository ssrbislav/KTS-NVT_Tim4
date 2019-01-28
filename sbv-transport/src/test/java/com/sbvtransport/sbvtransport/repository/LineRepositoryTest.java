package com.sbvtransport.sbvtransport.repository;

import com.sbvtransport.sbvtransport.enumeration.TypeTransport;
import com.sbvtransport.sbvtransport.enumeration.Zone;
import com.sbvtransport.sbvtransport.model.Line;
import com.sbvtransport.sbvtransport.model.Location;
import com.sbvtransport.sbvtransport.model.Station;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
public class LineRepositoryTest {

    @Autowired
    private LineRepository lineRepository;

    @Test
    @Transactional
    @Rollback(true)
    public void saveLineTest() {
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
        List<Station> stations = new ArrayList<>();
        stations.add(s1);
        stations.add(s2);
        line.setStation_list(stations);

        Line savedLine = lineRepository.save(line);
        assertNotNull(savedLine);
        assertEquals(line.getLine_type(), savedLine.getLine_type());
        assertEquals(line.getName(), savedLine.getName());
        assertEquals(line.getStation_list(), savedLine.getStation_list());
        assertEquals(line.getFirst_station(), savedLine.getFirst_station());
        assertEquals(line.getZone(), savedLine.getZone());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void saveLineTest2() {
        lineRepository.save(new Line());
    }

}

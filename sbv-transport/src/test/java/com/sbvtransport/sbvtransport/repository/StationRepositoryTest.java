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
public class StationRepositoryTest {

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private LineRepository lineRepository;

    @Test
    @Transactional
    @Rollback(true)
    public void saveStationTest() {
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
        List<Station> stations = new ArrayList<>();
        stations.add(s1);
        line.setStation_list(stations);

        locationRepository.save(l1);
        lineRepository.save(line);
        Station savedStation = stationRepository.save(s1);
        assertNotNull(savedStation);
        assertEquals(s1.getLine(), savedStation.getLine());
        assertEquals(s1.getLocation(), savedStation.getLocation());
        assertEquals(s1.getZone(), savedStation.getZone());
        assertEquals(s1.isDeleted(), savedStation.isDeleted());
        assertEquals(s1.getLine(), savedStation.getLine());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void saveStationTest2() {
        stationRepository.save(new Station());
    }

}

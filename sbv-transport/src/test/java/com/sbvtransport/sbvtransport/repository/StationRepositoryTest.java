package com.sbvtransport.sbvtransport.repository;

import com.sbvtransport.sbvtransport.enumeration.TypeTransport;
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
    public void saveLineTest() {
        Line line = new Line("9", TypeTransport.bus);
        Location l1 = new Location("Stanica1", "Vojvode Supljikca 50", 30.40f, 32.02f, "Station");
        Station s1 = new Station(l1, line);
        List<Station> stations = new ArrayList<>();
        stations.add(s1);
        line.setStation_list(stations);

        locationRepository.save(l1);
        lineRepository.save(line);
        Station savedStation = stationRepository.save(s1);
        assertNotNull(savedStation);
        assertEquals(s1.getLine(), savedStation.getLine());
        assertEquals(s1.getLocation(), savedStation.getLocation());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void saveLineTest2() {
        stationRepository.save(new Station());
    }

}

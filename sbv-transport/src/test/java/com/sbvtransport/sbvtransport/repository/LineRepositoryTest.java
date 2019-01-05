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
public class LineRepositoryTest {

    @Autowired
    private LineRepository lineRepository;

    @Test
    @Transactional
    @Rollback(true)
    public void saveLineTest() {
        Line line = new Line("9", TypeTransport.bus);
        Location l1 = new Location("Stanica1", "Vojvode Supljikca 50", 30.40f, 32.02f, "Station");
        Station s1 = new Station(l1, line);
        Location l2 = new Location("Stanica2", "Vojvode Supljikca 1", 30.42f, 32.02f, "Station");
        Station s2 = new Station(l2, line);
        Location l3 = new Location("Stanica3", "Vojvode Bojovica 11", 30.42f, 32.04f, "Station");
        Station s3 = new Station(l3, line);
        Location l4 = new Location("Stanica4", "Vojvode Bojovica 99", 30.42f, 32.06f, "Station");
        Station s4 = new Station(l4, line);
        List<Station> stations = new ArrayList<>();
        stations.add(s1);
        stations.add(s2);
        stations.add(s3);
        stations.add(s4);
        line.setStation_list(stations);

        Line savedLine = lineRepository.save(line);
        assertNotNull(savedLine);
        assertEquals(line.getLine_type(), savedLine.getLine_type());
        assertEquals(line.getName(), savedLine.getName());
        assertEquals(line.getStation_list(), savedLine.getStation_list());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void saveLineTest2() {
        lineRepository.save(new Line());
    }

}

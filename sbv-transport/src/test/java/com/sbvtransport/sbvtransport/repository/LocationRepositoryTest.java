package com.sbvtransport.sbvtransport.repository;

import com.sbvtransport.sbvtransport.model.Location;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
public class LocationRepositoryTest {

    @Autowired
    private LocationRepository locationRepository;

    @Test
    @Transactional
    @Rollback(true)
    public void saveLocationTest() {
        Location location = new Location("Stanica1", "Vojvode Supljikca 50", 30.40f, 32.02f, "Station");
        Location savedLocation = locationRepository.save(location);
        assertNotNull(savedLocation);
        assertEquals(location.getLocation_name(), savedLocation.getLocation_name());
        assertEquals(location.getAddress(), savedLocation.getAddress());
        assertEquals(location.getLatitude(), savedLocation.getLatitude());
        assertEquals(location.getLongitude(), savedLocation.getLongitude());
        assertEquals(location.getType(), savedLocation.getType());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void saveLocationTest2() {
        locationRepository.save(new Location());
    }

}

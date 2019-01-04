package com.sbvtransport.sbvtransport.service;


import com.sbvtransport.sbvtransport.dto.LocationDTO;
import com.sbvtransport.sbvtransport.model.Location;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import static org.springframework.test.util.AssertionErrors.assertTrue;
import static org.springframework.test.util.AssertionErrors.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
public class LocationServiceTest {

    @Autowired
    private ILocationService locationService;

    @Test
    public void findAllTest() {
        List<Location> locations = locationService.findAll();
        assertThat(locations).hasSize(3);
    }

    @Test
    public void getOneTest() {
        Location location = locationService.getOne(3L);

        assertThat(location).isNotNull();
        assertThat(location.getId()).isEqualTo(3L);
        assertThat(location.getLocation_name()).isEqualTo("Stanica2");
        assertThat(location.getAddress()).isEqualTo("Vojvode Supljikca 99");
        assertThat(location.getLatitude()).isEqualTo(32.40f);
        assertThat(location.getLongitude()).isEqualTo(34.02f);
        assertThat(location.getType()).isEqualTo("Station");
    }

    @Test(expected = NullPointerException.class)
    public void getNOneTest() {
        Location location = locationService.getOne(10L);
        assertThat(location).isNull();
    }

    @Test
    @Transactional
    @Rollback(true)
    public void createTest() {
        LocationDTO locationDTO = new LocationDTO("Stanica3", "Vojvode Supljikca 15", 31.40f, 33.02f, "Station");
        int dbSizeBefore = locationService.findAll().size();
        String success = locationService.create(locationDTO);
        assertThat(success).isEqualTo("Location has been successfully created!");
        assertThat(dbSizeBefore).isEqualTo(locationService.findAll().size() - 1);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void deleteTest() {
        int dbSizeBefore = locationService.findAll().size();
        boolean success = locationService.delete(3L);
        assertThat(success).isTrue();
        assertThat(dbSizeBefore).isEqualTo(locationService.findAll().size() + 1);
        try {
            Location loc = locationService.getOne(3L);
            assertThat(loc).isNull();
            fail("Did not throw an exception.");
        } catch (Exception ex) {
            assertThat(ex.getClass()).isEqualTo(EntityNotFoundException.class);
        }
    }

    @Test
    @Transactional
    @Rollback(true)
    public void deleteTest2() {
        int dbSizeBefore = locationService.findAll().size();
        boolean success = locationService.delete(33L);
        assertThat(success).isFalse();
        assertThat(dbSizeBefore).isEqualTo(locationService.findAll().size());
    }

}

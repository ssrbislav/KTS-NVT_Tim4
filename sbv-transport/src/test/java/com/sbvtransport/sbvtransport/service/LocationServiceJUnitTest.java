package com.sbvtransport.sbvtransport.service;

import com.sbvtransport.sbvtransport.model.Location;
import com.sbvtransport.sbvtransport.repository.LocationRepository;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.sbvtransport.sbvtransport.repository.StationRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LocationServiceJUnitTest {

    @Autowired
    private ILocationService locationService;

    @MockBean
    private LocationRepository locationRepository;

    @MockBean
    private StationRepository stationRepository;

    @Before
    public void setUp() {
        List<Location> locations = new ArrayList<>();
//        Location l1 = new Location(1L, "Stanica1", "Vojvode Supljikca 50", 30.40f, 32.02f, "Station");
//        Location l2 = new Location(2L, "Stanica2", "Vojvode Supljikca 99", 50.35f, 32.22f, "Station");
//        locations.add(l1);
//        locations.add(l2);
        Mockito.when(locationRepository.findAll()).thenReturn(locations);

        Location location = new Location("Stanica3", "Vojvode Bojovica 11", 12.33f, 12.02f, "Station");
        Optional<Location> optionalLocation = Optional.of(location);
        Mockito.when(locationRepository.findById(1L)).thenReturn(optionalLocation);
        Mockito.when(locationRepository.getOne(1L)).thenReturn(location);
        Mockito.when(locationRepository.getOne(10L)).thenReturn(null);
        Mockito.when(locationRepository.findById(10L)).thenReturn(null);
    }

    @Test
    public void findAllTest() {
        List<Location> locations = locationService.findAll();
        assertThat(locations).hasSize(2);
        assertNotNull(locations);
        assertThat(locations.get(0).getLocation_name()).isEqualTo("Stanica1");
        assertThat(locations.get(0).getAddress()).isEqualTo("Vojvode Supljikca 50");
        assertThat(locations.get(0).getLatitude()).isEqualTo(30.40f);
        assertThat(locations.get(0).getLongitude()).isEqualTo(32.02f);
        assertThat(locations.get(0).getType()).isEqualTo("Station");
    }

    @Test
    public void getOneTest() {
        Location location = locationService.getOne(1L);
        assertThat(location).isNotNull();
        assertThat(location.getLocation_name()).isEqualTo("Stanica3");
        assertThat(location.getAddress()).isEqualTo("Vojvode Bojovica 11");
        assertThat(location.getLatitude()).isEqualTo(12.33f);
        assertThat(location.getLongitude()).isEqualTo(12.02f);
        assertThat(location.getType()).isEqualTo("Station");
    }

    @Test(expected = NullPointerException.class)
    public void getNOneTest() {
        Location location = locationService.getOne(10L);
        assertThat(location).isNull();
    }

    @Test
    public void deleteTest() {
        boolean success = locationService.delete(1L);
        assertThat(success).isTrue();
    }

    @Test
    public void deleteTest2() {
        boolean success = locationService.delete(10L);
        assertThat(success).isFalse();
    }

}

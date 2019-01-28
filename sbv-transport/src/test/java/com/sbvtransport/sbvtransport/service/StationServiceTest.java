package com.sbvtransport.sbvtransport.service;


import com.sbvtransport.sbvtransport.dto.StationDTO;
import com.sbvtransport.sbvtransport.model.Line;
import com.sbvtransport.sbvtransport.model.Location;
import com.sbvtransport.sbvtransport.model.Station;
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
import static org.springframework.test.util.AssertionErrors.assertTrue;
import static org.springframework.test.util.AssertionErrors.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
@Rollback(value=true)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StationServiceTest {

    @Autowired
    private IStationService stationService;

    @Autowired
    private ILineService lineService;

    @Autowired ILocationService locationService;

    @Test
    public void aaafindAllTest() {
        List<Station> stations = stationService.findAll();
        assertThat(stations).hasSize(3);
    }

    @Test
    public void getOneTest() {
        Station station = stationService.getOne(3L);
        //Line line = lineService.getOne(station.getLine().getId());
        Location location = locationService.getOne(station.getLocation().getId());
        assertThat(station).isNotNull();
        assertThat(station.getId()).isEqualTo(3L);
       // assertThat(station.getLine()).isEqualTo(line);
        assertThat(station.getLocation()).isEqualTo(location);
    }

    @Test
    public void getNOneTest() {
        Station station = stationService.getOne(10L);
        assertThat(station).isNull();
    }

    @Test
    @Transactional
    @Rollback(true)
    public void createTest_OK() {
        StationDTO stationDTO = new StationDTO(1L, "first");
        int dbSizeBefore = stationService.findAll().size();
        Station station = stationService.create(stationDTO);
        assertThat(station.getZone().toString()).isEqualTo(stationDTO.getZone());
        assertThat(station.getLocation().getId()).isEqualTo(stationDTO.getLocation_id());
        assertThat(dbSizeBefore).isEqualTo(stationService.findAll().size() - 1);
    }

    @Test(expected = IllegalArgumentException.class)
    @Transactional
    @Rollback(true)
    public void createTest_BadZone() {
        StationDTO stationDTO = new StationDTO(1L, "x");
        Station station = stationService.create(stationDTO);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void createTest_BadLocation() {
        StationDTO stationDTO = new StationDTO(111L, "second");
        Station station = stationService.create(stationDTO);
        assertThat(station).isNull();
    }

    @Test
    @Transactional
    @Rollback(true)
    public void deleteTest() {
        int dbSizeBefore = stationService.findAll().size();
        boolean success = stationService.delete(3L);
        assertThat(success).isTrue();
        assertThat(dbSizeBefore).isEqualTo(stationService.findAll().size() + 1);
        try {
            Station loc = stationService.getOne(3L);
            assertThat(loc.isDeleted()).isTrue();
        } catch (Exception ex) {
            assertThat(ex.getClass()).isEqualTo(EntityNotFoundException.class);
        }
    }

    @Test
    @Transactional
    @Rollback(true)
    public void deleteTest2() {
        int dbSizeBefore = stationService.findAll().size();
        boolean success = stationService.delete(33L);
        assertThat(success).isFalse();
        assertThat(dbSizeBefore).isEqualTo(stationService.findAll().size());
    }

}

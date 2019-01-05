package com.sbvtransport.sbvtransport.service;


import com.sbvtransport.sbvtransport.dto.StationDTO;
import com.sbvtransport.sbvtransport.model.Line;
import com.sbvtransport.sbvtransport.model.Location;
import com.sbvtransport.sbvtransport.model.Station;
import org.junit.Before;
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
public class StationServiceTest {

    @Autowired
    private IStationService stationService;

    @Autowired
    private ILineService lineService;

    @Autowired ILocationService locationService;

    @Test
    public void findAllTest() {
        List<Station> stations = stationService.findAll();
        assertThat(stations).hasSize(3);
    }

    @Test
    public void getOneTest() {
        Station station = stationService.getOne(3L);
        Line line = lineService.getOne(station.getLine().getId());
        Location location = locationService.getOne(station.getLocation().getId());
        assertThat(station).isNotNull();
        assertThat(station.getId()).isEqualTo(3L);
        assertThat(station.getLine()).isEqualTo(line);
        assertThat(station.getLocation()).isEqualTo(location);
    }

    @Test(expected = EntityNotFoundException.class)
    public void getNOneTest() {
        Station station = stationService.getOne(10L);
        assertThat(station).isNull();
    }

    @Test
    @Transactional
    @Rollback(true)
    public void createTest() {
        StationDTO stationDTO = new StationDTO(1L, 1L);
        int dbSizeBefore = stationService.findAll().size();
        String success = stationService.create(stationDTO);
        assertThat(success).isEqualTo("The station has been successfully created.");
        assertThat(dbSizeBefore).isEqualTo(stationService.findAll().size() - 1);
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
            assertThat(loc).isNull();
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

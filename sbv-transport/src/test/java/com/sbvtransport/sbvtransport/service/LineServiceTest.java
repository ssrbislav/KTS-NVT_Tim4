package com.sbvtransport.sbvtransport.service;


import com.sbvtransport.sbvtransport.dto.AddFirstStationDTO;
import com.sbvtransport.sbvtransport.dto.LineDTO;
import com.sbvtransport.sbvtransport.enumeration.TypeTransport;
import com.sbvtransport.sbvtransport.enumeration.Zone;
import com.sbvtransport.sbvtransport.model.Line;
import com.sbvtransport.sbvtransport.model.Station;
import java.util.ArrayList;
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
public class LineServiceTest {

  @Autowired
  private ILineService lineService;

  @Autowired
  private IStationService stationService;

  //@Test
  public void aafindAllTest() {
      List<Line> lines = lineService.findAll();
      assertThat(lines).hasSize(3);
  }

  @Test
  public void getOneTest() {
      Line line = lineService.getOne(3L);

      assertThat(line).isNotNull();
      assertThat(line.getId()).isEqualTo(3L);
      assertThat(line.getName()).isEqualTo("7ca");
      assertThat(line.getLine_type()).isEqualTo(TypeTransport.trolley);
  }

  @Test
  public void getNOneTest() {
      Line line = lineService.getOne(10L);
      assertThat(line).isNull();
  }

  @Test
  @Transactional
  @Rollback(true)
  public void createTest() {
      LineDTO lineDTO = new LineDTO("bus", "nova_linija4", Zone.first);
      int dbSizeBefore = lineService.findAll().size();
      Line line = lineService.create(lineDTO);
      assertThat(line.getZone()).isEqualTo(lineDTO.getZone());
      assertThat(line.getLine_type().toString()).isEqualTo(lineDTO.getLine_type());
      assertThat(line.getName()).isEqualTo(lineDTO.getName());
      assertThat(dbSizeBefore).isEqualTo(lineService.findAll().size() - 1);
  }

  @Test
  @Transactional
  @Rollback(true)
  public void deleteTest() {
      int dbSizeBefore = lineService.findAll().size();
      boolean success = lineService.delete(3L);
      assertThat(success).isTrue();
      assertThat(dbSizeBefore).isEqualTo(lineService.findAll().size() + 1);
      try {
          Line loc = lineService.getOne(3L);
          assertThat(loc.isDeleted()).isTrue();
      } catch (Exception ex) {
          assertThat(ex.getClass()).isEqualTo(EntityNotFoundException.class);
      }
  }

  @Test
  @Transactional
  @Rollback(true)
  public void deleteTest2() {
      int dbSizeBefore = lineService.findAll().size();
      boolean success = lineService.delete(33L);
      assertThat(success).isFalse();
      assertThat(dbSizeBefore).isEqualTo(lineService.findAll().size());
  }

  @Test
  @Transactional
  @Rollback(true)
  public void addStationTest_OK() {
    AddFirstStationDTO dto = new AddFirstStationDTO();
    dto.setId_line(1L);
    dto.setId_station(1L);
    String addStation = lineService.addStation(dto);
    assertThat(addStation).isEqualTo("Station successfully added!");
    Line line = lineService.getOne(dto.getId_line());
    Station station = stationService.getOne(dto.getId_station());
    assertThat(line.getStation_list()).contains(station);
  }

  @Test
  @Transactional
  @Rollback(true)
  public void addStationTest_BadLine() {
    AddFirstStationDTO dto = new AddFirstStationDTO();
    dto.setId_line(111L);
    dto.setId_station(1L);
    String addStation = lineService.addStation(dto);
    assertThat(addStation).isEqualTo("");
  }

  @Test
  @Transactional
  @Rollback(true)
  public void addStationTest_BadStation() {
    AddFirstStationDTO dto = new AddFirstStationDTO();
    dto.setId_line(1L);
    dto.setId_station(13L);
    String addStation = lineService.addStation(dto);
    assertThat(addStation).isEqualTo("");
  }

  @Test
  @Transactional
  @Rollback(true)
  public void addStationListTest_OK() {
    List<AddFirstStationDTO> addFirstStationDTOList = new ArrayList<>();
    AddFirstStationDTO dto1 = new AddFirstStationDTO();
    dto1.setId_line(1L);
    dto1.setId_station(1L);
    AddFirstStationDTO dto2 = new AddFirstStationDTO();
    dto2.setId_line(1L);
    dto2.setId_station(2L);
    addFirstStationDTOList.add(dto1);
    addFirstStationDTOList.add(dto2);
    Line line = lineService.addListStations(addFirstStationDTOList);
    Station station1 = stationService.getOne(dto1.getId_station());
    Station station2 = stationService.getOne(dto2.getId_station());
    assertThat(line.getStation_list()).contains(station1);
    assertThat(line.getStation_list()).contains(station2);
  }

  @Test(expected = EntityNotFoundException.class)
  @Transactional
  @Rollback(true)
  public void addStationListTest_NoLine() {
    List<AddFirstStationDTO> addFirstStationDTOList = new ArrayList<>();
    AddFirstStationDTO dto1 = new AddFirstStationDTO();
    dto1.setId_line(111L);
    dto1.setId_station(1L);
    AddFirstStationDTO dto2 = new AddFirstStationDTO();
    dto2.setId_line(111L);
    dto2.setId_station(2L);
    addFirstStationDTOList.add(dto1);
    addFirstStationDTOList.add(dto2);
    Line line = lineService.addListStations(addFirstStationDTOList);
    assertThat(line).isNull();
  }

  @Test
  @Transactional
  @Rollback(true)
  public void addStationListTest_NoStations() {
    List<AddFirstStationDTO> addFirstStationDTOList = new ArrayList<>();
    AddFirstStationDTO dto1 = new AddFirstStationDTO();
    dto1.setId_line(1L);
    dto1.setId_station(123L);
    AddFirstStationDTO dto2 = new AddFirstStationDTO();
    dto2.setId_line(1L);
    dto2.setId_station(322L);
    addFirstStationDTOList.add(dto1);
    addFirstStationDTOList.add(dto2);
    Line line = lineService.addListStations(addFirstStationDTOList);
    assertThat(line).isNull();
  }

  @Test
  @Transactional
  @Rollback(true)
  public void addStationListTest_DifferentLines() {
    List<AddFirstStationDTO> addFirstStationDTOList = new ArrayList<>();
    AddFirstStationDTO dto1 = new AddFirstStationDTO();
    dto1.setId_line(2L);
    dto1.setId_station(2L);
    AddFirstStationDTO dto2 = new AddFirstStationDTO();
    dto2.setId_line(3L);
    dto2.setId_station(1L);
    addFirstStationDTOList.add(dto1);
    addFirstStationDTOList.add(dto2);
    Line line = lineService.addListStations(addFirstStationDTOList);
    Station station1 = stationService.getOne(dto1.getId_station());
    Station station2 = stationService.getOne(dto2.getId_station());
    assertThat(line.getStation_list()).contains(station1);
    assertThat(line.getStation_list()).contains(station2);
    Line l2 = lineService.getOne(3L);
    assertThat(l2.getStation_list()).doesNotContain(station2);
  }

}

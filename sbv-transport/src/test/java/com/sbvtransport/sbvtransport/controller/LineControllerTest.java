package com.sbvtransport.sbvtransport.controller;

import com.sbvtransport.sbvtransport.TestUtil;
import com.sbvtransport.sbvtransport.dto.AddFirstStationDTO;
import com.sbvtransport.sbvtransport.dto.LineDTO;
import com.sbvtransport.sbvtransport.enumeration.TypeTransport;
import com.sbvtransport.sbvtransport.enumeration.Zone;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;
import java.nio.charset.Charset;
import org.springframework.web.util.NestedServletException;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
@Rollback(value=true)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LineControllerTest {

    private static final String URL_PREFIX = "/api/line";
    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @PostConstruct
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void aaagetAllTest() throws Exception {
        mockMvc.perform(get(URL_PREFIX)).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$.[*].id").value(hasItem(2)))
                .andExpect(jsonPath("$.[*].name").value(hasItem("7ca")))
                .andExpect(jsonPath("$.[*].line_type").value(hasItem("subway")));
    }

    @Test
    public void getOneTest() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/getLine/1")).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("7ca"))
                .andExpect(jsonPath("$.line_type").value("bus"));
    }

    @Test
    @Transactional
    @Rollback(true)
    public void createTest() throws Exception {
        LineDTO lineDTO = new LineDTO("bus", "9", Zone.first);
        String json = TestUtil.json(lineDTO);
        this.mockMvc.perform(post(URL_PREFIX + "/addLine").contentType(contentType).content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5L))
                .andExpect(jsonPath("$.name").value(lineDTO.getName()))
                .andExpect(jsonPath("$.station_list").isEmpty())
                .andExpect(jsonPath("$.line_type").value(TypeTransport.valueOf(lineDTO.getLine_type()).toString()))
                .andExpect(jsonPath("$.zone").value(lineDTO.getZone().toString()))
                .andExpect(jsonPath("$.timetable").isEmpty())
                .andExpect(jsonPath("$.deleted").value(false));
    }

    @Test
    @Transactional
    @Rollback(true)
    public void updateStations() throws Exception {
        List<AddFirstStationDTO> addFirstStationDTOList = new ArrayList<>();
        AddFirstStationDTO dto1 = new AddFirstStationDTO();
        dto1.setId_line(1L);
        dto1.setId_station(1L);
        AddFirstStationDTO dto2 = new AddFirstStationDTO();
        dto2.setId_line(1L);
        dto2.setId_station(2L);
        addFirstStationDTOList.add(dto1);
        addFirstStationDTOList.add(dto2);
//        AddFirstStationDTO dto3 = new AddFirstStationDTO();
//        dto3.setId_line(2L);
//        dto3.setId_station(3L);
        String json = TestUtil.json(addFirstStationDTOList);
        this.mockMvc.perform(post(URL_PREFIX + "/updateLine").contentType(contentType).content(json))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.station_list.[*].id").value(hasItem(1)))
            .andExpect(jsonPath("$.station_list.[*].id").value(hasItem(2)))
            .andExpect(jsonPath("$.id").value(dto1.getId_line()))
            .andExpect(jsonPath("$.id").value(dto2.getId_line()));
    }

    @Test
    @Transactional
    @Rollback(true)
    public void deleteTest() throws Exception {
        this.mockMvc.perform(post(URL_PREFIX + "/deleteLine/1")).andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    @Transactional
    @Rollback(true)
    public void deleteTest2() throws Exception {
        this.mockMvc.perform(post(URL_PREFIX + "/deleteLine/10")).andExpect(status().isBadRequest())
                .andExpect(content().string("false"));
    }

    @Test
    @Transactional
    @Rollback(true)
    public void addStationTest_OK() throws Exception {
        AddFirstStationDTO dto = new AddFirstStationDTO();
        dto.setId_line(1L);
        dto.setId_station(1L);
        String json = TestUtil.json(dto);
        this.mockMvc.perform(post(URL_PREFIX + "/addStation").contentType(contentType).content(json))
            .andExpect(status().isOk());
//            .andExpect(content().string("Station successfully added!"));
    }

    @Test
    @Transactional
    @Rollback(true)
    public void addStationTest_BadLine() throws Exception {
        AddFirstStationDTO dto = new AddFirstStationDTO();
        dto.setId_line(10L);
        dto.setId_station(1L);
        String json = TestUtil.json(dto);
        this.mockMvc.perform(post(URL_PREFIX + "/addStation").contentType(contentType).content(json))
            .andExpect(status().isBadRequest())
            .andExpect(content().string(""));
    }

    @Test
    @Transactional
    @Rollback(true)
    public void addStationTest_BadStation() throws Exception {
        AddFirstStationDTO dto = new AddFirstStationDTO();
        dto.setId_line(1L);
        dto.setId_station(10L);
        String json = TestUtil.json(dto);
        this.mockMvc.perform(post(URL_PREFIX + "/addStation").contentType(contentType).content(json))
            .andExpect(status().isBadRequest())
            .andExpect(content().string(""));
    }

    @Test
    @Transactional
    @Rollback(true)
    public void addStations_OK() throws Exception {
        List<AddFirstStationDTO> addFirstStationDTOList = new ArrayList<>();
        AddFirstStationDTO dto1 = new AddFirstStationDTO();
        dto1.setId_line(1L);
        dto1.setId_station(1L);
        AddFirstStationDTO dto2 = new AddFirstStationDTO();
        dto2.setId_line(1L);
        dto2.setId_station(2L);
        addFirstStationDTOList.add(dto1);
        addFirstStationDTOList.add(dto2);
//        AddFirstStationDTO dto3 = new AddFirstStationDTO();
//        dto3.setId_line(2L);
//        dto3.setId_station(3L);
        String json = TestUtil.json(addFirstStationDTOList);
        this.mockMvc.perform(post(URL_PREFIX + "/addListStation").contentType(contentType).content(json))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.station_list.[*].id").value(hasItem(1)))
            .andExpect(jsonPath("$.station_list.[*].id").value(hasItem(2)))
            .andExpect(jsonPath("$.id").value(dto1.getId_line()))
            .andExpect(jsonPath("$.id").value(dto2.getId_line()));
    }

    @Test
    @Transactional
    @Rollback(true)
    public void addStations_DeletedLine() throws Exception {
        List<AddFirstStationDTO> addFirstStationDTOList = new ArrayList<>();
        AddFirstStationDTO dto1 = new AddFirstStationDTO();
        dto1.setId_line(4L);
        dto1.setId_station(1L);
        AddFirstStationDTO dto2 = new AddFirstStationDTO();
        dto2.setId_line(4L);
        dto2.setId_station(2L);
        addFirstStationDTOList.add(dto1);
        addFirstStationDTOList.add(dto2);
//        AddFirstStationDTO dto3 = new AddFirstStationDTO();
//        dto3.setId_line(2L);
//        dto3.setId_station(3L);
        String json = TestUtil.json(addFirstStationDTOList);
        this.mockMvc.perform(post(URL_PREFIX + "/addListStation").contentType(contentType).content(json))
            .andExpect(status().isBadRequest())
            .andExpect(content().string(""));
    }

    @Test(expected = NestedServletException.class)
    @Transactional
    @Rollback(true)
    public void addStations_NoLine() throws Exception {
        List<AddFirstStationDTO> addFirstStationDTOList = new ArrayList<>();
        AddFirstStationDTO dto1 = new AddFirstStationDTO();
        dto1.setId_line(30L);
        dto1.setId_station(1L);
        AddFirstStationDTO dto2 = new AddFirstStationDTO();
        dto2.setId_line(30L);
        dto2.setId_station(2L);
        addFirstStationDTOList.add(dto1);
        addFirstStationDTOList.add(dto2);
//        AddFirstStationDTO dto3 = new AddFirstStationDTO();
//        dto3.setId_line(2L);
//        dto3.setId_station(3L);
        String json = TestUtil.json(addFirstStationDTOList);
        this.mockMvc.perform(post(URL_PREFIX + "/addListStation").contentType(contentType).content(json))
            .andExpect(status().isBadRequest())
            .andExpect(content().string(""));
    }

    @Test
    @Transactional
    @Rollback(true)
    public void addStations_NoStations() throws Exception {
        List<AddFirstStationDTO> addFirstStationDTOList = new ArrayList<>();
        AddFirstStationDTO dto1 = new AddFirstStationDTO();
        dto1.setId_line(1L);
        dto1.setId_station(10L);
        AddFirstStationDTO dto2 = new AddFirstStationDTO();
        dto2.setId_line(1L);
        dto2.setId_station(20L);
        addFirstStationDTOList.add(dto1);
        addFirstStationDTOList.add(dto2);
//        AddFirstStationDTO dto3 = new AddFirstStationDTO();
//        dto3.setId_line(2L);
//        dto3.setId_station(3L);
        String json = TestUtil.json(addFirstStationDTOList);
        this.mockMvc.perform(post(URL_PREFIX + "/addListStation").contentType(contentType).content(json))
            .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void addStations_DifferentLines() throws Exception {
        List<AddFirstStationDTO> addFirstStationDTOList = new ArrayList<>();
        AddFirstStationDTO dto1 = new AddFirstStationDTO();
        dto1.setId_line(1L);
        dto1.setId_station(1L);
        AddFirstStationDTO dto2 = new AddFirstStationDTO();
        dto2.setId_line(2L);
        dto2.setId_station(2L);
        addFirstStationDTOList.add(dto1);
        addFirstStationDTOList.add(dto2);
//        AddFirstStationDTO dto3 = new AddFirstStationDTO();
//        dto3.setId_line(2L);
//        dto3.setId_station(3L);
        String json = TestUtil.json(addFirstStationDTOList);
        this.mockMvc.perform(post(URL_PREFIX + "/addListStation").contentType(contentType).content(json))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.station_list.[*].id").value(hasItem(1)));
    }

}

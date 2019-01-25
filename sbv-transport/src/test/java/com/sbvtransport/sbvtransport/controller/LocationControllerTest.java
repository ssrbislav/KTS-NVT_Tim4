package com.sbvtransport.sbvtransport.controller;

import com.sbvtransport.sbvtransport.TestUtil;
import com.sbvtransport.sbvtransport.dto.LocationDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
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

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
public class LocationControllerTest {

    private static final String URL_PREFIX = "/api/location";
    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @PostConstruct
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void getAllTest() throws Exception {
        mockMvc.perform(get(URL_PREFIX)).andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$.[*].id").value(hasItem(2)))
                .andExpect(jsonPath("$.[*].location_name").value(hasItem("Banatic")))
                .andExpect(jsonPath("$.[*].address").value(hasItem("150, Rumenacka, Банатић, Novi Sad, Novi Sad City, South Backa District, Vojvodina, 21138, Serbia")))
                .andExpect(jsonPath("$.[*].latitude").value(hasItem(45.2652)))
                .andExpect(jsonPath("$.[*].longitude").value(hasItem(19.8159)))
                .andExpect(jsonPath("$.[*].type").value(hasItem("station")));
    }

    @Test
    public void getOneTest() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/getLocation/2")).andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.location_name").value("Banatic"))
                .andExpect(jsonPath("$.address").value("150, Rumenacka, Банатић, Novi Sad, Novi Sad City, South Backa District, Vojvodina, 21138, Serbia"))
                .andExpect(jsonPath("$.latitude").value(45.2652))
                .andExpect(jsonPath("$.longitude").value(19.8159))
                .andExpect(jsonPath("$.type").value("station"));
    }

    @Test
    @Transactional
    @Rollback(true)
    public void createTest() throws Exception {
        LocationDTO locationDTO = new LocationDTO("Stanica2", "Vojvode Supljikca 99", 32.40f, 34.02f, "Station");
        String json = TestUtil.json(locationDTO);
        this.mockMvc.perform(post(URL_PREFIX + "/addLocation").contentType(contentType).content(json))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType));
    }

    @Test
    @Transactional
    @Rollback(true)
    public void deleteTest() throws Exception {
        this.mockMvc.perform(get(URL_PREFIX + "/deleteLocation/1")).andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(content().string("true"));
    }

    @Test
    @Transactional
    @Rollback(true)
    public void deleteTest2() throws Exception {
        this.mockMvc.perform(get(URL_PREFIX + "/deleteLocation/10")).andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(content().string("false"));
    }

}

package com.sbvtransport.sbvtransport.controller;


import com.sbvtransport.sbvtransport.TestUtil;
import com.sbvtransport.sbvtransport.dto.AddFirstStationDTO;
import com.sbvtransport.sbvtransport.dto.LineDTO;
import com.sbvtransport.sbvtransport.enumeration.TypeTransport;
import com.sbvtransport.sbvtransport.enumeration.Zone;
import com.sbvtransport.sbvtransport.model.Pricelist;
import java.util.ArrayList;
import java.util.Date;
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
public class PricelistControllerTest {

  private static final String URL_PREFIX = "/api/pricelist";
  private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset
      .forName("utf8"));
  private MockMvc mockMvc;

  @Autowired
  private WebApplicationContext webApplicationContext;

  @PostConstruct
  public void setup() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }

  @Test
  public void agetAllTest() throws Exception {
    mockMvc.perform(get(URL_PREFIX)).andExpect(status().isOk())
        .andExpect(content().contentType(contentType))
        .andExpect(jsonPath("$.[*].id").value(hasItem(2)))

        .andExpect(jsonPath("$.[*].valid_since").value(hasItem("2019-01-01")))
        .andExpect(jsonPath("$.[*].valid_until").value(hasItem("2019-12-31")))

        .andExpect(jsonPath("$.[*].active").value(hasItem(true)))

        .andExpect(jsonPath("$.[*].senior_discount_percentage").value(hasItem(10)))
        .andExpect(jsonPath("$.[*].student_discount_percentage").value(hasItem(15)))
        .andExpect(jsonPath("$.[*].standard_discount_percentage").value(hasItem(1)))

        .andExpect(jsonPath("$.[*].double_zone_premium_percentage").value(hasItem(180)))

        .andExpect(jsonPath("$.[*].bus_one_use_price").value(hasItem(65.0)))
        .andExpect(jsonPath("$.[*].bus_daily_use_price").value(hasItem(175.0)))
        .andExpect(jsonPath("$.[*].bus_monthly_use_price").value(hasItem(1750.0)))
        .andExpect(jsonPath("$.[*].bus_yearly_use_price").value(hasItem(17500.0)))

        .andExpect(jsonPath("$.[*].subway_one_use_price").value(hasItem(85.0)))
        .andExpect(jsonPath("$.[*].subway_daily_use_price").value(hasItem(195.0)))
        .andExpect(jsonPath("$.[*].subway_monthly_use_price").value(hasItem(1950.0)))
        .andExpect(jsonPath("$.[*].subway_yearly_use_price").value(hasItem(19500.0)))

        .andExpect(jsonPath("$.[*].trolley_one_use_price").value(hasItem(75.0)))
        .andExpect(jsonPath("$.[*].trolley_daily_use_price").value(hasItem(185.0)))
        .andExpect(jsonPath("$.[*].trolley_monthly_use_price").value(hasItem(1850.0)))
        .andExpect(jsonPath("$.[*].trolley_yearly_use_price").value(hasItem(18500.0)))

        .andExpect(jsonPath("$.[*].deleted").value(hasItem(false)));
  }

  @Test
  public void getOneTest_OK() throws Exception {
    mockMvc.perform(get(URL_PREFIX + "/getPricelist/2")).andExpect(status().isOk())
        .andExpect(content().contentType(contentType))
        .andExpect(jsonPath("$.id").value(2))

        .andExpect(jsonPath("$.valid_since").value("2019-01-01"))
        .andExpect(jsonPath("$.valid_until").value("2019-12-31"))

        .andExpect(jsonPath("$.active").value(true))

        .andExpect(jsonPath("$.senior_discount_percentage").value(10))
        .andExpect(jsonPath("$.student_discount_percentage").value(15))
        .andExpect(jsonPath("$.standard_discount_percentage").value(1))

        .andExpect(jsonPath("$.double_zone_premium_percentage").value(180))

        .andExpect(jsonPath("$.bus_one_use_price").value(65.0))
        .andExpect(jsonPath("$.bus_daily_use_price").value(175.0))
        .andExpect(jsonPath("$.bus_monthly_use_price").value(1750.0))
        .andExpect(jsonPath("$.bus_yearly_use_price").value(17500.0))

        .andExpect(jsonPath("$.subway_one_use_price").value(85.0))
        .andExpect(jsonPath("$.subway_daily_use_price").value(195.0))
        .andExpect(jsonPath("$.subway_monthly_use_price").value(1950.0))
        .andExpect(jsonPath("$.subway_yearly_use_price").value(19500.0))

        .andExpect(jsonPath("$.trolley_one_use_price").value(75.0))
        .andExpect(jsonPath("$.trolley_daily_use_price").value(185.0))
        .andExpect(jsonPath("$.trolley_monthly_use_price").value(1850.0))
        .andExpect(jsonPath("$.trolley_yearly_use_price").value(18500.0))

        .andExpect(jsonPath("$.deleted").value(false));
  }

  @Test
  public void getOneTest_NoPricelist() throws Exception {
    mockMvc.perform(get(URL_PREFIX + "/getPricelist/55"))
        .andExpect(status().isNotFound());
  }

  @Test
  @Transactional
  @Rollback(true)
  public void createTest() throws Exception {
    Pricelist pricelist = new Pricelist();
    pricelist.setDeleted(false);
    pricelist.setActive(true);
    pricelist.setBus_daily_use_price(11.00);
    pricelist.setBus_monthly_use_price(11.00);
    pricelist.setBus_one_use_price(11.00);
    pricelist.setBus_yearly_use_price(11.00);
    pricelist.setSubway_daily_use_price(11.00);
    pricelist.setSubway_monthly_use_price(11.00);
    pricelist.setSubway_one_use_price(11.00);
    pricelist.setSubway_yearly_use_price(11.00);
    pricelist.setTrolley_daily_use_price(11.00);
    pricelist.setTrolley_monthly_use_price(11.00);
    pricelist.setTrolley_one_use_price(11.00);
    pricelist.setTrolley_yearly_use_price(11.00);
    pricelist.setDouble_zone_premium_percentage(200);
    pricelist.setSenior_discount_percentage(10);
    pricelist.setStandard_discount_percentage(1);
    pricelist.setStudent_discount_percentage(15);
    pricelist.setValid_since(new Date());
    pricelist.setValid_until(new Date(343584958495L));
    String json = TestUtil.json(pricelist);
    this.mockMvc.perform(post(URL_PREFIX + "/addPricelist").contentType(contentType).content(json))
        .andExpect(status().isOk())
        .andExpect(content().contentType(contentType));
  }

  @Test(expected = NestedServletException.class)
  @Transactional
  @Rollback(true)
  public void createTest_EmptyFields() throws Exception {
    Pricelist pricelist = new Pricelist();
    String json = TestUtil.json(pricelist);
    this.mockMvc.perform(post(URL_PREFIX + "/addPricelist").contentType(contentType).content(json))
        .andExpect(status().isBadRequest());
  }

  @Test
  @Transactional
  @Rollback(true)
  public void deleteTest_OK() throws Exception {
    this.mockMvc.perform(get(URL_PREFIX + "/deletePricelist/1"))
        .andExpect(status().isOk())
        .andExpect(content().string("true"));
  }

  @Test
  @Transactional
  @Rollback(true)
  public void deleteTest_NoPricelist() throws Exception {
    this.mockMvc.perform(get(URL_PREFIX + "/deletePricelist/100"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("false"));
  }

}

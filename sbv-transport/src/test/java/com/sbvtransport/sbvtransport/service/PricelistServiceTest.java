package com.sbvtransport.sbvtransport.service;

import com.sbvtransport.sbvtransport.model.Pricelist;
import java.util.Date;
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
import static org.junit.Assert.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertTrue;
import static org.springframework.test.util.AssertionErrors.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
@Rollback(value=true)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PricelistServiceTest {

  @Autowired
  private IPricelistService pricelistService;

  //@Test
  public void aafindAllTest() {
    List<Pricelist> pricelists = pricelistService.findAll();
    assertThat(pricelists).hasSize(2);
  }

  @Test
  public void getOneTest() {
    Pricelist pricelist = pricelistService.getOne(3L);

    assertThat(pricelist).isNotNull();
    assertThat(pricelist.getId()).isEqualTo(3L);
  }

  @Test
  public void getNOneTest() {
    Pricelist pricelist = pricelistService.getOne(10L);
    assertThat(pricelist).isNull();
  }

  @Test
  public void getActiveTest() {
    Pricelist pricelist = pricelistService.getActive();

    assertThat(pricelist).isNotNull();
    assertThat(pricelist.getActive()).isEqualTo(true);
  }
  
  @Test
  @Transactional
  @Rollback(true)
  public void createTest() throws Exception {
    int dbSizeBeforeCreate = pricelistService.findAll().size();
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
    Pricelist newPricelist = pricelistService.create(pricelist);

    assertEquals(pricelist.getValid_until(), newPricelist.getValid_until());
    assertEquals(pricelist.getValid_since(), newPricelist.getValid_since());
    assertEquals(pricelist.getActive(), newPricelist.getActive());
    assertEquals(pricelist.getBus_daily_use_price(), newPricelist.getBus_daily_use_price());
    assertEquals(pricelist.getBus_monthly_use_price(), newPricelist.getBus_monthly_use_price());
    assertEquals(pricelist.getBus_one_use_price(), newPricelist.getBus_one_use_price());
    assertEquals(pricelist.getBus_yearly_use_price(), newPricelist.getBus_yearly_use_price());
    assertEquals(pricelist.getSubway_daily_use_price(), newPricelist.getSubway_daily_use_price());
    assertEquals(pricelist.getSubway_monthly_use_price(), newPricelist.getSubway_monthly_use_price());
    assertEquals(pricelist.getSubway_one_use_price(), newPricelist.getSubway_one_use_price());
    assertEquals(pricelist.getSubway_yearly_use_price(), newPricelist.getSubway_yearly_use_price());
    assertEquals(pricelist.getTrolley_daily_use_price(), newPricelist.getTrolley_daily_use_price());
    assertEquals(pricelist.getTrolley_monthly_use_price(), newPricelist.getTrolley_monthly_use_price());
    assertEquals(pricelist.getTrolley_one_use_price(), newPricelist.getTrolley_one_use_price());
    assertEquals(pricelist.getTrolley_yearly_use_price(), newPricelist.getTrolley_yearly_use_price());
    assertEquals(pricelist.getDouble_zone_premium_percentage(), newPricelist.getDouble_zone_premium_percentage());
    assertEquals(pricelist.getSenior_discount_percentage(), newPricelist.getSenior_discount_percentage());
    assertEquals(pricelist.getStandard_discount_percentage(), newPricelist.getStandard_discount_percentage());
    assertEquals(pricelist.getStudent_discount_percentage(), newPricelist.getStudent_discount_percentage());
    assertThat(dbSizeBeforeCreate == (pricelistService.findAll().size() - 1)).isTrue();
  }

  @Test
  @Transactional
  @Rollback(true)
  public void deleteTest() {
    int dbSizeBefore = pricelistService.findAll().size();
    boolean success = pricelistService.delete(1L);
    assertThat(success).isTrue();
    assertThat(dbSizeBefore).isEqualTo(pricelistService.findAll().size() + 1);
    try {
      Pricelist loc = pricelistService.getOne(3L);
      assertThat(loc.isDeleted()).isTrue();
    } catch (Exception ex) {
      assertThat(ex.getClass()).isEqualTo(EntityNotFoundException.class);
    }
  }

  @Test
  @Transactional
  @Rollback(true)
  public void deleteTest2() {
    int dbSizeBefore = pricelistService.findAll().size();
    boolean success = pricelistService.delete(33L);
    assertThat(success).isFalse();
    assertThat(dbSizeBefore).isEqualTo(pricelistService.findAll().size());
  }

}

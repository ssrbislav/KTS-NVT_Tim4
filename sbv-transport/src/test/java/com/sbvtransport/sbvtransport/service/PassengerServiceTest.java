package com.sbvtransport.sbvtransport.service;

import com.sbvtransport.sbvtransport.dto.PassengerDTO;
import com.sbvtransport.sbvtransport.model.Passenger;
import com.sbvtransport.sbvtransport.model.Role;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
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
public class PassengerServiceTest {

  @Autowired
  private IPassengerService passengerService;

  @Test(expected = StackOverflowError.class)
  public void aafindAllTest() {
    List<Passenger> passengers = passengerService.findAll();
    assertThat(passengers).hasSize(2);
  }

  @Test
  public void getOneTest() {
    Passenger passenger = passengerService.getOne(4L);

    assertThat(passenger).isNotNull();
    assertThat(passenger.getId()).isEqualTo(4L);
  }

  @Test
  public void getNOneTest() {
    Passenger passenger = passengerService.getOne(10L);
    assertThat(passenger).isNull();
  }

//  @Test
//  @Transactional
//  @Rollback(true)
  public void createTest() {
    PassengerDTO passenger = new PassengerDTO();
    passenger.setUsername("putnikx");
    passenger.setPassword("password");
    passenger.setAddress("Adresa putnika");
    passenger.setEmail("mail@email.com");
    passenger.setFirst_name("Putnik");
    passenger.setLast_name("Prezivase");
    passenger.setPhone_number("06665452");
    Set<String> roles = new HashSet<>();
    roles.add("ROLE_PASSENGER");
    passenger.setRole(roles);
    passenger.setDate_birth(new Date());
    Passenger newPassenger = passengerService.create(passenger);
    System.out.println(newPassenger.toString());
    assertThat(newPassenger).isNotNull();
    assertThat(passenger.getUsername()).isEqualTo(newPassenger.getUsername());
    assertThat(passenger.getEmail()).isEqualTo(newPassenger.getEmail());
    assertThat(passenger.getAddress()).isEqualTo(newPassenger.getAddress());
    assertThat(passenger.getDate_birth()).isEqualTo(newPassenger.getDate_birth());
    assertThat(passenger.getFirst_name()).isEqualTo(newPassenger.getFirst_name());
    assertThat(passenger.getLast_name()).isEqualTo(newPassenger.getLast_name());
    assertThat(passenger.getPassword()).isEqualTo(newPassenger.getPassword());
    assertThat(passenger.getPhone_number()).isEqualTo(newPassenger.getPhone_number());
  }

  @Test
  @Transactional
  @Rollback(true)
  public void deleteTest() {
    int dbSizeBefore = passengerService.findAll().size();
    boolean success = passengerService.delete(3L);
    assertThat(success).isTrue();
    assertThat(dbSizeBefore).isEqualTo(passengerService.findAll().size() + 1);
    try {
      Passenger loc = passengerService.getOne(3L);
      assertThat(loc).isNull();
    } catch (Exception ex) {
      assertThat(ex.getClass()).isEqualTo(EntityNotFoundException.class);
    }
  }

  @Test
  @Transactional
  @Rollback(true)
  public void deleteTest2() {
    int dbSizeBefore = passengerService.findAll().size();
    boolean success = passengerService.delete(33L);
    assertThat(success).isFalse();
    assertThat(dbSizeBefore).isEqualTo(passengerService.findAll().size());
  }

}

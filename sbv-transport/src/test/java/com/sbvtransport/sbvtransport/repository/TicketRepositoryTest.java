package com.sbvtransport.sbvtransport.repository;

import com.sbvtransport.sbvtransport.enumeration.DemographicTicketType;
import com.sbvtransport.sbvtransport.enumeration.TicketType;
import com.sbvtransport.sbvtransport.enumeration.TypeTransport;
import com.sbvtransport.sbvtransport.enumeration.UserType;
import com.sbvtransport.sbvtransport.enumeration.Zone;
import com.sbvtransport.sbvtransport.model.Line;
import com.sbvtransport.sbvtransport.model.Location;
import com.sbvtransport.sbvtransport.model.Passenger;
import com.sbvtransport.sbvtransport.model.Station;
import com.sbvtransport.sbvtransport.model.Ticket;
import java.util.Date;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
public class TicketRepositoryTest {

  @Autowired
  private TicketRepository ticketRepository;

  @Autowired PassengerRepository passengerRepository;

  @Test
  @Transactional
  @Rollback(true)
  public void saveTicketTest() {

    Passenger passenger = new Passenger();
    passenger.setUsername("putnik");
    passenger.setPassword("password");
    passenger.setAddress("Adresa putnika");
    passenger.setEmail("mail@email.com");
    passenger.setFirst_name("Putnik");
    passenger.setLast_name("Prezivase");
    passenger.setPhone_number("06665452");
    passenger.setDate_birth(new Date());
    passenger.setUserType(UserType.standard);

    passenger = passengerRepository.save(passenger);

    Ticket ticket = new Ticket();
    ticket.setActive(true);
    ticket.setTicket_type(TicketType.daily);
    ticket.setZone(Zone.first);
    ticket.setType_transport(TypeTransport.bus);
    ticket.setDemographic_type(DemographicTicketType.standard);
    ticket.setTime_expired(new Date());
    ticket.setBlock(false);
    ticket.setCode_transport("bla");
    ticket.setCost(111.00);
    ticket.setDate(new Date());
    ticket.setExpired(false);
    ticket.setPassenger(passenger);
    ticket.setDate_purchase(new Date());
    passengerRepository.save(passenger);
    Ticket newTicket = ticketRepository.save(ticket);

    assertEquals(ticket.isActive(), newTicket.isActive());
    assertEquals(ticket.getTicket_type(), newTicket.getTicket_type());
    assertEquals(ticket.getZone(), newTicket.getZone());
    assertEquals(ticket.getType_transport(), newTicket.getType_transport());
    assertEquals(ticket.getDemographic_type(), newTicket.getDemographic_type());
    assertEquals(ticket.getTime_expired(), newTicket.getTime_expired());
    assertEquals(ticket.isBlock(), newTicket.isBlock());
    assertEquals(ticket.getCode_transport(), newTicket.getCode_transport());
    assertEquals(ticket.getDate(), newTicket.getDate());
    assertEquals(ticket.isExpired(), newTicket.isExpired());
    assertEquals(ticket.getPassenger(), newTicket.getPassenger());
    assertEquals(ticket.getDate_purchase(), newTicket.getDate_purchase());

  }

  @Test(expected = DataIntegrityViolationException.class)
  public void saveTicketTest2() { ticketRepository.save(new Ticket()); }

}

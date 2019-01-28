package com.sbvtransport.sbvtransport.service;

import com.sbvtransport.sbvtransport.dto.PassengerDTO;
import com.sbvtransport.sbvtransport.dto.TicketDTO;
import com.sbvtransport.sbvtransport.enumeration.DemographicTicketType;
import com.sbvtransport.sbvtransport.enumeration.TicketType;
import com.sbvtransport.sbvtransport.enumeration.TypeTransport;
import com.sbvtransport.sbvtransport.enumeration.UserType;
import com.sbvtransport.sbvtransport.enumeration.Zone;
import com.sbvtransport.sbvtransport.model.Passenger;
import com.sbvtransport.sbvtransport.model.Ticket;
import com.sbvtransport.sbvtransport.model.Role;
import com.sbvtransport.sbvtransport.repository.PassengerRepository;
import com.sbvtransport.sbvtransport.repository.TicketRepository;
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
import static org.junit.Assert.assertNotEquals;
import static org.springframework.test.util.AssertionErrors.assertTrue;
import static org.springframework.test.util.AssertionErrors.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
@Rollback(value=true)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TicketServiceTest {

	@Autowired
	private ITicketService ticketService;

	@Autowired
	IPassengerService passengerService;

	@Test
	public void aafindAllTest() {
		List<Ticket> tickets = ticketService.findAll();
		assertThat(tickets).hasSize(3);
	}

	@Test
	public void getOneTest() {
		Ticket ticket = ticketService.getOne(1L);

		assertThat(ticket).isNotNull();
		assertThat(ticket.getId()).isEqualTo(1L);
	}

	@Test
	public void getNOneTest() {
		Ticket ticket = ticketService.getOne(10L);
		assertThat(ticket).isNull();
	}

	@Test
	@Transactional
	@Rollback(true)
	public void createTest() {
		int sz = ticketService.findAll().size();
		Passenger passenger = passengerService.getOne(3L);

		TicketDTO ticket = new TicketDTO();
		ticket.setString("first");
		ticket.setIdPassenger(3L);
		ticket.setTicket_type(TicketType.daily.toString());
		ticket.setZone(Zone.first.toString());
		ticket.setType_transport(TypeTransport.bus.toString());
		ticket.setDemographic_type(DemographicTicketType.standard.toString());
		ticket.setCode_transport("bla");
		ticket.setDate(new Date());
		Ticket newTicket = ticketService.create(ticket);
		assertThat(newTicket).isNotNull();
		assertEquals(ticket.getTicket_type(), newTicket.getTicket_type().toString());
		assertEquals(ticket.getZone(), newTicket.getZone().toString());
		assertEquals(ticket.getType_transport(), newTicket.getType_transport().toString());
		assertEquals(ticket.getDemographic_type(), newTicket.getDemographic_type().toString());
		assertEquals(ticket.getCode_transport(), newTicket.getCode_transport());
		assertEquals(ticket.getDate(), newTicket.getDate());
		assertEquals(passenger, newTicket.getPassenger());
		assertThat(sz).isEqualTo(ticketService.findAll().size() - 1);
	}

	@Test
	@Transactional
	@Rollback(true)
	public void deleteTest() {
		int dbSizeBefore = ticketService.findAll().size();
		boolean success = ticketService.delete(3L);
		assertThat(success).isTrue();
		assertThat(dbSizeBefore).isEqualTo(ticketService.findAll().size() + 1);
		try {
			Ticket loc = ticketService.getOne(3L);
			assertThat(loc).isNull();
		} catch (Exception ex) {
			assertThat(ex.getClass()).isEqualTo(EntityNotFoundException.class);
		}
	}

	@Test
	@Transactional
	@Rollback(true)
	public void deleteTest2() {
		int dbSizeBefore = ticketService.findAll().size();
		boolean success = ticketService.delete(33L);
		assertThat(success).isFalse();
		assertThat(dbSizeBefore).isEqualTo(ticketService.findAll().size());
	}

	@Test
	@Transactional
	@Rollback(true)
	public void findByUserIDTest_OK() {
		List<Ticket> tickets = ticketService.findByUserID(4L);
		Passenger passenger = passengerService.getOne(4L);
		assertEquals(tickets, passenger.getTickets());
	}

	@Test
	@Transactional
	@Rollback(true)
	public void findByUserIDTest_IDMismatch() {
		List<Ticket> tickets = ticketService.findByUserID(3L);
		Passenger passenger = passengerService.getOne(4L);
		assertNotEquals(tickets, passenger.getTickets());
	}

	@Test
	@Transactional
	@Rollback(true)
	public void activateTest_OK() {
		Ticket ticket = ticketService.activate(3L);
		assertThat(ticket).isNotNull();
		assertThat(ticket.isActive()).isTrue();
	}

	@Test
	@Transactional
	@Rollback(true)
	public void activateTest_NoTicket() {
		Ticket ticket = ticketService.activate(33L);
		assertThat(ticket).isNull();
	}

}

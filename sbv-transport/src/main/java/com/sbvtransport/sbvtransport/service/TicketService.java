package com.sbvtransport.sbvtransport.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbvtransport.sbvtransport.dto.TicketDTO;
import com.sbvtransport.sbvtransport.enumeration.DemographicTicketType;
import com.sbvtransport.sbvtransport.enumeration.TicketType;
import com.sbvtransport.sbvtransport.enumeration.TypeTransport;
import com.sbvtransport.sbvtransport.enumeration.UserType;
import com.sbvtransport.sbvtransport.enumeration.Zone;
import com.sbvtransport.sbvtransport.model.Passenger;
import com.sbvtransport.sbvtransport.model.Ticket;
import com.sbvtransport.sbvtransport.repository.PassengerRepository;
import com.sbvtransport.sbvtransport.repository.TicketRepository;

@Service
public class TicketService implements ITicketService {

	@Autowired
	TicketRepository ticketRepository;

	@Autowired
	PassengerRepository passengerRepository;

	@Autowired
	BusService busService;

	@Autowired
	TrolleyService trolleyService;

	@Autowired
	SubwayService subwayService;

	@Autowired
	AdministratorService adminService;

	@Autowired
	PricelistService pricelistService;

	@Override
	public Ticket getOne(Long id) {
		return ticketRepository.getOne(id);
	}

	@Override
	public List<Ticket> findAll() {
		return ticketRepository.findAll();
	}

	@Override
	public String create(TicketDTO ticket) {

		Passenger passenger = null;

		if (passengerRepository.findAll().contains(passengerRepository.getOne(ticket.getIdPassenger()))) {

			passenger = passengerRepository.getOne(ticket.getIdPassenger());
			if (!(passenger.isActive()))
				return "Passenger with that id is not active!";

		} else
			return "Passenger with that id doesn't exist!";

		if (ticket.getDemographic_type().equals(DemographicTicketType.senior))
			if (passenger.getUserType() != UserType.senior)
				return "This passenger can't buy tickets for seniors!";
			else if (!passenger.isDocument_validated()) {
				return "You do not have valid document!";
			}

		if (ticket.getDemographic_type().equals(DemographicTicketType.student))
			if (passenger.getUserType() != UserType.student)
				return "This passenger can't buy ticket for students!";
			else if (!passenger.isDocument_validated()) {
				return "You do not have valid document!";
			}

		Ticket t = new Ticket();
		Date d = new Date();
		System.out.println(d);

		t.setDate_purchase(d);
		t.setDate(ticket.getDate());
		t.setPassenger(passenger);

		if (ticket.getDate().before(d)) {
			boolean check = checkDate(ticket.getDate(), d);
			if (!(check)) {
				return "Date is not good!";
			}
		}

		for (TicketType typeOfTicket : TicketType.values())
			if (ticket.getTicket_type() != typeOfTicket)
				return "Ticket type is not correct!";

		t.setTicket_type(ticket.getTicket_type());

		for (Zone typeOfZone : Zone.values())
			if (ticket.getZone() != typeOfZone)
				return "Zone is not correct!";

		t.setZone(ticket.getZone());

		for (DemographicTicketType typeOfTicketDemographic : DemographicTicketType.values())
			if (ticket.getDemographic_type() != typeOfTicketDemographic)
				return "Demographic type is not correct!";

		t.setDemographic_type(ticket.getDemographic_type());

		for (TypeTransport typeOfTransport : TypeTransport.values())
			if (ticket.getType_transport() != typeOfTransport)
				return "Transport is not correct!";

		t.setType_transport(ticket.getType_transport());

		if (ticket.getTicket_type().equals(TicketType.oneUse)) {
			t.setActive(false);
		} else if (ticket.getTicket_type().equals(TicketType.daily)) {
			boolean check = checkDate(d, ticket.getDate());
			if (check)
				t.setActive(true);
			else
				t.setActive(false);
		} else
			t.setActive(true);

		t.setBlock(false);
		t.setExpired(false);

		if (ticket.getType_transport().equals(TypeTransport.bus)) {
			boolean exist = busService.codeExist(ticket.getCode_transport());
			if (!(exist)) {
				return "That bus doesn't exist!";
			}
		} else if (ticket.getType_transport().equals(TypeTransport.subway)) {
			boolean exist = subwayService.codeExist(ticket.getCode_transport());
			if (!(exist)) {
				return "That subway doesn't exist!";
			}
		} else {
			boolean exist = trolleyService.codeExist(ticket.getCode_transport());
			if (!(exist)) {
				return "That trolley doesn't exist!";
			}
		}
		t.setCode_transport(ticket.getCode_transport());

		// calculate the cost from price list
		t.setCost(pricelistService.calculatePrice(ticket.getType_transport(), ticket.getDemographic_type(),
				ticket.getTicket_type(), ticket.getZone()));

		if (ticket.getTicket_type().equals(TicketType.daily)) {
			t.setTime_expired(calculateExpiredDate(ticket.getDate(), 1));
		} else if (ticket.getTicket_type().equals(TicketType.monthly)) {
			t.setTime_expired(calculateExpiredDate(ticket.getDate(), 2));
		} else if (ticket.getTicket_type().equals(TicketType.year)) {
			t.setTime_expired(calculateExpiredDate(ticket.getDate(), 3));
		} else {
			t.setTime_expired(calculateExpiredDate(ticket.getDate(), 3));
		}

		ticketRepository.save(t);

		return "Your ticket has been successfully created";
	}

	@Override
	public Date calculateExpiredDate(Date date, int option) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);

		if (option == 1) {
			c.add(Calendar.DATE, 1);
			return c.getTime();
		} else if (option == 2) {
			c.add(Calendar.MONTH, 1);
			return c.getTime();
		} else {
			c.add(Calendar.YEAR, 1);
			System.out.println(c.getTime());
			return c.getTime();
		}

	}

	@Override
	public boolean checkDate(Date purchase, Date date) {

		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(purchase);
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(date);

		if (calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)
				&& calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH)
				&& calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH)) {
			return true;
		}
		return false;
	}

	@Override
	public Ticket update(Ticket ticket) {
		Optional<Ticket> updateTicket = ticketRepository.findById(ticket.getId());
		updateTicket.get().setActive(ticket.isActive());
		updateTicket.get().setBlock(ticket.isBlock());
		updateTicket.get().setCode_transport(ticket.getCode_transport());
		updateTicket.get().setCost(ticket.getCost());
		updateTicket.get().setDate(ticket.getDate());
		updateTicket.get().setDemographic_type(ticket.getDemographic_type());
		updateTicket.get().setExpired(ticket.isExpired());
		updateTicket.get().setPassenger(ticket.getPassenger());
		updateTicket.get().setTicket_type(ticket.getTicket_type());
		updateTicket.get().setTime_expired(ticket.getTime_expired());
		updateTicket.get().setType_transport(ticket.getType_transport());
		updateTicket.get().setZone(ticket.getZone());
		return ticketRepository.save(updateTicket.get());
	}

	@Override
	public boolean delete(Long id) {
		if (ticketRepository.findAll().contains(ticketRepository.getOne(id))) {
			ticketRepository.delete(ticketRepository.getOne(id));
			return true;
		}
		return false;
	}

	@Override
	public void changeBecauseTransport(String code) {

		for (Ticket ticket : findAll()) {
			if (ticket.getCode_transport().equals(code)) {
				Ticket t = ticketRepository.getOne(ticket.getId());
				t.setActive(false);
				update(t);
				ticketRepository.save(t);

			}
		}

	}

}

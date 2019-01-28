package com.sbvtransport.sbvtransport.service;

import com.sbvtransport.sbvtransport.enumeration.TypeTransport;
import com.sbvtransport.sbvtransport.enumeration.Zone;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbvtransport.sbvtransport.dto.TicketDTO;
import com.sbvtransport.sbvtransport.enumeration.DemographicTicketType;
import com.sbvtransport.sbvtransport.enumeration.TicketType;
import com.sbvtransport.sbvtransport.enumeration.UserType;
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
		return ticketRepository.findById(id).orElse(null);
	}

	@Override
	public List<Ticket> findAll() {
		return ticketRepository.findAll();
	}

	@Override
	public Ticket create(TicketDTO ticket) {

		Passenger passenger;

		if (passengerRepository.findAll().contains(passengerRepository.getOne(ticket.getIdPassenger()))) {
			passenger = passengerRepository.getOne(ticket.getIdPassenger());
			if (!(passenger.isActive())) {
        return null;
      }
		} else {
			return null;
		}

    Ticket t = new Ticket();
    Date d = new Date();

    t.setDemographic_type(DemographicTicketType.valueOf(ticket.getDemographic_type()));
    t.setType_transport(TypeTransport.valueOf(ticket.getType_transport()));
    t.setZone(Zone.valueOf(ticket.getZone()));
    t.setTicket_type(TicketType.valueOf(ticket.getTicket_type()));
    if (t.getDemographic_type().equals(DemographicTicketType.senior)) {
      if (!passenger.getUserType().equals(UserType.senior) || !passenger.isDocument_validated()) {
        return null;
      }
    }

    if (t.getDemographic_type().equals(DemographicTicketType.student)) {
      if (!passenger.getUserType().equals(UserType.student) || !passenger.isDocument_validated()) {
        return null;
      }
    }


		System.out.println(d);

		t.setDate_purchase(d);
		t.setDate(ticket.getDate());
		t.setPassenger(passenger);

		if (ticket.getDate().before(d)) {
			boolean check = checkDate(ticket.getDate(), d);
			if (!(check)) {
				return null;
			}
		}



		if (t.getTicket_type().equals(TicketType.oneUse)) {
			t.setActive(false);
		} else if (t.getTicket_type().equals(TicketType.daily)) {
			boolean check = checkDate(d, ticket.getDate());
			if (check)
				t.setActive(true);
			else
				t.setActive(false);
		} else
			t.setActive(true);

		t.setBlock(false);
		t.setExpired(false);

		t.setCode_transport(ticket.getCode_transport());

		t.setCost(pricelistService.calculatePrice(t.getType_transport(), t.getDemographic_type(),
				t.getTicket_type(), t.getZone()));

		if (t.getTicket_type().equals(TicketType.daily)) {
			t.setTime_expired(calculateExpiredDate(ticket.getDate(), 1));
		} else if (t.getTicket_type().equals(TicketType.monthly)) {
			t.setTime_expired(calculateExpiredDate(ticket.getDate(), 2));
		} else if (t.getTicket_type().equals(TicketType.year)) {
			t.setTime_expired(calculateExpiredDate(ticket.getDate(), 3));
		} else {
			t.setTime_expired(calculateExpiredDate(ticket.getDate(), 3));
		}

		return ticketRepository.save(t);
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
		
		if(code == null){
			return;
		}

		for (Ticket ticket : findAll()) {
			if (ticket.getCode_transport().equals(code)) {
				Ticket t = ticketRepository.getOne(ticket.getId());
				t.setActive(false);
				update(t);
				ticketRepository.save(t);

			}
		}

	}

	@Override
	public List<Ticket> findByUserID(Long id) {
		Passenger passenger = passengerRepository.findById(id).orElse(null);
		if (passenger == null || passenger.getTickets().isEmpty() || passenger.getTickets() == null) {
			return null;
		}
		return passenger.getTickets();
	}

	@Override
	public Ticket activate(Long id) {
		Ticket ticket = getOne(id);
		if (ticket == null) {
			return null;
		}
		if(ticket.isBlock()) {
			return null;
		}
		ticket.setActive(true);
		return update(ticket);
	}

}

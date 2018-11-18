package com.sbvtransport.sbvtransport.service;

import com.sbvtransport.sbvtransport.dto.TicketDTO;
import com.sbvtransport.sbvtransport.enumeration.DemographicTicketType;
import com.sbvtransport.sbvtransport.enumeration.TicketType;
import com.sbvtransport.sbvtransport.enumeration.TypeTransport;
import com.sbvtransport.sbvtransport.enumeration.Zone;
import com.sbvtransport.sbvtransport.model.Passenger;
import com.sbvtransport.sbvtransport.model.Ticket;
import com.sbvtransport.sbvtransport.repository.PassengerRepository;
import com.sbvtransport.sbvtransport.repository.TicketRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.assertj.core.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketService implements ITicketService {

	@Autowired
	TicketRepository ticketRepository;
  
	@Autowired
	PassengerRepository passengerRepository;

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
		
		Passenger passenger = passengerRepository.getOne(ticket.getIdPassenger());
		Ticket t = new Ticket();
		Date d = new Date();
		System.out.println(d);
		t.setDate_purchase(d);
		t.setDate(ticket.getDate());
		t.setPassenger(passenger);
		
		if(ticket.getDate().before(d)){
			boolean check = checkDate(ticket.getDate(), d);
			if(!(check)){
				return "Date is not good!";
			}
			
		}
		
		ArrayList<String> ticketType= new ArrayList<String>();
		ticketType.addAll( Arrays.asList("oneUse","daily","year","monthly"));
		if(!(ticketType.contains(ticket.getTicket_type()))){
			return "Ticket type is not correct!";
		}
		t.setTicket_type(TicketType.valueOf(ticket.getTicket_type()));
		
		ArrayList<String> zoneType= new ArrayList<String>();
		zoneType.addAll( Arrays.asList("first","second"));
		if(!(zoneType.contains(ticket.getZone()))){
			return "Zone is not correct!";
		}
		t.setZone(Zone.valueOf(ticket.getZone()));
		
		ArrayList<String> demographicType= new ArrayList<String>();
		demographicType.addAll( Arrays.asList("senior","student","standard"));
		if(!(demographicType.contains(ticket.getDemographic_type()))){
			return "Demographic type is not correct!";
		}
		t.setDemographic_type(DemographicTicketType.valueOf(ticket.getDemographic_type()));
		
		ArrayList<String> typeTransport= new ArrayList<String>();
		typeTransport.addAll( Arrays.asList("bus","subway", "trolley"));
		if(!(typeTransport.contains(ticket.getType_transport()))){
			return "Transport is not correct!";
		}
		t.setType_transport(TypeTransport.valueOf(ticket.getType_transport()));
		
		if(ticket.getTicket_type().equals("oneUse")){
			t.setActive(false);
		}else{
			boolean check = checkDate(d,ticket.getDate());
			if(check){
				t.setActive(true);
			}else{
				t.setActive(false);
			}
		}
		
		t.setBlock(false);
		t.setExpired(false);
		t.setCost(ticket.getCost());
		t.setCode_transport(ticket.getCode_transport());
		
		if(ticket.getTicket_type().equals("daily")){ 
			t.setTime_expired(calculateExpiredDate(ticket.getDate(), 1));
		}else if(ticket.getTicket_type().equals("monthly")){
			t.setTime_expired(calculateExpiredDate(ticket.getDate(), 2));
		}else if(ticket.getTicket_type().equals("year")){
			t.setTime_expired(calculateExpiredDate(ticket.getDate(), 3));
		}
		
		//there is a error in time_expired, date from calendar is to big for database?
		
		ticketRepository.save(t);
	  
		return "Your ticket has been successfully created";
  }
	
	@Override
	public Date calculateExpiredDate(Date date, int option){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		
		if(option ==1){
			c.add(Calendar.DATE, 1);
			return c.getTime();
		}else if(option == 2){
			c.add(Calendar.MONTH, 1);
			return c.getTime();
		}else{
			c.add(Calendar.YEAR, 1);
			System.out.println(c.getTime());
			return c.getTime();
		}

	}
	
	@Override
	public boolean checkDate(Date purchase, Date date){
		
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(purchase);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date);
        
        if(calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) && 
        		calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH) && 
        		calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH)){
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
			if(ticket.getCode_transport().equals(code)){
				Ticket t = ticketRepository.getOne(ticket.getId());
				t.setActive(false);
				update(t);
				ticketRepository.save(t);

		}
	}
	

}

}

package com.sbvtransport.sbvtransport.service;

import com.sbvtransport.sbvtransport.dto.ReportResultTicketDTO;
import com.sbvtransport.sbvtransport.dto.ReportTicketDTO;
import com.sbvtransport.sbvtransport.enumeration.DemographicTicketType;
import com.sbvtransport.sbvtransport.enumeration.TicketType;
import com.sbvtransport.sbvtransport.enumeration.TypeTransport;
import com.sbvtransport.sbvtransport.enumeration.Zone;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sbvtransport.sbvtransport.model.Pricelist;
import com.sbvtransport.sbvtransport.model.Ticket;
import com.sbvtransport.sbvtransport.repository.PricelistRepository;

@Service
public class PricelistService implements IPricelistService {

	@Autowired
	PricelistRepository pricelistRepository;

	@Autowired
	TicketService ticketService;

	@Override
	public Pricelist getOne(Long id) {
		return pricelistRepository.findById(id).orElse(null);
	}

	@Override
	public Pricelist getActive() {
		for (Pricelist pricelist : pricelistRepository.findAll()) {
			if (pricelist.getActive() && new Date().after(pricelist.getValid_since())
					&& new Date().before(pricelist.getValid_until())) {
				return pricelist;
			}
		}
		return null;
	}

	@Override
	public double calculatePrice(TypeTransport typeTransport, DemographicTicketType demographicTicketType,
			TicketType ticketType, Zone zone) {

		double cost = 0;
		Pricelist pricelist = getActive();

		if (typeTransport.equals(TypeTransport.bus)) {
			if (ticketType.equals(TicketType.daily)) {
				cost = pricelist.getBus_daily_use_price();
			} else if (ticketType.equals(TicketType.monthly)) {
				cost = pricelist.getBus_monthly_use_price();
			} else if (ticketType.equals(TicketType.oneUse)) {
				cost = pricelist.getBus_one_use_price();
			} else {
				cost = pricelist.getBus_yearly_use_price();
			}
		} else if (typeTransport.equals(TypeTransport.subway)) {
			if (ticketType.equals(TicketType.daily)) {
				cost = pricelist.getSubway_daily_use_price();
			} else if (ticketType.equals(TicketType.monthly)) {
				cost = pricelist.getSubway_monthly_use_price();
			} else if (ticketType.equals(TicketType.oneUse)) {
				cost = pricelist.getSubway_one_use_price();
			} else {
				cost = pricelist.getSubway_yearly_use_price();
			}
		} else {
			if (ticketType.equals(TicketType.daily)) {
				cost = pricelist.getTrolley_daily_use_price();
			} else if (ticketType.equals(TicketType.monthly)) {
				cost = pricelist.getTrolley_monthly_use_price();
			} else if (ticketType.equals(TicketType.oneUse)) {
				cost = pricelist.getTrolley_one_use_price();
			} else {
				cost = pricelist.getTrolley_yearly_use_price();
			}
		}

		if (demographicTicketType.equals(DemographicTicketType.standard)) {
			cost = cost * (100 - pricelist.getStandard_discount_percentage()) / 100;
		} else if (demographicTicketType.equals(DemographicTicketType.senior)) {
			cost = cost * (100 - pricelist.getSenior_discount_percentage()) / 100;
		} else {
			cost = cost * (100 - pricelist.getStudent_discount_percentage()) / 100;
		}

		if (zone.equals(Zone.first)) {
			cost = cost;
		} else {
			cost = cost * pricelist.getDouble_zone_premium_percentage() / 100;
		}

		return cost;
	}

	@Override
	public List<Pricelist> findAll() {
		List<Pricelist> notDeleted = new ArrayList<>();
		List<Pricelist> findAll = pricelistRepository.findAll();
		for (Pricelist pricelist : findAll) {
			if (!pricelist.isDeleted()) {
				notDeleted.add(pricelist);
			}
		}
		return notDeleted;
	}

	@Override
	public Pricelist create(Pricelist pricelist) {

		pricelist.setDeleted(false);
		return pricelistRepository.save(pricelist);
	}

	@Override
	public Pricelist update(Pricelist pricelist) {

		Optional<Pricelist> updatePricelist = pricelistRepository.findById(pricelist.getId());
		updatePricelist.get().setActive(pricelist.getActive());
		updatePricelist.get().setBus_daily_use_price(pricelist.getBus_daily_use_price());
		updatePricelist.get().setBus_monthly_use_price(pricelist.getBus_monthly_use_price());
		updatePricelist.get().setBus_one_use_price(pricelist.getBus_one_use_price());
		updatePricelist.get().setBus_yearly_use_price(pricelist.getBus_yearly_use_price());
		updatePricelist.get().setDouble_zone_premium_percentage(pricelist.getDouble_zone_premium_percentage());
		updatePricelist.get().setSenior_discount_percentage(pricelist.getSenior_discount_percentage());
		updatePricelist.get().setStandard_discount_percentage(pricelist.getStandard_discount_percentage());
		updatePricelist.get().setStudent_discount_percentage(pricelist.getStudent_discount_percentage());
		updatePricelist.get().setSubway_daily_use_price(pricelist.getSubway_daily_use_price());
		updatePricelist.get().setSubway_monthly_use_price(pricelist.getSubway_monthly_use_price());
		updatePricelist.get().setSubway_one_use_price(pricelist.getSubway_one_use_price());
		updatePricelist.get().setSubway_yearly_use_price(pricelist.getSubway_yearly_use_price());
		updatePricelist.get().setTrolley_daily_use_price(pricelist.getTrolley_daily_use_price());
		updatePricelist.get().setTrolley_monthly_use_price(pricelist.getTrolley_monthly_use_price());
		updatePricelist.get().setTrolley_one_use_price(pricelist.getTrolley_one_use_price());
		updatePricelist.get().setTrolley_yearly_use_price(pricelist.getTrolley_yearly_use_price());
		updatePricelist.get().setValid_since(pricelist.getValid_since());
		updatePricelist.get().setValid_until(pricelist.getValid_until());
		return pricelistRepository.save(updatePricelist.get());
	}

	@Override
	public boolean delete(Long id) {
		for (Pricelist pricelist : findAll()) {
			if (pricelist.getId() == id) {
				pricelist.setDeleted(true);
				pricelistRepository.save(pricelist);
				return true;
			}
		}
		return false;
	}

	@Override
	public List<ReportResultTicketDTO> reportTicket(ReportTicketDTO report) {
		List<Pricelist> allPricelists = findAll();
		List<ReportResultTicketDTO> finalResult = new ArrayList<>();
		Date begin = new Date();
		Date end = new Date();
			
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy/MM/dd");
		
		for (Pricelist pricelist : allPricelists) {
		
			if(fmt.format(pricelist.getValid_since()).equals(fmt.format(report.getStart_date()))){
				System.out.println("Uslooo");
				begin = pricelist.getValid_since();
				if(pricelist.getValid_until().before(report.getFinished_date())){
					end = pricelist.getValid_until(); 
				}else if(pricelist.getValid_until().after(report.getFinished_date())){
					end = report.getFinished_date();
				}else{
					end = report.getFinished_date();
				}
				
				int number = calculateTickets(begin,end);
		
				ReportResultTicketDTO result = new ReportResultTicketDTO(pricelist, number, begin, end);
				finalResult.add(result);
				
			}else if(pricelist.getValid_since().before(report.getStart_date())){
				if(pricelist.getValid_until().after(report.getStart_date()) || 
						fmt.format(report.getFinished_date()).equals(fmt.format(pricelist.getValid_since()))){
					begin = report.getStart_date();
					if(pricelist.getValid_until().before(report.getFinished_date())){
						end = pricelist.getValid_until(); 
					}else if(pricelist.getValid_until().after(report.getFinished_date())){
						end = report.getFinished_date();
					}else{
						end = report.getFinished_date();
					}
					int number = calculateTickets(begin,end);
					
					ReportResultTicketDTO result = new ReportResultTicketDTO(pricelist, number, begin, end);
					finalResult.add(result);
				}
				
			}else if(pricelist.getValid_since().after(report.getStart_date())){
				if(report.getFinished_date().after(pricelist.getValid_since()) || 
						fmt.format(report.getStart_date()).equals(fmt.format(pricelist.getValid_until())) ){
					begin = pricelist.getValid_since();
					if(pricelist.getValid_until().before(report.getFinished_date())){
						end = pricelist.getValid_until(); 
					}else if(pricelist.getValid_until().after(report.getFinished_date())){
						end = report.getFinished_date();
					}else{
						end = report.getFinished_date();
					}
					int number = calculateTickets(begin,end);
					
					ReportResultTicketDTO result = new ReportResultTicketDTO(pricelist, number, begin, end);
					finalResult.add(result);
				}
				
			}else if(fmt.format(pricelist.getValid_since()).equals(fmt.format(report.getFinished_date()))){
				begin = pricelist.getValid_since();
				end = report.getFinished_date();
				int number = 0;
				for (Ticket ticket : ticketService.findAll()) {
					if(fmt.format(ticket.getDate_purchase()).equals(fmt.format(begin))){
						number = number + 1;
					}
				}
				ReportResultTicketDTO result = new ReportResultTicketDTO(pricelist, number, begin, end);
				finalResult.add(result);
			}
			
		}
		
		return finalResult;
	}
	
	@Override
	public int calculateTickets(Date begin, Date end){
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy/MM/dd");
		int number = 0;
		for (Ticket ticket : ticketService.findAll()) {
			if(ticket.getDate_purchase().after(begin) && ticket.getDate_purchase().before(end) ||
					fmt.format(ticket.getDate_purchase()).equals(fmt.format(begin)) || fmt.format(ticket.getDate_purchase()).equals(fmt.format(end))){
				number = number + 1;
			}
		}
		
		return number;
	}

}

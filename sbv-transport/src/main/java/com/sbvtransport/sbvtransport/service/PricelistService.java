package com.sbvtransport.sbvtransport.service;

import com.sbvtransport.sbvtransport.enumeration.DemographicTicketType;
import com.sbvtransport.sbvtransport.enumeration.TicketType;
import com.sbvtransport.sbvtransport.enumeration.TypeTransport;
import com.sbvtransport.sbvtransport.enumeration.Zone;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sbvtransport.sbvtransport.model.Pricelist;
import com.sbvtransport.sbvtransport.repository.PricelistRepository;

@Service
public class PricelistService implements IPricelistService {

	@Autowired
	PricelistRepository pricelistRepository;

	@Override
	public Pricelist getOne(Long id) {
		return pricelistRepository.getOne(id);
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
		return pricelistRepository.findAll();
	}

	@Override
	public Pricelist create(Pricelist pricelist) {

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
				pricelistRepository.delete(pricelist);
				return true;
			}
		}
		return false;
	}

}

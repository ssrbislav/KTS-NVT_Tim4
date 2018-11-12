package com.sbvtransport.sbvtransport.service;

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
            if(pricelist.getId() == id){
                pricelistRepository.delete(pricelist);
                return true;
            }
        }
        return false;
    }

}

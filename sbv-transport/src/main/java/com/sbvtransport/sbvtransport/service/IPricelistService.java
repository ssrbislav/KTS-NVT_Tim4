package com.sbvtransport.sbvtransport.service;

import com.sbvtransport.sbvtransport.model.Pricelist;
import java.util.List;

public interface IPricelistService {


    public Pricelist getOne(Long id);
    List<Pricelist> findAll();
    Pricelist create(Pricelist pricelist);
    Pricelist update(Pricelist pricelist);
    boolean delete (Long id);

}

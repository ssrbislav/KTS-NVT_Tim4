package com.sbvtransport.sbvtransport.controller;

import com.sbvtransport.sbvtransport.model.Pricelist;
import com.sbvtransport.sbvtransport.service.IPricelistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value="api/pricelist")
public class PricelistController {

  @Autowired
  IPricelistService pricelistService;

  @RequestMapping(value = "", method = RequestMethod.GET)
  public ResponseEntity<List<Pricelist>> getAll(){

    List<Pricelist> pricelists = pricelistService.findAll();

    return new ResponseEntity<>(pricelists, HttpStatus.OK);
  }

  @RequestMapping(value = "/addPricelist", method = RequestMethod.POST)
  public ResponseEntity<Pricelist> create(@RequestBody Pricelist pricelist){

    Pricelist newPricelist = pricelistService.create(pricelist);

    return new ResponseEntity<>(newPricelist,HttpStatus.OK);

  }

  @RequestMapping(value = "/updatePricelist", method = RequestMethod.POST)
  public ResponseEntity<Pricelist> update(@RequestBody Pricelist pricelist){

    Pricelist updatePricelist = pricelistService.update(pricelist);

    return new ResponseEntity<>(updatePricelist,HttpStatus.OK);

  }

  @RequestMapping(value = "/deletePricelist/{id}", method = RequestMethod.GET)
  public ResponseEntity<Boolean> delete(@PathVariable Long id){

    boolean delete = pricelistService.delete(id);

    return new ResponseEntity<>(delete,HttpStatus.OK);

  }

}

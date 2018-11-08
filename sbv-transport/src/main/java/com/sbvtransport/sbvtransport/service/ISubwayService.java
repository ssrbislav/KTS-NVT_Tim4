package com.sbvtransport.sbvtransport.service;

import java.util.List;

import com.sbvtransport.sbvtransport.model.Subway;

public interface ISubwayService {

	List<Subway> findAll();

	Subway getOne(Long id);

	Subway create(Subway subway);

	Subway update(Subway subway);

	boolean delete(Long id);

}

package com.sbvtransport.sbvtransport.service;

import java.util.List;

import com.sbvtransport.sbvtransport.dto.SubwayDTO;
import com.sbvtransport.sbvtransport.model.Subway;

public interface ISubwayService {

	List<Subway> findAll();

	Subway getOne(Long id);

	Subway create(SubwayDTO subway);

	Subway update(SubwayDTO subway);

	boolean delete(Long id);

}

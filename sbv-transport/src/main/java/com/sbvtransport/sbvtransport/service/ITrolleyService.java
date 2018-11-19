package com.sbvtransport.sbvtransport.service;

import java.util.List;

import com.sbvtransport.sbvtransport.dto.TrolleyDTO;
import com.sbvtransport.sbvtransport.model.Trolley;

public interface ITrolleyService {

	List<Trolley> findAll();
	Trolley getOne(Long id);
	Trolley create(TrolleyDTO trolley);
	Trolley update(Trolley trolley);
	boolean delete(Long id);
	boolean codeExist(String code);

}

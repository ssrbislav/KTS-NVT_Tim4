package com.sbvtransport.sbvtransport.service;

import java.util.List;
import com.sbvtransport.sbvtransport.dto.AddLocationDTO;
import com.sbvtransport.sbvtransport.dto.BusDTO;
import com.sbvtransport.sbvtransport.dto.ChangeTransportDTO;
import com.sbvtransport.sbvtransport.model.Bus;
import com.sbvtransport.sbvtransport.model.Line;

public interface IBusService {

	List<Bus> findAll();
	Bus getOne(Long id);
	Bus create(BusDTO bus);
	Bus update(Bus bus);
	Bus change(ChangeTransportDTO bus);
	boolean delete(Long id);
	boolean codeExist(String code);
	Line checkLine(Long lineId);
	Bus addLocation(AddLocationDTO addLocation);
	boolean checkIfLate(int time);
	void deleteBecauseLine(Long id_line);
}

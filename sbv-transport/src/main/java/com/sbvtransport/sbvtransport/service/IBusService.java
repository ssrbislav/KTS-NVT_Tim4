package com.sbvtransport.sbvtransport.service;

import java.util.List;
import com.sbvtransport.sbvtransport.dto.BusDTO;
import com.sbvtransport.sbvtransport.model.Bus;
import com.sbvtransport.sbvtransport.model.Line;

public interface IBusService {

  List<Bus> findAll();
  Bus getOne(Long id);
  String create(BusDTO bus);
  Bus update(Bus bus);
  boolean delete(Long id);
  boolean codeExist(String code);
  Line checkLine(Long lineId);
}

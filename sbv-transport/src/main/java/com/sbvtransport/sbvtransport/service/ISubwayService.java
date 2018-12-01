package com.sbvtransport.sbvtransport.service;

import java.util.List;
import com.sbvtransport.sbvtransport.dto.SubwayDTO;
import com.sbvtransport.sbvtransport.model.Line;
import com.sbvtransport.sbvtransport.model.Subway;

public interface ISubwayService {

  List<Subway> findAll();
  Subway getOne(Long id);
  String create(SubwayDTO subway);
  Subway update(Subway subway);
  boolean delete(Long id);
  boolean codeExist(String code);
  Line checkLine(Long lineId);
  
}

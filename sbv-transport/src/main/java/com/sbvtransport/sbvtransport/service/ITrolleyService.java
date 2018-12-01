package com.sbvtransport.sbvtransport.service;

import java.util.List;
import com.sbvtransport.sbvtransport.dto.TrolleyDTO;
import com.sbvtransport.sbvtransport.model.Line;
import com.sbvtransport.sbvtransport.model.Trolley;

public interface ITrolleyService {

  List<Trolley> findAll();
  Trolley getOne(Long id);
  String create(TrolleyDTO trolley);
  Trolley update(Trolley trolley);
  boolean delete(Long id);
  boolean codeExist(String code);
  Line checkLine(Long lineId);
  
}

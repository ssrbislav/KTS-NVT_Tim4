package com.sbvtransport.sbvtransport.service;

import com.sbvtransport.sbvtransport.dto.AddFirstStationDTO;
import com.sbvtransport.sbvtransport.dto.LineDTO;
import com.sbvtransport.sbvtransport.model.Line;

import java.util.List;

public interface ILineService {

  Line getOne(Long id);
  List<Line> findAll();
  Line create(LineDTO lineDTO);
  Line update(Line line);
  boolean delete(Long id);
  String addStation(AddFirstStationDTO addFirst);
  Line addListStations(List<AddFirstStationDTO> list);


}

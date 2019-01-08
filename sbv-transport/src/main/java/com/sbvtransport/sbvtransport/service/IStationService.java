package com.sbvtransport.sbvtransport.service;

import com.sbvtransport.sbvtransport.dto.AddFirstStationDTO;
import com.sbvtransport.sbvtransport.dto.StationDTO;
import com.sbvtransport.sbvtransport.model.Station;
import java.util.List;

public interface IStationService {

  Station getOne(Long id);
  List<Station> findAll();
  String create(StationDTO stationDTO);
  Station update(Station station);
  boolean delete(Long id);
  String addFirstStation(AddFirstStationDTO addStation);

}

package com.sbvtransport.sbvtransport.service;

import com.sbvtransport.sbvtransport.dto.ChangeStationDTO;
import com.sbvtransport.sbvtransport.dto.StationDTO;
import com.sbvtransport.sbvtransport.model.Station;
import java.util.List;

public interface IStationService {

  Station getOne(Long id);
  List<Station> findAll();
  Station create(StationDTO stationDTO);
  Station update(Station station);
  Station change(ChangeStationDTO station);
  boolean delete(Long id);


}

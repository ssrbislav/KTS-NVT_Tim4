package com.sbvtransport.sbvtransport.service;

import com.sbvtransport.sbvtransport.model.Station;
import java.util.List;

public interface IStationService {

  Station getOne(Long id);
  List<Station> findAll();
  Station create(Station station);
  Station update(Station station);
  boolean delete(Long id);

}

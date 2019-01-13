package com.sbvtransport.sbvtransport.service;

import com.sbvtransport.sbvtransport.dto.LocationDTO;
import com.sbvtransport.sbvtransport.model.Location;
import java.util.List;

public interface ILocationService {

  Location getOne(Long id);
  List<Location> findAll();
  Location create(LocationDTO locationDTO);
  Location update(Location location);
  boolean delete(Long id);

}

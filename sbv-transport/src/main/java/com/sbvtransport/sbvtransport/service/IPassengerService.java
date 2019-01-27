package com.sbvtransport.sbvtransport.service;

import java.util.List;
import com.sbvtransport.sbvtransport.dto.PassengerChangeBooleanDTO;
import com.sbvtransport.sbvtransport.dto.PassengerDTO;
import com.sbvtransport.sbvtransport.dto.UserDTO;
import com.sbvtransport.sbvtransport.model.Passenger;

public interface IPassengerService {

  List<Passenger> findAll();
  Passenger create(PassengerDTO passenger);
  Passenger update(Passenger passenger, Long id);
  boolean delete (Long id);
  Passenger logIn(UserDTO user);
  Passenger getOne(Long id);
  String changeActive(PassengerChangeBooleanDTO change);

  Passenger loadUserByUsername(String username);
}

package com.sbvtransport.sbvtransport.service;

import java.util.List;
import com.sbvtransport.sbvtransport.dto.FilterSearchControllerDTO;
import com.sbvtransport.sbvtransport.model.Controller;

public interface IControllerService {

  List<Controller> findAll();
  Controller create(Controller controller);
  Controller getOne(Long id);
  Controller loadUserByUsername(String username);
  Controller update(Controller controller);
  boolean delete (Long id);
  List<Controller> filterSearch(FilterSearchControllerDTO filterSearch);
  boolean checkTicket(Long id);
  public boolean blockTicket(Long id);
}

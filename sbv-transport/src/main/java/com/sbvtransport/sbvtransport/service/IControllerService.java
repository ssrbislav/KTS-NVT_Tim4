package com.sbvtransport.sbvtransport.service;

import java.util.List;

import com.sbvtransport.sbvtransport.model.Controller;

public interface IControllerService {

  List<Controller> findAll();
  Controller create(Controller controller);
  Controller update(Controller controller);
  boolean delete (Long id);

}

package com.sbvtransport.sbvtransport.service;

import com.sbvtransport.sbvtransport.model.Ticket;
import java.util.List;

public interface ITicketService {

  Ticket getOne(Long id);
  List<Ticket> findAll();
  Ticket create(Ticket ticket);
  Ticket update(Ticket ticket);
  boolean delete(Long id);

}

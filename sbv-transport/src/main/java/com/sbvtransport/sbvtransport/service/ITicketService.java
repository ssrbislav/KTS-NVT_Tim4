package com.sbvtransport.sbvtransport.service;

import java.util.Date;
import java.util.List;
import com.sbvtransport.sbvtransport.dto.TicketDTO;
import com.sbvtransport.sbvtransport.model.Ticket;

public interface ITicketService {

  Ticket getOne(Long id);
  List<Ticket> findAll();
  String create(TicketDTO ticket);
  Date calculateExpiredDate(Date date, int option);
  boolean checkDate(Date purchase, Date date);
  Ticket update(Ticket ticket);
  boolean delete(Long id);
  void changeBecauseTransport(String code);

}

package com.sbvtransport.sbvtransport.service;

import com.sbvtransport.sbvtransport.dto.ReportResultTicketDTO;
import com.sbvtransport.sbvtransport.dto.ReportTicketDTO;
import com.sbvtransport.sbvtransport.enumeration.DemographicTicketType;
import com.sbvtransport.sbvtransport.enumeration.TicketType;
import com.sbvtransport.sbvtransport.enumeration.TypeTransport;
import com.sbvtransport.sbvtransport.enumeration.Zone;
import com.sbvtransport.sbvtransport.model.Pricelist;

import java.util.Date;
import java.util.List;

public interface IPricelistService {


  public Pricelist getOne(Long id);
  Pricelist getActive();
  double calculatePrice(TypeTransport typeTransport, DemographicTicketType demographicTicketType, TicketType ticketType, Zone zone);
  List<Pricelist> findAll();
  Pricelist create(Pricelist pricelist);
  Pricelist update(Pricelist pricelist);
  boolean delete (Long id);
  List<ReportResultTicketDTO> reportTicket(ReportTicketDTO report);
  int calculateTickets(Date begin, Date end);
  

}

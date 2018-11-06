package com.sbvtransport.sbvtransport.model;

import com.sbvtransport.sbvtransport.enumeration.DemographicTicketType;
import com.sbvtransport.sbvtransport.enumeration.TicketType;
import com.sbvtransport.sbvtransport.enumeration.TypeTransport;
import com.sbvtransport.sbvtransport.enumeration.Zone;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "pricelist")
public class Pricelist {

  // TODO #1: Finish this class.

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="id", unique=true, nullable=false)
  private Long id;

  private Date valid_since;
  private Date valid_until;
  private DemographicTicketType demographic_ticket_type;
  private TicketType ticket_type;
  private TypeTransport type_transport;
  private Zone zone;
  private Double price;

  private Ticket ticket;

}

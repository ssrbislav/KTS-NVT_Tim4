package com.sbvtransport.sbvtransport.repository;

import com.sbvtransport.sbvtransport.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

}

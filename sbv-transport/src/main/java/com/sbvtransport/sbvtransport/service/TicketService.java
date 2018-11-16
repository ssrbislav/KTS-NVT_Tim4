package com.sbvtransport.sbvtransport.service;
import com.sbvtransport.sbvtransport.model.Ticket;
import com.sbvtransport.sbvtransport.repository.TicketRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketService implements ITicketService {

  @Autowired
  TicketRepository ticketRepository;

  @Override
  public Ticket getOne(Long id) {
    return ticketRepository.getOne(id);
  }

  @Override
  public List<Ticket> findAll() {
    return ticketRepository.findAll();
  }

  @Override
  public Ticket create(Ticket ticket) {
    return ticketRepository.save(ticket);
  }

  @Override
  public Ticket update(Ticket ticket) {
    Optional<Ticket> updateTicket = ticketRepository.findById(ticket.getId());
    updateTicket.get().setActive(ticket.isActive());
    updateTicket.get().setApproved(ticket.isApproved());
    updateTicket.get().setBlock(ticket.isBlock());
    updateTicket.get().setCode_transport(ticket.getCode_transport());
    updateTicket.get().setCost(ticket.getCost());
    updateTicket.get().setDate(ticket.getDate());
    updateTicket.get().setDemographic_type(ticket.getDemographic_type());
    updateTicket.get().setExpired(ticket.isExpired());
    updateTicket.get().setPassenger(ticket.getPassenger());
    updateTicket.get().setTicket_type(ticket.getTicket_type());
    updateTicket.get().setTime_expired(ticket.isTime_expired());
    updateTicket.get().setType_transport(ticket.getType_transport());
    updateTicket.get().setZone(ticket.getZone());
    return ticketRepository.save(updateTicket.get());
  }

  @Override
  public boolean delete(Long id) {
    if (ticketRepository.findAll().contains(ticketRepository.getOne(id))) {
      ticketRepository.delete(ticketRepository.getOne(id));
      return true;
    }
    return false;
  }

@Override
public boolean deleteBecauseTransport(String code) {
	int i = 0;
	
	for (Ticket ticket : findAll()) {
		if(ticket.getCode_transport().equals(code)){
		      ticketRepository.delete(ticketRepository.getOne(ticket.getId()));
		      i++;

		}
	}
	
	if (i>0){
		return true;
	}else{
		return false;
	}
}

}

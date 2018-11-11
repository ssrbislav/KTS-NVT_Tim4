package com.sbvtransport.sbvtransport.dto;

import com.sbvtransport.sbvtransport.model.Ticket;

public class BuyTicketDTO {
	
	private Long id;
	private Ticket ticket;
	
	public BuyTicketDTO(){
		
	}

	public BuyTicketDTO(Long id, Ticket ticket) {
		super();
		this.id = id;
		this.ticket = ticket;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}
	
	

}

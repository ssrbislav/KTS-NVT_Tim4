package com.sbvtransport.sbvtransport.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sbvtransport.sbvtransport.model.Pricelist;

public class ReportResultTicketDTO {
	
	private Pricelist pricelist;
	private int number_ticket;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
	private Date start;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
	private Date end;
	
	public ReportResultTicketDTO(){
		
	}


	public ReportResultTicketDTO(Pricelist pricelist, int number_ticket, Date start, Date end) {
		super();
		this.pricelist = pricelist;
		this.number_ticket = number_ticket;
		this.start = start;
		this.end = end;
	}



	public Pricelist getPricelist() {
		return pricelist;
	}

	public void setPricelist(Pricelist pricelist) {
		this.pricelist = pricelist;
	}

	public int getNumber_ticket() {
		return number_ticket;
	}

	public void setNumber_ticket(int number_ticket) {
		this.number_ticket = number_ticket;
	}


	public Date getStart() {
		return start;
	}


	public void setStart(Date start) {
		this.start = start;
	}


	public Date getEnd() {
		return end;
	}


	public void setEnd(Date end) {
		this.end = end;
	}
	
	
	

}

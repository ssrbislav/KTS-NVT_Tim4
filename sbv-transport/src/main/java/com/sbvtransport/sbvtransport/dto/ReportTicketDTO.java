package com.sbvtransport.sbvtransport.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ReportTicketDTO {
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date start_date;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date finished_date;
	
	public ReportTicketDTO(){
		
	}

	public ReportTicketDTO(Date start_date, Date finished_date) {
		super();
		this.start_date = start_date;
		this.finished_date = finished_date;
	}

	public Date getStart_date() {
		return start_date;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	public Date getFinished_date() {
		return finished_date;
	}

	public void setFinished_date(Date finished_date) {
		this.finished_date = finished_date;
	}
	
	

}

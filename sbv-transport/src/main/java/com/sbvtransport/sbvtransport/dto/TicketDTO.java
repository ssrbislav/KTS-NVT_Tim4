package com.sbvtransport.sbvtransport.dto;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

public class TicketDTO {
	
	private Long idPassenger;
	private String type_transport;
	private String zone;
	private String ticket_type;
	private String demographic_type;
	private String code_transport;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy.")
	private Date date;
		
	public TicketDTO(){
		
	}


	public TicketDTO(Long idPassenger, String type_transport, String zone, String ticket_type,
			String demographic_type, String code_transport, Date date) {
		super();
		this.idPassenger = idPassenger;
		this.type_transport = type_transport;
		this.zone = zone;
		this.ticket_type = ticket_type;
		this.demographic_type = demographic_type;
		this.code_transport = code_transport;
		this.date = date;
	}


	public Long getIdPassenger() {
		return idPassenger;
	}


	public void setIdPassenger(Long idPassenger) {
		this.idPassenger = idPassenger;
	}


	public String getType_transport() {
		return type_transport;
	}


	public void setType_transport(String type_transport) {
		this.type_transport = type_transport;
	}

	public String getZone() {
		return zone;
	}


	public void setZone(String zone) {
		this.zone = zone;
	}


	public String getTicket_type() {
		return ticket_type;
	}


	public void setTicket_type(String ticket_type) {
		this.ticket_type = ticket_type;
	}


	public String getDemographic_type() {
		return demographic_type;
	}


	public void setDemographic_type(String demographic_type) {
		this.demographic_type = demographic_type;
	}


	public String getCode_transport() {
		return code_transport;
	}


	public void setCode_transport(String code_transport) {
		this.code_transport = code_transport;
	}


	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}


	@Override
	public String toString() {
		return "TicketDTO [idPassenger=" + idPassenger + ", type_transport=" + type_transport +  ", zone=" + zone + ", ticket_type=" + ticket_type + ", demographic_type=" + demographic_type
				+ ", code_transport=" + code_transport + ", date=" + date + "]";
	}
	

}

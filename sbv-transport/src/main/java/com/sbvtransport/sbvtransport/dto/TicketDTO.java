package com.sbvtransport.sbvtransport.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sbvtransport.sbvtransport.enumeration.DemographicTicketType;
import com.sbvtransport.sbvtransport.enumeration.TicketType;
import com.sbvtransport.sbvtransport.enumeration.TypeTransport;
import com.sbvtransport.sbvtransport.enumeration.Zone;

public class TicketDTO {

	private Long idPassenger;
	private TypeTransport type_transport;
	private Zone zone;
	private TicketType ticket_type;
	private DemographicTicketType demographic_type;
	private String code_transport;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy.")
	private Date date;

	public TicketDTO() {

	}

	public TicketDTO(Long idPassenger, TypeTransport type_transport, Zone zone, TicketType ticket_type,
			DemographicTicketType demographic_type, String code_transport, Date date) {
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

	public TypeTransport getType_transport() {
		return type_transport;
	}

	public void setType_transport(TypeTransport type_transport) {
		this.type_transport = type_transport;
	}

	public Zone getZone() {
		return zone;
	}

	public void setZone(Zone zone) {
		this.zone = zone;
	}

	public TicketType getTicket_type() {
		return ticket_type;
	}

	public void setTicket_type(TicketType ticket_type) {
		this.ticket_type = ticket_type;
	}

	public DemographicTicketType getDemographic_type() {
		return demographic_type;
	}

	public void setDemographic_type(DemographicTicketType demographic_type) {
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
		return "TicketDTO [idPassenger=" + idPassenger + ", type_transport=" + type_transport + ", zone=" + zone
				+ ", ticket_type=" + ticket_type + ", demographic_type=" + demographic_type + ", code_transport="
				+ code_transport + ", date=" + date + "]";
	}

}

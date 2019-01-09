package com.sbvtransport.sbvtransport.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import java.util.Map;

public class TimetableDTO {

	private String transportType; // bus/subway/trolley
	private Long id_transport;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy. HH:mm")
	private Map<Long, Date> timetable;

	public TimetableDTO( String transportType, Map<Long, Date> timetable,Long id) {
		this.transportType = transportType;
		this.timetable = timetable;
		this.id_transport = id;
	}

	public TimetableDTO() {
	}

	public Map<Long, Date> getTimetable() {
		return timetable;
	}

	public void setTimetable(Map<Long, Date> timetable) {
		this.timetable = timetable;
	}

	public String getTransportType() {
		return transportType;
	}

	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}

	public Long getId_transport() {
		return id_transport;
	}

	public void setId_transport(Long id_transport) {
		this.id_transport = id_transport;
	}
	

}

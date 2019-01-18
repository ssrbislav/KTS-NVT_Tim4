package com.sbvtransport.sbvtransport.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class TimetableDTO {

	private String transportType; // bus/subway/trolley
	private Long id_transport;
	private List<ScheduleDTO> schedules;


	public TimetableDTO(@JsonProperty String transportType, @JsonProperty Long id_transport, List<ScheduleDTO> schedules) {
		this.transportType = transportType;
		this.id_transport = id_transport;
		this.schedules = schedules;
	}

	public TimetableDTO() {
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

	public List<ScheduleDTO> getSchedules() {
		return schedules;
	}

	public void setSchedules(List<ScheduleDTO> schedules) {
		this.schedules = schedules;
	}
}

package com.sbvtransport.sbvtransport.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import java.util.Map;

public class TimetableDTO {

	private StationDTO stationDTO;
	private String transportCode;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy. HH:mm")
	private Map<Long, Date> timetable;

	public TimetableDTO(StationDTO stationDTO, String transportCode, Map<Long, Date> timetable) {
		this.stationDTO = stationDTO;
		this.transportCode = transportCode;
		this.timetable = timetable;
	}

	public TimetableDTO() {
	}

	public StationDTO getStationDTO() {
		return stationDTO;
	}

	public void setStationDTO(StationDTO stationDTO) {
		this.stationDTO = stationDTO;
	}

	public String getTransportCode() {
		return transportCode;
	}

	public void setTransportCode(String transportCode) {
		this.transportCode = transportCode;
	}

	public Map<Long, Date> getTimetable() {
		return timetable;
	}

	public void setTimetable(Map<Long, Date> timetable) {
		this.timetable = timetable;
	}

}

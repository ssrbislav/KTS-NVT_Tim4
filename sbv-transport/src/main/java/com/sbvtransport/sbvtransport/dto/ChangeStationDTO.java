package com.sbvtransport.sbvtransport.dto;

import com.sbvtransport.sbvtransport.enumeration.Zone;

public class ChangeStationDTO {
	
	private Long id_station;
	private String location_name;
	private Zone zone;
	
	public ChangeStationDTO(){
		
	}

	public ChangeStationDTO(Long id_station, String location_name, Zone zone) {
		super();
		this.id_station = id_station;
		this.location_name = location_name;
		this.zone = zone;
	}

	public Long getId_station() {
		return id_station;
	}

	public void setId_station(Long id_station) {
		this.id_station = id_station;
	}

	public String getLocation_name() {
		return location_name;
	}

	public void setLocation_name(String location_name) {
		this.location_name = location_name;
	}

	public Zone getZone() {
		return zone;
	}

	public void setZone(Zone zone) {
		this.zone = zone;
	}
	
	
	

}

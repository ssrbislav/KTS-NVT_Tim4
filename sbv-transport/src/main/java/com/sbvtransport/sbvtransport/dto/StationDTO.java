package com.sbvtransport.sbvtransport.dto;

public class StationDTO {

	private Long location_id;
	private String zone;

	public StationDTO(Long location_id,String zone) {
		this.location_id = location_id;
		this.zone = zone;
	}
	
	public StationDTO(){
		
	}

	public Long getLocation_id() {
		return location_id;
	}

	public void setLocation_id(Long location_id) {
		this.location_id = location_id;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	
}

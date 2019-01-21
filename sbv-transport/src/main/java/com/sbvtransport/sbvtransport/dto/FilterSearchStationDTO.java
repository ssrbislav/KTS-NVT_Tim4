package com.sbvtransport.sbvtransport.dto;

public class FilterSearchStationDTO {
	
	private String zone;
	private String search_text;
	
	public FilterSearchStationDTO(){
		
	}

	public FilterSearchStationDTO(String zone, String search_text) {
		super();
		this.zone = zone;
		this.search_text = search_text;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public String getSearch_text() {
		return search_text;
	}

	public void setSearch_text(String search_text) {
		this.search_text = search_text;
	}
	
	

}

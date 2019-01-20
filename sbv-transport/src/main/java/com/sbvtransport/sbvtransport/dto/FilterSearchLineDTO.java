package com.sbvtransport.sbvtransport.dto;

public class FilterSearchLineDTO {
	
	private String zone;
	private String type;
	private Long id_station;
	private String search_text;
	
	public FilterSearchLineDTO(){
		
	}

	public FilterSearchLineDTO(String zone, String type, Long id_station, String search_text) {
		super();
		this.zone = zone;
		this.type = type;
		this.id_station = id_station;
		this.search_text = search_text;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getId_station() {
		return id_station;
	}

	public void setId_station(Long id_station) {
		this.id_station = id_station;
	}

	public String getSearch_text() {
		return search_text;
	}

	public void setSearch_text(String search_text) {
		this.search_text = search_text;
	}
	
	

}

package com.sbvtransport.sbvtransport.dto;

public class AddFirstStationDTO {
	
	private Long id_station;
	private Long id_line;
	
	public AddFirstStationDTO(){
		
	}

	public AddFirstStationDTO(Long id_station, Long id_line) {
		super();
		this.id_station = id_station;
		this.id_line = id_line;
	}

	public Long getId_station() {
		return id_station;
	}

	public void setId_station(Long id_station) {
		this.id_station = id_station;
	}

	public Long getId_line() {
		return id_line;
	}

	public void setId_line(Long id_line) {
		this.id_line = id_line;
	}
	
	

}

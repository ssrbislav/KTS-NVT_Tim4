package com.sbvtransport.sbvtransport.dto;

public class BusDTO {

	private String name;
	private Long id_line;
	private int time_arrive;

	public BusDTO() {

	}

	public BusDTO(boolean late, String name, Long id_line,int time_arrive) {
		this.name = name;
		this.id_line = id_line;
		this.time_arrive = time_arrive;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId_line() {
		return id_line;
	}

	public void setId_line(Long id_line) {
		this.id_line = id_line;
	}

	public int getTime_arrive() {
		return time_arrive;
	}

	public void setTime_arrive(int time_arrive) {
		this.time_arrive = time_arrive;
	}
	

}

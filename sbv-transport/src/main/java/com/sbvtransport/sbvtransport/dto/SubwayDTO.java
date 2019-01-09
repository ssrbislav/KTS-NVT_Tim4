package com.sbvtransport.sbvtransport.dto;

public class SubwayDTO {

	private int time_arrive;
	private String name;
	private Long id_line;

	public SubwayDTO() {

	}

	public SubwayDTO(String name, Long id_line,int time_arrive) {
		super();
		this.time_arrive = time_arrive;
		this.name = name;
		this.id_line = id_line;
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

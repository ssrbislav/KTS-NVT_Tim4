package com.sbvtransport.sbvtransport.dto;

public class TrolleyDTO {

	private int time_arrive;
	private String name;
	private Long id_line;

	public TrolleyDTO() {

	}

	public TrolleyDTO(String name, Long id_line,int time) {
		super();
		this.name = name;
		this.id_line = id_line;
		this.time_arrive = time;
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

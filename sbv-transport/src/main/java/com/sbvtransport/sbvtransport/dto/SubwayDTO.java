package com.sbvtransport.sbvtransport.dto;

public class SubwayDTO {
	
	private Long id;
	private String code;
	private double speed;
	private boolean late;
	private String name;
	private Long id_line;
	
	public SubwayDTO(){
		
	}

	public SubwayDTO(Long id, String code, double speed, boolean late, String name, Long id_line) {
		super();
		this.id = id;
		this.code = code;
		this.speed = speed;
		this.late = late;
		this.name = name;
		this.id_line = id_line;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public boolean isLate() {
		return late;
	}

	public void setLate(boolean late) {
		this.late = late;
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
	
	

}
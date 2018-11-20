package com.sbvtransport.sbvtransport.dto;

public class BusDTO {
	
	// TODO #5: String code ne treba (auto-generated - create).
	// TODO #6: Izbrisati speed iz modela.
	// TODO #7: Sve uraditi za Trolley, Subway.
	
//	private String code;
//	private double speed;
	private boolean late;
	private String name;
	private Long id_line;
	
	public BusDTO(){
		
	}

	public BusDTO(boolean late, String name, Long id_line) {
		this.late = late;
		this.name = name;
		this.id_line = id_line;
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

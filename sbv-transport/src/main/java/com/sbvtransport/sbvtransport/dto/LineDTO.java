package com.sbvtransport.sbvtransport.dto;

import com.sbvtransport.sbvtransport.enumeration.Zone;

public class LineDTO {

	private String line_type;
	private String name;
	private Zone zone;

	public LineDTO() {

	}

	public LineDTO(String line_type, String name, Zone zone) {
		super();
		this.line_type = line_type;
		this.name = name;
		this.zone = zone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLine_type() {
		return line_type;
	}

	public void setLine_type(String line_type) {
		this.line_type = line_type;
	}

	public Zone getZone() {
		return zone;
	}

	public void setZone(Zone zone) {
		this.zone = zone;
	}
	

}

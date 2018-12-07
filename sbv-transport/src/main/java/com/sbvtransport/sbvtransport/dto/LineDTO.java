package com.sbvtransport.sbvtransport.dto;

public class LineDTO {

	private String line_type;
	private String name;

	public LineDTO() {

	}

	public LineDTO(String line_type, String name) {
		super();
		this.line_type = line_type;
		this.name = name;
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

}

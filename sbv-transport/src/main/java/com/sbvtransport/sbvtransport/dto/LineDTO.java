package com.sbvtransport.sbvtransport.dto;

public class LineDTO {
	
	private String line_type;
	
	public LineDTO(){
		
	}

	public LineDTO(String line_type) {
		super();
		this.line_type = line_type;
	}

	public String getLine_type() {
		return line_type;
	}

	public void setLine_type(String line_type) {
		this.line_type = line_type;
	}
	
	

}

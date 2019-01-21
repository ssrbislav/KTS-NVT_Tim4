package com.sbvtransport.sbvtransport.dto;

public class FilterSearchControllerDTO {
	
	private String type;
	private String text_search;
	
	public FilterSearchControllerDTO(){
		
	}

	public FilterSearchControllerDTO(String type, String text_search) {
		super();
		this.type = type;
		this.text_search = text_search;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getText_search() {
		return text_search;
	}

	public void setText_search(String text_search) {
		this.text_search = text_search;
	}
	
	

}

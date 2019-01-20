package com.sbvtransport.sbvtransport.dto;

public class FilterSearchDTO {
	
	private Long id_line;
	private boolean late;
	private Long id_location;
	private String text_search;
	
	public FilterSearchDTO(){
		
	}

	public FilterSearchDTO(Long id_line, boolean late, Long id_location, String text_search) {
		super();
		this.id_line = id_line;
		this.late = late;
		this.id_location = id_location;
		this.text_search = text_search;
	}

	public Long getId_line() {
		return id_line;
	}

	public void setId_line(Long id_line) {
		this.id_line = id_line;
	}

	public boolean isLate() {
		return late;
	}

	public void setLate(boolean late) {
		this.late = late;
	}

	public Long getId_location() {
		return id_location;
	}

	public void setId_location(Long id_location) {
		this.id_location = id_location;
	}

	public String getText_search() {
		return text_search;
	}

	public void setText_search(String text_search) {
		this.text_search = text_search;
	}	

}

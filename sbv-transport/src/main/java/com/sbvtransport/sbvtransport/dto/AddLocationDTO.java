package com.sbvtransport.sbvtransport.dto;

//add location in transport(bus/subway/trolley)
public class AddLocationDTO {
	
	private Long id_transport;
	private Long id_location;
	
	public AddLocationDTO(){
		
	}

	public AddLocationDTO(Long id_transport, Long id_location) {
		super();
		this.id_transport = id_transport;
		this.id_location = id_location;
	}

	public Long getId_transport() {
		return id_transport;
	}

	public void setId_transport(Long id_transport) {
		this.id_transport = id_transport;
	}

	public Long getId_location() {
		return id_location;
	}

	public void setId_location(Long id_location) {
		this.id_location = id_location;
	}
	

}

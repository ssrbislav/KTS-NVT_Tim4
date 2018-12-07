package com.sbvtransport.sbvtransport.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sbvtransport.sbvtransport.model.Passenger;

public class DocumentDTO {

	private Long id;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy.")
	private Date dateOfUpload;
	private String imageLocation;
	private Long idPassenger;

	public Date getDateOfUpload() {
		return dateOfUpload;
	}

	public void setDateOfUpload(Date dateOfUpload) {
		this.dateOfUpload = dateOfUpload;
	}

	public String getImageLocation() {
		return imageLocation;
	}

	public void setImageLocation(String imageLocation) {
		this.imageLocation = imageLocation;
	}

	public Long getIdPassenger() {
		return idPassenger;
	}

	public void setIdPassenger(Long idPassenger) {
		this.idPassenger = idPassenger;
	}

	public Long getId() {
		return id;
	}

	public DocumentDTO() {
		super();
	}

	public DocumentDTO(Date dateOfUpload, String imageLocation, Long idPassenger) {
		super();
		this.dateOfUpload = dateOfUpload;
		this.imageLocation = imageLocation;
		this.idPassenger = idPassenger;
	}

}

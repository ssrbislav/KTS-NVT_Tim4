package com.sbvtransport.sbvtransport.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Document {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, updatable = false)
	private Long id;

	@Column(name = "date_of_upload", nullable = false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date date_of_upload;

	@Column(name = "image_location", nullable = false)
	private String image_location;

	@OneToOne(fetch = FetchType.LAZY)
	private Passenger passenger;

	public Document() {
		super();
	}

	public Document(Date dateOfUpload, String imageLocation, Passenger passenger) {
		super();
		this.date_of_upload = dateOfUpload;
		this.image_location = imageLocation;
		this.passenger = passenger;
	}

	@JsonIgnore
	public Passenger getPassenger() {
		return passenger;
	}

	public void setPassenger(Passenger passenger) {
		this.passenger = passenger;
	}

	public Date getDateOfUpload() {
		return date_of_upload;
	}

	public void setDateOfUpload(Date dateOfUpload) {
		this.date_of_upload = dateOfUpload;
	}

	public String getImageLocation() {
		return image_location;
	}

	public void setImageLocation(String imageLocation) {
		this.image_location = imageLocation;
	}

	public Long getId() {
		return id;
	}

}

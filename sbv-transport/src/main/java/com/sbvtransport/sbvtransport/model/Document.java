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
	
	@Column(name = "approved", nullable = false) //declined,approved, need approve
	private String approved;

	@OneToOne(fetch = FetchType.LAZY)
	private Passenger passenger;

	public Document() {
		super();
	}

	public Document(Date dateOfUpload, String imageLocation, Passenger passenger,String approved) {
		super();
		this.date_of_upload = dateOfUpload;
		this.image_location = imageLocation;
		this.passenger = passenger;
		this.approved = approved;
	}

	public Document(Long id, Date date_of_upload, String image_location, Passenger passenger,String approved) {
		super();
		this.id = id;
		this.date_of_upload = date_of_upload;
		this.image_location = image_location;
		this.passenger = passenger;
		this.approved = approved;
	}

	@JsonIgnore
	public Passenger getPassenger() {
		return passenger;
	}

	public void setPassenger(Passenger passenger) {
		this.passenger = passenger;
	}

	public Date getDate_of_upload() {
		return date_of_upload;
	}

	public void setDate_of_upload(Date date_of_upload) {
		this.date_of_upload = date_of_upload;
	}

	public String getImage_location() {
		return image_location;
	}

	public void setImage_location(String image_location) {
		this.image_location = image_location;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getApproved() {
		return approved;
	}

	public void setApproved(String approved) {
		this.approved = approved;
	}

	
	
	

}

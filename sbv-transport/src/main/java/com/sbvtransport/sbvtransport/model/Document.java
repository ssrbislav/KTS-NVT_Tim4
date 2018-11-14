package com.sbvtransport.sbvtransport.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Document {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, updatable = false)
	private Long id;
	
	@Column(name = "dateOfUploud", nullable = false)
	private Date dateOfUpload;
	
	@Column(name = "imageLocation", nullable = false)
	private String imageLocation;
	
	@OneToOne(fetch = FetchType.LAZY)
	private Passenger passenger;
	
	
	public Document() {
		super();
	}

	public Passenger getPassenger() {
		return passenger;
	}

	public void setPassenger(Passenger passenger) {
		this.passenger = passenger;
	}

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

	public Long getId() {
		return id;
	}
	
	
	
}

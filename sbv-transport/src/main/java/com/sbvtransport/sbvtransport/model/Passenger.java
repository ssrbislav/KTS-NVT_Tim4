package com.sbvtransport.sbvtransport.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


public class Passenger extends User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=IDENTITY)
	@Column(name="id", unique=true, nullable=false)
	private Long id;

	@Column(name="active", unique=false, nullable=false)
	private boolean active;
	
	@Column(name="document", unique=false, nullable=false)
	private String document;
	
	@Column(name="validate_document", unique=false, nullable=false)
	private boolean validate_document;
	
	public Passenger(){
		super();
	}

	public boolean isActive() {
		return active;
	}

	public String getDocument() {
		return document;
	}

	public boolean isValidate_document() {
		return validate_document;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public void setValidate_document(boolean validate_document) {
		this.validate_document = validate_document;
	}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Passenger [id=" + id + ", active=" + active + ", document=" + document + ", validate_document="
				+ validate_document + ", email=" + email + ", username=" + username + ", password=" + password
				+ ", first_name=" + first_name + ", last_name=" + last_name + ", address=" + address + ", phone_number="
				+ phone_number + "]";
	}

	
	
	
	




}

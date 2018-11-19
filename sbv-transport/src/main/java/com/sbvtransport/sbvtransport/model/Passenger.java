package com.sbvtransport.sbvtransport.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="passenger")
public class Passenger extends User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=IDENTITY)
	@Column(name="id", unique=true, nullable=false)
	private Long id;
	
	// DOB

	@Column(name="active", unique=false, nullable=false)
	private boolean active;
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "passenger", cascade = CascadeType.ALL)
	@JoinColumn(name = "passenger", referencedColumnName = "id")
	private Document document;
	
	@Column(name="validate_document", unique=false, nullable=false)
	private boolean document_validated;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy = "passenger", 
	        cascade = CascadeType.ALL)
	private List<Ticket> tickets = new ArrayList<Ticket>();
	
	public Passenger(){
		
	}
	
	public Passenger(boolean active, boolean document_validated,String email, String username, 
			String password, String first_name, String last_name, String address,
			String phone_number, Date date_birth) {
		super(email, username, password, first_name, last_name, address,phone_number, date_birth);
		this.active = active;
		this.document_validated = document_validated;
	}

	public boolean isActive() {
		return active;
	}

	public Document getDocument() {
		return document;
	}

	public boolean isDocument_validated() {
		return document_validated;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public void setDocument_validated(boolean document_validated) {
		this.document_validated = document_validated;
	}
	

	public Long getId() {
		return id;
	}

	
	public List<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}
	
	

	@Override
	public String toString() {
		return "Passenger [id=" + id + ", active=" + active + ", document=" + document + ", validate_document="
				+ document_validated + ", tickets=" + tickets + ", email=" + email + ", username=" + username
				+ ", password=" + password + ", first_name=" + first_name + ", last_name=" + last_name + ", address="
				+ address + ", phone_number=" + phone_number + "]";
	}


}

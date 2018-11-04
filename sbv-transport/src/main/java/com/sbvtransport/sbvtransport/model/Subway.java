package com.sbvtransport.sbvtransport.model;

import static javax.persistence.GenerationType.IDENTITY;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="subway")
public class Subway extends Transport implements Serializable {

	/**
	 * need to add current location
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=IDENTITY)
	@Column(name="id", unique=true, nullable=false)
	private Long id;
	
	@Column(name="code", unique=false, nullable=false)
	private String code;
	
	public Subway(){
		
	}

	public Subway(Long id, String code) {
		super();
		this.id = id;
		this.code = code;
	}

	public Long getId() {
		return id;
	}

	public String getCode() {
		return code;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "Subway [id=" + id + ", code=" + code + ", speed=" + speed + ", line=" + line + ", late=" + late
				+ ", name=" + name + "]";
	}
	
	
	
	

}

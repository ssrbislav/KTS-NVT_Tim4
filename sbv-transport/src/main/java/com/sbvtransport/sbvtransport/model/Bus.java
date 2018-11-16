package com.sbvtransport.sbvtransport.model;

import static javax.persistence.GenerationType.IDENTITY;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="bus")
public class Bus extends Transport implements Serializable {

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
	
	public Bus(){
		
	}

	public Bus(Long id, String code,double speed, Long line, boolean late, String name) {
		super(speed, line, late, name);
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
		return "Bus [id=" + id + ", code=" + code + ", speed=" + speed + ", line=" + line_id + ", late=" + late + ", name="
				+ name + "]";
	}
	
	

	
	
}

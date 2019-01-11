package com.sbvtransport.sbvtransport.model;

import static javax.persistence.GenerationType.IDENTITY;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "bus")
public class Bus extends Transport implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@Column(name = "code", unique = false, nullable = false)
	private String code;

	public Bus() {

	}
	
	public Bus(String code, Line line, boolean late, String name,int time,boolean deleted) {
		super(line, late, name,time,deleted);
		this.code = code;
		
	}
	
	
	public Bus(Line line, boolean late, String name, Long id, String code,int time,boolean deleted) {
		super(line, late, name,time,deleted);
		this.id = id;
		this.code = code;
	}

	public Long getId() {
		return id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	

	public void setId(Long id) {
		this.id = id;
	}


	@Override
	public String toString() {
		return "Bus [id=" + id + ", code=" + code + ", line=" + line + ", late=" + late + ", name=" + name + "]";
	}

}

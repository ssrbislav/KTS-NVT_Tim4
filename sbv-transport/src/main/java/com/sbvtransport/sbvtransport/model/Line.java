package com.sbvtransport.sbvtransport.model;

import static javax.persistence.GenerationType.IDENTITY;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="line")
public class Line implements Serializable {
	
	/**
	 * need to add timetable and stations
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=IDENTITY)
	@Column(name="id", unique=true, nullable=false)
	private Long id;
	
	public Line(){
		
	}

	public Line(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	
	
}

package com.sbvtransport.sbvtransport.model;

import static javax.persistence.GenerationType.IDENTITY;

import com.sbvtransport.sbvtransport.enumeration.TypeTransport;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
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

	@Column(name = "station_list", unique = false, nullable = false)
	private List<Station> station_list;


	@Column(name = "line_type", unique = false, nullable = false)
	private TypeTransport station_type;

	
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

	public List<Station> getStation_list() {
		return station_list;
	}

	public void setStation_list(List<Station> station_list) {
		this.station_list = station_list;
	}

	public TypeTransport getStation_type() {
		return station_type;
	}

	public void setStation_type(TypeTransport station_type) {
		this.station_type = station_type;
	}
}

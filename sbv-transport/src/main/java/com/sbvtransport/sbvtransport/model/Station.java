package com.sbvtransport.sbvtransport.model;

import static javax.persistence.GenerationType.IDENTITY;

import com.sbvtransport.sbvtransport.enumeration.TypeTransport;
import java.io.Serializable;

import java.util.Date;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
@Entity
@Table(name="station")
public class Station implements Serializable {

	/**
	 * need to add location and line
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=IDENTITY)
	@Column(name="id", unique=true, nullable=false)
	private Long id;

	// TODO #2: Solve MappingException.
//	@Column(name = "location", unique = false, nullable = false)
	@OneToOne(targetEntity = Location.class)
	private Location location;

	// TODO #3: Solve MappingException.
//	@Column(name = "timetable", unique = false, nullable = false)
	private Map<Transport, Date> timetable;
	
	public Station(){
		
	}

	public Station(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Map<Transport, Date> getTimetable() {
		return timetable;
	}

	public void setTimetable(
			Map<Transport, Date> timetable) {
		this.timetable = timetable;
	}

	@Override
	public String toString() {
		return "Station [id=" + id + "]";
	}
	
	


}

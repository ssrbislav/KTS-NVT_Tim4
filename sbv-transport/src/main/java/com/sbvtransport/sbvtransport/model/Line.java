package com.sbvtransport.sbvtransport.model;

import static javax.persistence.GenerationType.IDENTITY;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sbvtransport.sbvtransport.enumeration.TypeTransport;
import com.sbvtransport.sbvtransport.enumeration.Zone;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "line")
public class Line implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@Column(name = "name", unique = true, nullable = false)
	private String name;

	@ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
        name = "line_Project", 
        joinColumns = { @JoinColumn(name = "id") }, 
        inverseJoinColumns = { @JoinColumn(name = "station_id") }
    )
	public List<Station> station_list = new ArrayList<Station>();

	@Column(name = "line_type", unique = false, nullable = false)
	private TypeTransport line_type;
	
	@Column(name = "zone", unique = false, nullable = false)
	private Zone zone;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "timetable", referencedColumnName = "id")
	private Timetable timetable;
	
	@Column(name = "first_station", unique = false, nullable = true)
	private Long first_station;
	
	@Column(name = "deleted", unique = false, nullable = false)
	private boolean deleted;

	public Line() {

	}

	public Line(String name, TypeTransport line_type) {
		super();
		this.name = name;
		this.line_type = line_type;
	}

	public Line(String name, List<Station> station_list, TypeTransport line_type) {
		this.name = name;
		this.station_list = station_list;
		this.line_type = line_type;
	}

	public Line(String name, TypeTransport line_type, Zone zone, Long first_station) {
		super();
		this.name = name;
		this.line_type = line_type;
		this.zone = zone;
		this.first_station = first_station;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TypeTransport getLine_type() {
		return line_type;
	}

	public void setLine_type(TypeTransport line_type) {
		this.line_type = line_type;
	}

	public Long getId() {
		return id;
	}
	

	public void setId(Long id) {
		this.id = id;
	}

//	@JsonIgnore
	public List<Station> getStation_list() {
		return station_list;
	}

	public void setStation_list(List<Station> station_list) {
		this.station_list = station_list;
	}
	

	public Zone getZone() {
		return zone;
	}

	public void setZone(Zone zone) {
		this.zone = zone;
	}

	public Timetable getTimetable() {
		return timetable;
	}

	public void setTimetable(Timetable timetable) {
		this.timetable = timetable;
	}

	public Long getFirst_station() {
		return first_station;
	}

	public void setFirst_station(Long first_station) {
		this.first_station = first_station;
	}
	public void addStation(Station station){
		this.station_list.add(station);
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public String toString() {
		return "Line [id=" + id + ", name=" + name + ", station_list=" + station_list + ", line_type=" + line_type
				+ "]";
	}
	
	

}

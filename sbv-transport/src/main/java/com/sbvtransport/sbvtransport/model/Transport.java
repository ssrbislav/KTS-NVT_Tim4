package com.sbvtransport.sbvtransport.model;

import static javax.persistence.GenerationType.IDENTITY;
import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@MappedSuperclass
public class Transport implements Serializable {

	// TODO: Add current location.

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "line", referencedColumnName = "id")
	protected Line line;

	@Column(name = "late", unique = false, nullable = false)
	protected boolean late;

	@Column(name = "name", unique = false, nullable = false)
	protected String name;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "location", referencedColumnName = "id")
	protected Location location;

	@OneToOne(targetEntity = Timetable.class, cascade = CascadeType.ALL)
	protected Timetable timetable;

	public Transport() {

	}

	public Transport(Line line, boolean late, String name, Location location) {
		this.line = line;
		this.late = late;
		this.name = name;
		this.location = location;
	}

	public Transport(Line line, boolean late, String name) {
		this.line = line;
		this.late = late;
		this.name = name;
	}

	public Transport(Line line, boolean late, String name, Timetable timetable) {
		this.line = line;
		this.late = late;
		this.name = name;
		this.timetable = timetable;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Line getLine() {
		return line;
	}

	public void setLine(Line line) {
		this.line = line;
	}

	public Long getId() {
		return id;
	}

	public boolean isLate() {
		return late;
	}

	public String getName() {
		return name;
	}

	public void setLate(boolean late) {
		this.late = late;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Timetable getTimetable() {
		return timetable;
	}

	public void setTimetable(Timetable timetable) {
		this.timetable = timetable;
	}
}

package com.sbvtransport.sbvtransport.model;

import static javax.persistence.GenerationType.IDENTITY;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "station")
public class Station implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@OneToOne(targetEntity = Location.class)
	@JoinColumn(name = "location", referencedColumnName = "id")
	private Location location;

	// TODO: Change mapping.

	@OneToOne(targetEntity = Timetable.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "timetable", referencedColumnName = "id")
	private Timetable timetable;

	@ManyToOne(optional = false)
	@JoinColumn(name = "line", referencedColumnName = "id")
	private Line line;

	public Station() {

	}

	public Station(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	@JsonIgnore
	public Timetable getTimetable() {
		return timetable;
	}

	public void setTimetable(Timetable timetable) {
		this.timetable = timetable;
	}

	@Override
	public String toString() {
		return "Station [id=" + id + "]";
	}

	public Line getLine() {
		return line;
	}

	public void setLine(Line line) {
		this.line = line;
	}

}

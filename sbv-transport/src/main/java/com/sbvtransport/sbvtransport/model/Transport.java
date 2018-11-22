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


@MappedSuperclass
public class Transport implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

//	@Column(name = "speed", unique = false, nullable = false)
//	protected double speed;

	// TODO #4: Line - Transport
//	@OneToOne(fetch = FetchType.LAZY, mappedBy = "transport", cascade = CascadeType.ALL)
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "line", referencedColumnName = "id")
	protected Line line;
//	protected Long line_id;

//	@OneToOne(fetch = FetchType.LAZY, mappedBy = "transport", cascade = CascadeType.ALL)
//	@JoinColumn(name = "transport", referencedColumnName = "id")
//	@OneToOne(cascade = CascadeType.ALL, mappedBy = "transport")
//	@OneToOne(t)
//	protected Timetable timetable;

	@Column(name = "late", unique = false, nullable = false)
	protected boolean late;

	@Column(name = "name", unique = false, nullable = false)
	protected String name;

	public Transport() {

	}

//	public Transport(double speed, Long line, boolean late, String name) {
//		super();
//		this.speed = speed;
//		this.line_id = line;
//		this.late = late;
//		this.name = name;
//	}

//	public Long getLine_id() {
//		return line_id;
//	}

//	public void setLine_id(Long line_id) {
//		this.line_id = line_id;
//	}

	public Transport(Line line, boolean late, String name) {
		this.line = line;
		this.late = late;
		this.name = name;
	}

//	public Transport(Line line, Timetable timetable, boolean late, String name) {
//		this.line = line;
//		this.timetable = timetable;
//		this.late = late;
//		this.name = name;
//	}
//
//	public Timetable getTimetable() {
//		return timetable;
//	}
//
//	public void setTimetable(Timetable timetable) {
//		this.timetable = timetable;
//	}

	public Line getLine() {
		return line;
	}

	public void setLine(Line line) {
		this.line = line;
	}

	public Long getId() {
		return id;
	}


//	public Long getLine() {
//		return line_id;
//	}

	public boolean isLate() {
		return late;
	}

	public String getName() {
		return name;
	}

//	public void setLine(Long line) {
//		this.line_id = line;
//	}

	public void setLate(boolean late) {
		this.late = late;
	}

	public void setName(String name) {
		this.name = name;
	}

//	@Override
//	public String toString() {
//		return "Transport [speed=" + speed + ", line=" + line_id + ", late=" + late + ", name=" + name + "]";
//	}

}

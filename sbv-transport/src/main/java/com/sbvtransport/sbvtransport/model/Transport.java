package com.sbvtransport.sbvtransport.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Transport {
	
	@Id
	@GeneratedValue(strategy=IDENTITY)
	@Column(name="id", unique=true, nullable=false)
	private Long id;


	@Column(name = "speed", unique = false, nullable = false)
	protected double speed;

	// TODO #4: Line - Transport
	@Column(name = "line", unique = false, nullable = false)
	protected Long line_id;

	@Column(name = "late", unique = false, nullable = false)
	protected boolean late;

	@Column(name = "name", unique = false, nullable = false)
	protected String name;

	public Transport() {

	}

	public Transport(double speed, Long line, boolean late, String name) {
		super();
		this.speed = speed;
		this.line_id = line;
		this.late = late;
		this.name = name;
	}

	public double getSpeed() {
		return speed;
	}

	public Long getLine() {
		return line_id;
	}

	public boolean isLate() {
		return late;
	}

	public String getName() {
		return name;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public void setLine(Long line) {
		this.line_id = line;
	}

	public void setLate(boolean late) {
		this.late = late;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Transport [speed=" + speed + ", line=" + line_id + ", late=" + late + ", name=" + name + "]";
	}

}

package com.sbvtransport.sbvtransport.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Transport {
	
	@Column(name="speed",unique=false, nullable=false)
	protected double speed;
	
	@Column(name="line",unique=false, nullable=false)
	protected Line line;
	
	@Column(name="late",unique=false, nullable=false)
	protected boolean late;
	
	@Column(name="name",unique=false, nullable=false)
	protected String name;
	
	public Transport(){
		
	}

	public double getSpeed() {
		return speed;
	}

	public Line getLine() {
		return line;
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

	public void setLine(Line line) {
		this.line = line;
	}

	public void setLate(boolean late) {
		this.late = late;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Transport [speed=" + speed + ", line=" + line + ", late=" + late + ", name=" + name + "]";
	}
	
	

}

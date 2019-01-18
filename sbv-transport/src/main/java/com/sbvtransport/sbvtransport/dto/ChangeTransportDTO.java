package com.sbvtransport.sbvtransport.dto;

import com.sbvtransport.sbvtransport.model.Location;
import com.sbvtransport.sbvtransport.model.Timetable;

public class ChangeTransportDTO {
	
	private Long id_transport;
	private String name;
	private int time_arrive;
	private Location current_location;
	private Timetable timetable;
	
	
	public ChangeTransportDTO(){
		
	}


	public ChangeTransportDTO(Long id_transport, String name, int time_arrive, Location current_location,
			Timetable timetable) {
		super();
		this.id_transport = id_transport;
		this.name = name;
		this.time_arrive = time_arrive;
		this.current_location = current_location;
		this.timetable = timetable;
	}


	public Long getId_transport() {
		return id_transport;
	}


	public void setId_transport(Long id_transport) {
		this.id_transport = id_transport;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getTime_arrive() {
		return time_arrive;
	}


	public void setTime_arrive(int time_arrive) {
		this.time_arrive = time_arrive;
	}


	public Location getCurrent_location() {
		return current_location;
	}


	public void setCurrent_location(Location current_location) {
		this.current_location = current_location;
	}


	public Timetable getTimetable() {
		return timetable;
	}


	public void setTimetable(Timetable timetable) {
		this.timetable = timetable;
	}
	

}

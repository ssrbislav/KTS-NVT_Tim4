package com.sbvtransport.sbvtransport.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import static javax.persistence.GenerationType.IDENTITY;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "timetable")
public class Timetable {

	// TODO #8: Transport (Bus, Subway, Trolley) - Timetable mapping.

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@Column(name = "code", unique = false, nullable = false)
	private String code;

	@Column(name = "schedule", unique = false, nullable = false)
	@ElementCollection(targetClass = Schedule.class)
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, targetEntity = Schedule.class)
	private Set<Schedule> schedule;
	
	@OneToOne(fetch = FetchType.LAZY)
	private Line line;


	public Timetable() {
	}

	public Timetable(String code, Set<Schedule> schedule) {
		this.code = code;
		this.schedule = schedule;
	}

	public Long getId() {
		return id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Set<Schedule> getSchedule() {
		return schedule;
	}

	public void setSchedule(Set<Schedule> schedule) {
		this.schedule = schedule;
	}
	
	@JsonIgnore
	public Line getLine() {
		return line;
	}
	@JsonProperty
	public void setLine(Line line) {
		this.line = line;
	}
	
	
}

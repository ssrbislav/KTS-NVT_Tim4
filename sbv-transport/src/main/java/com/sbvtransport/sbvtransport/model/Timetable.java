package com.sbvtransport.sbvtransport.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.*;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

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
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = Schedule.class)
	@JoinColumn(name = "timetable")
//	@JoinTable(name = "timetable_schedules", joinColumns = @JoinColumn(name = "timetable_id"), inverseJoinColumns = @JoinColumn(name = "schedule_id"))
	private List<Schedule> schedule;
	
//	@OneToOne(fetch = FetchType.LAZY)
//	private Line line;

	@Column(name = "deleted", unique = false, nullable = false)
	private boolean deleted;


	public Timetable() {
	}

	public Timetable(String code, List<Schedule> schedule) {
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

	public List<Schedule> getSchedule() {
		return schedule;
	}

	public void setSchedule(List<Schedule> schedule) {
		if (this.schedule != null) {
			this.schedule.clear();
		} else {
			this.schedule = new ArrayList<>();
		}
		this.schedule.addAll(schedule);
	}
	
//	@JsonIgnore
//	public Line getLine() {
//		return line;
//	}
//	@JsonProperty
//	public void setLine(Line line) {
//		this.line = line;
//	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	
}

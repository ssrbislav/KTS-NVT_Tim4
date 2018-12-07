package com.sbvtransport.sbvtransport.model;

import java.util.Map;
import javax.persistence.*;
import java.util.Date;
import java.util.List;
import static javax.persistence.GenerationType.IDENTITY;

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

	@Column(name = "date", unique = false, nullable = false)
	@ElementCollection(targetClass = Date.class)
	private Map<Station, Date> schedule;

	public Timetable() {
	}

	public Timetable(String code, Map<Station, Date> schedule) {
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

	public Map<Station, Date> getSchedule() {
		return schedule;
	}

	public void setSchedule(Map<Station, Date> schedule) {
		this.schedule = schedule;
	}
}

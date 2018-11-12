package com.sbvtransport.sbvtransport.model;

import javax.persistence.*;

import java.util.Date;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "timetable")
public class Timetable {

    @Id
    @GeneratedValue(strategy=IDENTITY)
    @Column(name="id", unique=true, nullable=false)
    private Long id;

    @Column(name="code", unique=false, nullable=false)
    private String code;

    @Column(name = "date", unique = false, nullable = false)
    @ElementCollection(targetClass = Date.class)
//    @OneToMany(targetEntity = Date.class)
    private List<Date> schedule;

    public Timetable() {
    }

    public Timetable(String code, List<Date> schedule) {
        this.code = code;
        this.schedule = schedule;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Date> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<Date> schedule) {
        this.schedule = schedule;
    }
}

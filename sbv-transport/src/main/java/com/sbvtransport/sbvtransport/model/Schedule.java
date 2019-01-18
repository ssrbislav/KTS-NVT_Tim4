package com.sbvtransport.sbvtransport.model;

import static javax.persistence.GenerationType.IDENTITY;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "schedule")
public class Schedule {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "id", unique = true, nullable = false)
  private Long id;

  @JoinColumn(name = "station", referencedColumnName = "station_id", unique = false)
  @OneToOne(fetch = FetchType.LAZY)
  private Station station;

  @Column(name = "times", unique = false, nullable = false)
  @ElementCollection(targetClass = Date.class)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
  @Temporal(TemporalType.TIME)
  private Set<Date> times;

  @Column(name = "deleted", unique = false, nullable = false)
  private boolean deleted;

  public Schedule(Station station, Set<Date> times, boolean deleted) {
    this.station = station;
    this.times = times;
    this.deleted = deleted;
  }

  public Schedule(Station station, Set<Date> times) {
    this.station = station;
    this.times = times;
  }

  public Schedule(Station station) {
    this.station = station;
  }

  public Schedule(Set<Date> times) {
    this.times = times;
  }

  public Schedule() {
  }

  public Long getId() {
    return id;
  }

  public Station getStation() {
    return station;
  }

  public void setStation(Station station) {
    this.station = station;
  }

  public Set<Date> getTimes() {
    return times;
  }

  public boolean isDeleted() {
    return deleted;
  }

  public void setDeleted(boolean deleted) {
    this.deleted = deleted;
  }

  public void setTimes(Set<Date> times) {
    this.times = times;


  }
}

package com.sbvtransport.sbvtransport.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import java.util.List;

public class AltTimetableDTO {

  private String transportType; // bus/subway/trolley
  private Long id_transport;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy. HH:mm")
  private List<Date> timetable;

  public AltTimetableDTO(@JsonProperty String transportType, @JsonProperty Long id_transport, @JsonProperty @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy. HH:mm") List<Date> timetable) {
    this.transportType = transportType;
    this.id_transport = id_transport;
    this.timetable = timetable;
  }

  public AltTimetableDTO() {
  }

  public String getTransportType() {
    return transportType;
  }

  public void setTransportType(String transportType) {
    this.transportType = transportType;
  }

  public Long getId_transport() {
    return id_transport;
  }

  public void setId_transport(Long id_transport) {
    this.id_transport = id_transport;
  }

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy. HH:mm")
  public List<Date> getTimetable() {
    return timetable;
  }

  public void setTimetable(List<Date> timetable) {
    this.timetable = timetable;
  }
}

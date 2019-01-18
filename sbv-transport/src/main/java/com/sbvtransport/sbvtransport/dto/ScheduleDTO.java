package com.sbvtransport.sbvtransport.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import java.util.List;

public class ScheduleDTO {

  private Long station_id;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
  private List<Date> dates;

  public ScheduleDTO(@JsonProperty Long station_id, @JsonProperty List<Date> dates) {
    this.station_id = station_id;
    this.dates = dates;
  }

  public ScheduleDTO() {
  }

  public Long getStation_id() {
    return station_id;
  }

  public void setStation_id(Long station_id) {
    this.station_id = station_id;
  }

  public List<Date> getDates() {
    return dates;
  }

  public void setDates(List<Date> dates) {
    this.dates = dates;
  }
}


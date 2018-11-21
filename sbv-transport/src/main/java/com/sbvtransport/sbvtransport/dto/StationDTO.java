package com.sbvtransport.sbvtransport.dto;

import com.sbvtransport.sbvtransport.model.Line;
import com.sbvtransport.sbvtransport.model.Location;

public class StationDTO {

  private Long id;
  private Long location_id;
  private Long line_id;
  private Line line;
  private Location location;

  public StationDTO(Long location_id, Long line_id,
      Line line, Location location) {
    this.location_id = location_id;
    this.line_id = line_id;
    this.line = line;
    this.location = location;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getLocation_id() {
    return location_id;
  }

  public void setLocation_id(Long location_id) {
    this.location_id = location_id;
  }

  public Long getLine_id() {
    return line_id;
  }

  public void setLine_id(Long line_id) {
    this.line_id = line_id;
  }

  public Line getLine() {
    return line;
  }

  public void setLine(Line line) {
    this.line = line;
  }

  public Location getLocation() {
    return location;
  }

  public void setLocation(Location location) {
    this.location = location;
  }
}

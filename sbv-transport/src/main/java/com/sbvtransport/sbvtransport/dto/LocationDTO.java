package com.sbvtransport.sbvtransport.dto;

import com.sbvtransport.sbvtransport.model.Station;

public class LocationDTO {


  private Long id;
  private String location_name;
  private String address;
  private Float latitude;
  private Float longitude;
  private String type;
  private Station station;
  private Long station_id;

  public LocationDTO(Long id, String location_name, String address, Float latitude,
      Float longitude, String type, Station station, Long station_id) {
    this.id = id;
    this.location_name = location_name;
    this.address = address;
    this.latitude = latitude;
    this.longitude = longitude;
    this.type = type;
    this.station = station;
    this.station_id = station_id;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getLocation_name() {
    return location_name;
  }

  public void setLocation_name(String location_name) {
    this.location_name = location_name;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public Float getLatitude() {
    return latitude;
  }

  public void setLatitude(Float latitude) {
    this.latitude = latitude;
  }

  public Float getLongitude() {
    return longitude;
  }

  public void setLongitude(Float longitude) {
    this.longitude = longitude;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Station getStation() {
    return station;
  }

  public void setStation(Station station) {
    this.station = station;
  }

  public Long getStation_id() {
    return station_id;
  }

  public void setStation_id(Long station_id) {
    this.station_id = station_id;
  }
}

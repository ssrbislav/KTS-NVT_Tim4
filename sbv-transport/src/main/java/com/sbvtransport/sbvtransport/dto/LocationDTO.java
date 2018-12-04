package com.sbvtransport.sbvtransport.dto;

import com.sbvtransport.sbvtransport.model.Station;

public class LocationDTO {


  private Long id;
  private String location_name;
  private String address;
  private Float latitude;
  private Float longitude;
  private String type;

  public LocationDTO(Long id, String location_name, String address, Float latitude,
      Float longitude, String type) {
    this.id = id;
    this.location_name = location_name;
    this.address = address;
    this.latitude = latitude;
    this.longitude = longitude;
    this.type = type;
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

}

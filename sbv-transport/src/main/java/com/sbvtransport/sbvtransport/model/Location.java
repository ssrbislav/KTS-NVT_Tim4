package com.sbvtransport.sbvtransport.model;

import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "location")
public class Location {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@Column(name = "location_name", unique = false, nullable = false)
	private String location_name;

	@Column(name = "address", unique = false, nullable = false)
	private String address;

	@Column(name = "latitude", unique = false, nullable = false)
	private Float latitude;

	@Column(name = "longitude", unique = false, nullable = false)
	private Float longitude;

	@Column(name = "type", unique = false, nullable = false)
	private String type;

	@OneToOne(fetch = FetchType.LAZY)
	private Station station;

	public Location() {
	}

	public Location(String location_name, String address, Float latitude, Float longitude, String type,
			Station station) {
		this.location_name = location_name;
		this.address = address;
		this.latitude = latitude;
		this.longitude = longitude;
		this.type = type;
		this.station = station;
	}

	public Long getId() {
		return id;
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

	@Override
	public String toString() {
		return "Location{" + "id=" + id + ", location_name='" + location_name + '\'' + ", address='" + address + '\''
				+ ", latitude=" + latitude + ", longitude=" + longitude + ", type='" + type + '\'' + '}';
	}

}

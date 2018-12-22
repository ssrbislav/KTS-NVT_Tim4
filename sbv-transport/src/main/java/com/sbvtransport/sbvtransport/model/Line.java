package com.sbvtransport.sbvtransport.model;

import static javax.persistence.GenerationType.IDENTITY;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sbvtransport.sbvtransport.enumeration.TypeTransport;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "line")
public class Line implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@Column(name = "name", unique = true, nullable = false)
	private String name;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "line", cascade = CascadeType.ALL)
	private List<Station> station_list = new ArrayList<Station>();

	@Column(name = "line_type", unique = false, nullable = false)
	private TypeTransport line_type;

	public Line() {

	}

	public Line(Long id) {
		super();
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TypeTransport getLine_type() {
		return line_type;
	}

	public void setLine_type(TypeTransport line_type) {
		this.line_type = line_type;
	}

	public Long getId() {
		return id;
	}

	@JsonIgnore
	public List<Station> getStation_list() {
		return station_list;
	}

	public void setStation_list(List<Station> station_list) {
		this.station_list = station_list;
	}

}

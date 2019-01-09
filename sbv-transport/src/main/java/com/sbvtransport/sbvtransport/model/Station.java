package com.sbvtransport.sbvtransport.model;

import static javax.persistence.GenerationType.IDENTITY;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sbvtransport.sbvtransport.enumeration.Zone;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "station")
public class Station implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "station_id", unique = true, nullable = false)
	private Long id;

	@OneToOne(targetEntity = Location.class)
	@JoinColumn(name = "location", referencedColumnName = "id")
	private Location location;
	
    @ManyToMany(mappedBy = "station_list")
    private Set<Line> lines = new HashSet<>();
	
	@OneToOne(fetch = FetchType.LAZY)
	private Line line_first_station;
	
	@Column(name = "zone", unique = false, nullable = false)
	private Zone zone;

	public Station(Location location,Zone zone,Set<Line>line,Line first_station_line ) {
		this.location = location;
		this.lines = line;
		this.zone = zone;
		this.line_first_station = first_station_line;
	}

	public Station() {

	}

	public Station(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Zone getZone() {
		return zone;
	}

	public void setZone(Zone zone) {
		this.zone = zone;
	}

	@JsonIgnore
	public Set<Line> getLine() {
		return lines;
	}
	@JsonProperty
	public void setLine(Line line) {
		this.lines.add(line);
	}
	@JsonIgnore
	public Line getLine_first_station() {
		return line_first_station;
	}

	public void setLine_first_station(Line line_first_station) {
		this.line_first_station = line_first_station;
	}
	

}

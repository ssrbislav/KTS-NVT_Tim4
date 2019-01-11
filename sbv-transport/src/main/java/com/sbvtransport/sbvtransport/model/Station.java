package com.sbvtransport.sbvtransport.model;

import static javax.persistence.GenerationType.IDENTITY;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sbvtransport.sbvtransport.enumeration.Zone;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
    private List<Line> lines = new ArrayList<>();
	
	@Column(name = "zone", unique = false, nullable = false)
	private Zone zone;
	
	@Column(name = "deleted", unique = false, nullable = false)
	private boolean deleted;

	public Station(Location location,Zone zone,List<Line>line) {
		this.location = location;
		this.lines = line;
		this.zone = zone;
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
	public List<Line> getLine() {
		return lines;
	}
	@JsonProperty
	public void setLine(Line line) {
		this.lines.add(line);
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	
	

}

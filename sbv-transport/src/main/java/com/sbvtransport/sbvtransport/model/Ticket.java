package com.sbvtransport.sbvtransport.model;

import static javax.persistence.GenerationType.IDENTITY;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.sbvtransport.sbvtransport.enumeration.DemographicTicketType;
import com.sbvtransport.sbvtransport.enumeration.TicketType;
import com.sbvtransport.sbvtransport.enumeration.TypeTransport;
import com.sbvtransport.sbvtransport.enumeration.Zone;

@Entity
@Table(name="ticket")
public class Ticket implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=IDENTITY)
	@Column(name="id", unique=true, nullable=false)
	private Long id;
	
	@Column(name="type_transport", unique=false, nullable=false)
	private TypeTransport type_transport;
	
	@Column(name="cost", unique=false, nullable=false)
	private double cost;
	
	@Column(name="zone", unique=false, nullable=false)
	private Zone zone;
	
	@Column(name="date", unique=false, nullable=false)
	private Date date;

	@Column(name="ticket_type", unique=false, nullable=false)
	private TicketType ticket_type;
	
	@Column(name="active", unique=false, nullable=false)
	private boolean active;
	
	@Column(name="approved", unique=false, nullable=false)
	private boolean approved;
	
	@Column(name="expired", unique=false, nullable=false)
	private boolean expired;
	
	@Column(name="demographic_type", unique=false, nullable=false)
	private DemographicTicketType demographic_type;
	
	@Column(name="time_expired", unique=false, nullable=false)
	private boolean time_expired;
	
	@Column(name="block", unique=false, nullable=false)
	private boolean block;
	
	@Column(name="code_transport", unique=false, nullable=false)
	private String code_transport;
	
	@ManyToOne (optional = false)
	@JoinColumn(name = "passenger", referencedColumnName="id")
	private Passenger passenger;
	
	public Ticket(){
		
	}

	public Ticket(Long id, TypeTransport type_transport, double cost, Zone zone, Date date, TicketType ticket_type,
			boolean active, boolean approved, boolean expired, DemographicTicketType demographic_type,
			boolean time_expired, boolean block, String code_transport, Passenger passenger) {
		super();
		this.id = id;
		this.type_transport = type_transport;
		this.cost = cost;
		this.zone = zone;
		this.date = date;
		this.ticket_type = ticket_type;
		this.active = active;
		this.approved = approved;
		this.expired = expired;
		this.demographic_type = demographic_type;
		this.time_expired = time_expired;
		this.block = block;
		this.code_transport = code_transport;
		this.passenger = passenger;
	}

	public Long getId() {
		return id;
	}

	public TypeTransport getType_transport() {
		return type_transport;
	}

	public double getCost() {
		return cost;
	}

	public Zone getZone() {
		return zone;
	}

	public Date getDate() {
		return date;
	}

	public TicketType getTicket_type() {
		return ticket_type;
	}

	public boolean isActive() {
		return active;
	}

	public boolean isApproved() {
		return approved;
	}

	public boolean isExpired() {
		return expired;
	}

	public DemographicTicketType getDemographic_type() {
		return demographic_type;
	}

	public boolean isTime_expired() {
		return time_expired;
	}

	public boolean isBlock() {
		return block;
	}

	public String getCode_transport() {
		return code_transport;
	}

	public Passenger getPassenger() {
		return passenger;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setType_transport(TypeTransport type_transport) {
		this.type_transport = type_transport;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public void setZone(Zone zone) {
		this.zone = zone;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setTicket_type(TicketType ticket_type) {
		this.ticket_type = ticket_type;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	public void setExpired(boolean expired) {
		this.expired = expired;
	}

	public void setDemographic_type(DemographicTicketType demographic_type) {
		this.demographic_type = demographic_type;
	}

	public void setTime_expired(boolean time_expired) {
		this.time_expired = time_expired;
	}

	public void setBlock(boolean block) {
		this.block = block;
	}

	public void setCode_transport(String code_transport) {
		this.code_transport = code_transport;
	}

	public void setPassenger(Passenger passenger) {
		this.passenger = passenger;
	}

	@Override
	public String toString() {
		return "Ticket [id=" + id + ", type_transport=" + type_transport + ", cost=" + cost + ", zone=" + zone
				+ ", date=" + date + ", ticket_type=" + ticket_type + ", active=" + active + ", approved=" + approved
				+ ", expired=" + expired + ", demographic_type=" + demographic_type + ", time_expired=" + time_expired
				+ ", block=" + block + ", code_transport=" + code_transport + ", passenger=" + passenger + "]";
	}

	

	

}

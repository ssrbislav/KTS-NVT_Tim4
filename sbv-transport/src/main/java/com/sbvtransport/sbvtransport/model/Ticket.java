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
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sbvtransport.sbvtransport.enumeration.DemographicTicketType;
import com.sbvtransport.sbvtransport.enumeration.TicketType;
import com.sbvtransport.sbvtransport.enumeration.TypeTransport;
import com.sbvtransport.sbvtransport.enumeration.Zone;

@Entity
@Table(name = "ticket")
public class Ticket implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@Column(name = "type_transport", unique = false, nullable = false)
	private TypeTransport type_transport;

	@Column(name = "cost", unique = false, nullable = false)
	private double cost;

	@Column(name = "zone", unique = false, nullable = false)
	private Zone zone;

	@Column(name = "date", unique = false, nullable = false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy.")
	private Date date;

	@Column(name = "ticket_type", unique = false, nullable = false)
	private TicketType ticket_type;

	@Column(name = "active", unique = false, nullable = false)
	private boolean active;

	@Column(name = "expired", unique = false, nullable = false)
	private boolean expired;

	@Column(name = "demographic_type", unique = false, nullable = false)
	private DemographicTicketType demographic_type;

	// stavi Date vreme isteka karte(proveri da li je oneUse,monty,year)
	@Column(name = "time_expired", unique = false, nullable = false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy.")
	private Date time_expired;

	@Column(name = "block", unique = false, nullable = false)
	private boolean block;

	@Column(name = "code_transport", unique = false, nullable = false)
	private String code_transport;

	@Column(name = "date_purchase", unique = false, nullable = false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy.")
	private Date date_purchase;

	@ManyToOne(optional = false)
	@JoinColumn(name = "passenger", referencedColumnName = "id")
	private Passenger passenger;

	public Ticket() {

	}

	public Ticket(TypeTransport type_transport, double cost, Zone zone, Date date, TicketType ticket_type,
			boolean active, boolean expired, DemographicTicketType demographic_type, Date time_expired, boolean block,
			String code_transport, Date date_purchase, Passenger passenger) {
		super();
		this.type_transport = type_transport;
		this.cost = cost;
		this.zone = zone;
		this.date = date;
		this.ticket_type = ticket_type;
		this.active = active;
		this.expired = expired;
		this.demographic_type = demographic_type;
		this.time_expired = time_expired;
		this.block = block;
		this.code_transport = code_transport;
		this.date_purchase = date_purchase;
		this.passenger = passenger;
	}

	public Long getId() {
		return id;
	}

	public TypeTransport getType_transport() {
		return type_transport;
	}

	public void setType_transport(TypeTransport type_transport) {
		this.type_transport = type_transport;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public Zone getZone() {
		return zone;
	}

	public void setZone(Zone zone) {
		this.zone = zone;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public TicketType getTicket_type() {
		return ticket_type;
	}

	public void setTicket_type(TicketType ticket_type) {
		this.ticket_type = ticket_type;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isExpired() {
		return expired;
	}

	public void setExpired(boolean expired) {
		this.expired = expired;
	}

	public DemographicTicketType getDemographic_type() {
		return demographic_type;
	}

	public void setDemographic_type(DemographicTicketType demographic_type) {
		this.demographic_type = demographic_type;
	}

	public Date getTime_expired() {
		return time_expired;
	}

	public void setTime_expired(Date time_expired) {
		this.time_expired = time_expired;
	}

	public boolean isBlock() {
		return block;
	}

	public void setBlock(boolean block) {
		this.block = block;
	}

	public String getCode_transport() {
		return code_transport;
	}

	public void setCode_transport(String code_transport) {
		this.code_transport = code_transport;
	}

	public Date getDate_purchase() {
		return date_purchase;
	}

	public void setDate_purchase(Date date_purchase) {
		this.date_purchase = date_purchase;
	}

	@JsonIgnore
	public Passenger getPassenger() {
		return passenger;
	}

	public void setPassenger(Passenger passenger) {
		this.passenger = passenger;
	}

	@Override
	public String toString() {
		return "Ticket [id=" + id + ", type_transport=" + type_transport + ", cost=" + cost + ", zone=" + zone
				+ ", date=" + date + ", ticket_type=" + ticket_type + ", active=" + active + ", expired=" + expired
				+ ", demographic_type=" + demographic_type + ", time_expired=" + time_expired + ", block=" + block
				+ ", code_transport=" + code_transport + ", passenger=" + passenger + "]";
	}

}

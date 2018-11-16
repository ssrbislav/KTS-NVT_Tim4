package com.sbvtransport.sbvtransport.dto;

public class BuyTicketDTO {
	
	private Long idPassenger;
	
	private Long id;
	
	private String type_transport;
	
	private double cost;
	
	private String zone;
	
	private String date;
 
	private String ticket_type;
	
	private boolean active;
	
	private boolean approved;
	
	private boolean expired;
	
	private String demographic_type;
	
	private boolean time_expired;
	
	private boolean block;
	
	private String code_transport;
	
	
	public BuyTicketDTO(){
		
	}


	public BuyTicketDTO(Long idPassenger,Long id,String type_transport, double cost, String zone, String date, String ticket_type, boolean active,
			boolean approved, boolean expired, String demographic_type, boolean time_expired, boolean block,
			String code_transport) {
		super();
		this.idPassenger = idPassenger;
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
	}

	

	public Long getIdPassenger() {
		return idPassenger;
	}


	public void setIdPassenger(Long idPassenger) {
		this.idPassenger = idPassenger;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getType_transport() {
		return type_transport;
	}


	public void setType_transport(String type_transport) {
		this.type_transport = type_transport;
	}


	public double getCost() {
		return cost;
	}


	public void setCost(double cost) {
		this.cost = cost;
	}


	public String getZone() {
		return zone;
	}


	public void setZone(String zone) {
		this.zone = zone;
	}


	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}


	public String getTicket_type() {
		return ticket_type;
	}


	public void setTicket_type(String ticket_type) {
		this.ticket_type = ticket_type;
	}


	public boolean isActive() {
		return active;
	}


	public void setActive(boolean active) {
		this.active = active;
	}


	public boolean isApproved() {
		return approved;
	}


	public void setApproved(boolean approved) {
		this.approved = approved;
	}


	public boolean isExpired() {
		return expired;
	}


	public void setExpired(boolean expired) {
		this.expired = expired;
	}


	public String getDemographic_type() {
		return demographic_type;
	}


	public void setDemographic_type(String demographic_type) {
		this.demographic_type = demographic_type;
	}


	public boolean isTime_expired() {
		return time_expired;
	}


	public void setTime_expired(boolean time_expired) {
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


	@Override
	public String toString() {
		return "TicketDTO [type_transport=" + type_transport + ", cost=" + cost + ", zone=" + zone + ", date=" + date
				+ ", ticket_type=" + ticket_type + ", active=" + active + ", approved=" + approved + ", expired="
				+ expired + ", demographic_type=" + demographic_type + ", time_expired=" + time_expired + ", block="
				+ block + ", code_transport=" + code_transport + "]";
	}
	
	

	
	
	

}

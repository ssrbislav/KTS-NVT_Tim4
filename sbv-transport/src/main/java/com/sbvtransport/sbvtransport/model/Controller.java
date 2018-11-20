package com.sbvtransport.sbvtransport.model;

import static javax.persistence.GenerationType.IDENTITY;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "controller")
public class Controller extends User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	public Controller() {
		super();
	}

	public Controller(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Controller [id=" + id + ", email=" + email + ", username=" + username + ", password=" + password
				+ ", first_name=" + first_name + ", last_name=" + last_name + ", address=" + address + ", phone_number="
				+ phone_number + "]";
	}

}

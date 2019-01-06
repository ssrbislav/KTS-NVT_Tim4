package com.sbvtransport.sbvtransport.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sbvtransport.sbvtransport.model.User;

public class UserPrinciple implements UserDetails {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String username;

	@JsonIgnore
	private String password;

	private String email;

	private String first_name;

	private String last_name;

	private String address;

	private String phone_number;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy.")
	private Date date_birth;

	private Collection<? extends GrantedAuthority> authorities;

	public UserPrinciple(Long id, String username, String password, String email, String first_name, String last_name,
			String address, String phone_number, Date date_birth, Collection<? extends GrantedAuthority> authorities) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.first_name = first_name;
		this.last_name = last_name;
		this.address = address;
		this.phone_number = phone_number;
		this.date_birth = date_birth;
		this.authorities = authorities;
	}

	public static UserPrinciple build(User user) {
		List<GrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toList());

		return new UserPrinciple(user.getId(), user.getUsername(), user.getPassword(), user.getEmail(),
				user.getFirst_name(), user.getLast_name(), user.getAddress(), user.getPhone_number(),
				user.getDate_birth(), authorities);
	}

	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public String getFirst_name() {
		return first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public String getAddress() {
		return address;
	}

	public String getPhone_number() {
		return phone_number;
	}

	public Date getDate_birth() {
		return date_birth;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		UserPrinciple user = (UserPrinciple) o;
		return Objects.equals(id, user.id);
	}

}

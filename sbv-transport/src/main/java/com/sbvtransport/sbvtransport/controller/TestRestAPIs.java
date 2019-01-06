package com.sbvtransport.sbvtransport.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class TestRestAPIs {

	@GetMapping("/api/test/user")
	@PreAuthorize("hasRole('PASSENGER')")
	public String userAccess() {
		return ">>> User Contents!";
	}

	@GetMapping("/api/test/controller")
	@PreAuthorize("hasRole('CONTROLLER')")
	public String projectManagementAccess() {
		return ">>> Controller content";
	}

	@GetMapping("/api/test/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public String adminAccess() {
		return ">>> Admin Contents";
	}
}
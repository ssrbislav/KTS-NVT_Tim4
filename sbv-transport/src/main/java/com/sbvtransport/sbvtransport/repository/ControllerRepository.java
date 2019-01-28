package com.sbvtransport.sbvtransport.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sbvtransport.sbvtransport.model.Controller;

public interface ControllerRepository  extends JpaRepository<Controller,Long> {

	Controller findByUsername(String username);
}

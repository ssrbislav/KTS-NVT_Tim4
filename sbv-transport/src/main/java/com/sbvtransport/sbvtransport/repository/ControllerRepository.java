package com.sbvtransport.sbvtransport.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sbvtransport.sbvtransport.model.Controller;

public interface ControllerRepository  extends JpaRepository<Controller,Long> {

	Optional<Controller> findByUsername(String username);
}

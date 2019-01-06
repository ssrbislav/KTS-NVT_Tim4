package com.sbvtransport.sbvtransport.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sbvtransport.sbvtransport.enumeration.RoleName;
import com.sbvtransport.sbvtransport.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	Optional<Role> findByName(RoleName roleNMame);
}

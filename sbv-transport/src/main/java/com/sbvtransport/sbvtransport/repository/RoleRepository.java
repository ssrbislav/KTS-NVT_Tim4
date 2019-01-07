package com.sbvtransport.sbvtransport.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sbvtransport.sbvtransport.enumeration.RoleName;
import com.sbvtransport.sbvtransport.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	Role findByName(RoleName roleName);
}

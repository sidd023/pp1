package com.PP1_BackEnd.Springboot.repository;

import java.util.Optional; 

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.PP1_BackEnd.Springboot.model.ERole;
import com.PP1_BackEnd.Springboot.model.Role;

/*
 * The table is pre-filled with pre-defined roles to make it 
 * consistent for new user registration 
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName(ERole name);
	
	
}

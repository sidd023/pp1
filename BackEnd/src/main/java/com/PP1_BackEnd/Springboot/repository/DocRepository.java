package com.PP1_BackEnd.Springboot.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.PP1_BackEnd.Springboot.model.Doc;


@Repository
public interface DocRepository  extends JpaRepository<Doc,Integer>{

	
	@Query(value = "SELECT * FROM Doc WHERE  username = :username", nativeQuery = true)
	Optional<Doc> findByUsername(@Param("username") String username);
	
	
	
}
package com.PP1_BackEnd.Springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.PP1_BackEnd.Springboot.model.JobEmployer;

@Repository
public interface JobEmployerRepo extends JpaRepository < JobEmployer, Long > {
	
	@Query(value = "SELECT * FROM All_JOBS WHERE  Employer_Username = :username", nativeQuery = true)
	List <JobEmployer> getAllJobs(@Param("username") String username);
	
	@Query(value = "SELECT * FROM All_jobs WHERE Employer_Username = :username ORDER BY id DESC Limit 0,3", nativeQuery = true)
	List <JobEmployer> getTop3Jobs(@Param("username") String username);

}

package com.PP1_BackEnd.Springboot.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.PP1_BackEnd.Springboot.model.JobEmployer;

/*
 * this repository stores all the details related to
 * job employer and stores them in the table 
 * to keep it updated
 */
@Repository
public interface JobEmployerRepo extends JpaRepository < JobEmployer, Long > {

	@Query(value = "SELECT * FROM All_JOBS WHERE  Employer_Username = :username ", nativeQuery = true)
	List <JobEmployer> getAllJobs(@Param("username") String username);

	@Query(value = "SELECT * FROM All_jobs WHERE Employer_Username = :username ORDER BY id DESC Limit 0,3", nativeQuery = true)
	List <JobEmployer> getTop3Jobs(@Param("username") String username);

	@Query(value = "SELECT * FROM All_JOBS", nativeQuery = true)
	List <JobEmployer> viewAll();

	@Transactional
	@Modifying
	@Query(value = "Delete from All_jobs WHERE Employer_Username = :username", nativeQuery = true)
	void deleteEmployer(@Param("username") String username);

	@Query(value = "Select DISTINCT username from Applied_Jobs where job_id=:id ", nativeQuery = true)
	List<String> getApplicantsName(@Param("id") int id) ;



}

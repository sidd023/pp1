package com.PP1_BackEnd.Springboot.repository;

import java.util.List;  

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.PP1_BackEnd.Springboot.model.JobEmployer;

@Repository
public interface JobSeekerRepo extends JpaRepository < JobEmployer, Long > {

	@Query(value = "SELECT * FROM All_JOBS", nativeQuery = true)
	List<JobEmployer> getAllJobs();
	
	@Query(value = "SELECT * FROM All_JOBS WHERE  Employer_Username = :username", nativeQuery = true)
	List <JobEmployer> getByUsername(@Param("username") String username);
	
	@Query(value = "SELECT * FROM All_JOBS WHERE  Pincode = :pincode", nativeQuery = true)
	List <JobEmployer> getByLocationPincode(@Param("pincode") int pincode);
	
	@Query(value = "SELECT * FROM All_JOBS WHERE  Category = :category", nativeQuery = true)
	List <JobEmployer> getByCategory(@Param("category") String category);
	
	@Query(value = "SELECT * FROM All_JOBS WHERE  Job_Type = :jobType", nativeQuery = true)
	List <JobEmployer> getByJobType(@Param("jobType") String jobtype);
	
	@Query(value = "SELECT * FROM All_JOBS WHERE  Job_Type = :jobType AND Pincode= :pincode AND category= :category", nativeQuery = true)
	List <JobEmployer> findJob(@Param("jobType") String jobtype, @Param("category") String category,@Param("pincode") int pincode);
	
	  
	 
	
	  
}

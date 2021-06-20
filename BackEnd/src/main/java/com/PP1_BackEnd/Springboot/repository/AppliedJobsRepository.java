package com.PP1_BackEnd.Springboot.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.PP1_BackEnd.Springboot.model.AppliedJobs;

/*
 * repository responsible for storing the applied 
 * job details of the users 
 */
@Repository
public interface AppliedJobsRepository extends JpaRepository < AppliedJobs, Long > {

	/*
	 * insert the applied job details into the table
	 */
	@Transactional
	@Modifying
	@Query(value = "Insert into Applied_Jobs(JobId, username) VALUES(:id, :username) ", nativeQuery = true)
	void applyJob(@Param("id") int id, @Param("username") String username);

	/*
	 * get the username of the job
	 */
	@Query(value = "Select username from Applied_Jobs where id=:id ", nativeQuery = true)
	List<String> getApplicantsName(@Param("id") int id) ;

	
	@Query(value = "SELECT id FROM all_jobs where employer_username=:username ", nativeQuery = true)
	List<Integer> getEmployerJob(@Param("username") String username);
	
	@Transactional
	@Modifying
	@Query(value = "Delete from Applied_Jobs where job_id= :id ", nativeQuery = true)
	void deleteJob(@Param("id") int id);

	@Transactional
	@Modifying
	@Query(value = "Delete from Applied_Jobs where username= :username ", nativeQuery = true)
	void deleteSeeker(@Param("username")  String username);
}

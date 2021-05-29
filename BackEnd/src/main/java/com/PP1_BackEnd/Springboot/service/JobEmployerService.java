package com.PP1_BackEnd.Springboot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.PP1_BackEnd.Springboot.model.JobEmployer;
import com.PP1_BackEnd.Springboot.repository.JobEmployerRepo;


@Service
public class JobEmployerService {
	
	
	@Autowired
	private JobEmployerRepo employerRepo ;

	public JobEmployer saveJob(JobEmployer job) {
		return employerRepo.save(job);
	}
	
	public List<JobEmployer> getAllJobs(String username)
	{
		return employerRepo.getAllJobs(username);
	}
	
	
	public List<JobEmployer> getTop3Jobs(String username)
	{
		return employerRepo.getTop3Jobs(username);
	}
	
	public List<JobEmployer> viewAll()
	{
		return employerRepo.viewAll();
	}
	
	public List<String> getApplicantsUsername(int id)
	{
		return employerRepo.getApplicantsName(id);
	}
	
	public void deleteEmployer(String username)
	{
		employerRepo.deleteEmployer(username);
	}
	

}

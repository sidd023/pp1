package com.PP1_BackEnd.Springboot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.PP1_BackEnd.Springboot.model.JobEmployer;
import com.PP1_BackEnd.Springboot.repository.JobSeekerRepo;

@Service
public class JobSeekerService {

	@Autowired
	private JobSeekerRepo seekerRepo ;

//	public JobEmployer saveBooking(JobSeeker job) {
//		return seekerRepo.save(job);
//	}
	
	public List<JobEmployer> getAllJobs()
	{
		return seekerRepo.getAllJobs();
	}
	
	public List<JobEmployer> getByCategory(String category)
	{
		return seekerRepo.getByCategory(category);
	}
	
	public List<JobEmployer> getByUsername(String username)
	{
		return seekerRepo.getByUsername(username);
	}
	
	
	public List<JobEmployer> getByLocation(int pincode)
	{
		return seekerRepo.getByLocationPincode(pincode);
	}
	
	
	public List<JobEmployer> getByJobType(String jobType)
	{
		return seekerRepo.getByJobType(jobType);
	}
	
	
	
	
	public List<JobEmployer> findJobs(String jobtype, String category, int pincode )
	{
		return seekerRepo.findJob(jobtype, category, pincode);
	}
	
	
	
}

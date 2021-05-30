package com.PP1_BackEnd.Springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.PP1_BackEnd.Springboot.repository.AppliedJobsRepository;

/*
 * service responsible and used by the controllers
 * to communicate with the repositories
 */
@Service
public class AppliedJobService {

	@Autowired 
	AppliedJobsRepository appliedJob;

	public void applyJob(int id, String username)
	{
		appliedJob.applyJob(id, username);
	}


}

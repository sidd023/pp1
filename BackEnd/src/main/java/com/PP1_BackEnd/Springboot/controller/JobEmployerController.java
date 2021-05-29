package com.PP1_BackEnd.Springboot.controller;

import java.util.List; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.PP1_BackEnd.Springboot.model.JobEmployer;
import com.PP1_BackEnd.Springboot.payload.request.JobEmployerRequest;
import com.PP1_BackEnd.Springboot.service.JobEmployerService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/job/employer")
public class JobEmployerController {

	@Autowired
	public JobEmployerService JobEmployerService;

	@PostMapping("/alljobs")
	public List<JobEmployer> getAllJobs(@RequestBody JobEmployerRequest info){
		return JobEmployerService.getAllJobs(info.getUsername());
	}

	@PostMapping("/top3")
	public List<JobEmployer> getTop3Jobs(@RequestBody JobEmployerRequest info){
		return JobEmployerService.getTop3Jobs(info.getUsername());
	}



}
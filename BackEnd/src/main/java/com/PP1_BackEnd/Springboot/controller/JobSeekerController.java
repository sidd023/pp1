package com.PP1_BackEnd.Springboot.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.PP1_BackEnd.Springboot.model.JobEmployer;
import com.PP1_BackEnd.Springboot.model.Profile;
import com.PP1_BackEnd.Springboot.model.User;
import com.PP1_BackEnd.Springboot.payload.request.JobEmployerRequest;
import com.PP1_BackEnd.Springboot.payload.request.JobSeekerRequest;
import com.PP1_BackEnd.Springboot.repository.UserRepository;
import com.PP1_BackEnd.Springboot.service.JobEmployerService;
import com.PP1_BackEnd.Springboot.service.JobSeekerService;
import com.PP1_BackEnd.Springboot.service.ProfileService;
import com.PP1_BackEnd.Springboot.service.UserService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/job/seeker")
public class JobSeekerController {


	@Autowired
	public JobSeekerService SeekerService;
	@Autowired
	UserRepository userRepository;

	@Autowired
	JobEmployerService employerService;

	@Autowired
	ProfileService profileService;

	@Autowired
	UserService userService;

	@GetMapping("/getjob")
	public List<JobEmployer> getJob() {
		return SeekerService.getAllJobs();     
	}

	@PostMapping("/category")
	public List<JobEmployer> getByCategory(@RequestBody JobSeekerRequest info){
		return SeekerService.getByCategory(info.getCategory());
	}

	@PostMapping("/username")
	public List<JobEmployer> getByUsername(@RequestBody JobSeekerRequest info){
		return SeekerService.getByUsername(info.getUsername());
	}

	@PostMapping("/location")
	public List<JobEmployer> getByLocation(@RequestBody JobSeekerRequest info){
		return SeekerService.getByLocation(info.getLocationPincode());
	}

	@PostMapping("/job_type")
	public List<JobEmployer> getByJobType(@RequestBody JobSeekerRequest info){
		return SeekerService.getByJobType(info.getJobType());
	}


	@PostMapping("/findall")
	public List<JobEmployer> getByAllSearch(@RequestBody JobSeekerRequest info){
		// "null"

		//List<String> jobType=;
		
		List<JobEmployer> jobList = new ArrayList<JobEmployer>();
		String info_jobtype = info.getJobType();
		String info_category = info.getCategory();
		int info_pincode = info.getLocationPincode();
		if(info_jobtype == null)
		{
			info_jobtype="Full Time";
		}
		if(info_category == null)
		{
			info_category = "Engineering";
		}
		if(info_pincode == 0)
		{
			info_pincode=3000;
		}
		
		
		String[] jobType= {"Full Time","Part Time","Casual"};
		String[] category= {"Engineering","Medical","Art","Information Technology"};
		
		List<String> selected_jobtype = new ArrayList<String>(); 
		selected_jobtype.add(info_jobtype);
		
		List<String> selected_category = new ArrayList<String>(); 
		selected_category.add(info_category);
		
		
		for(int i=0; i<jobType.length; i++)
		{
			if(!info.getJobType().equalsIgnoreCase(jobType[i]))
			{
				selected_jobtype.add(jobType[i]);
			}
		}
		
		for(int i=0; i<category.length; i++)
		{
			if(!info.getCategory().equalsIgnoreCase(category[i]))
			{
				selected_category.add(category[i]);
			}
		}
		
		int count = 0;
		int dec_pincode = 1;
		int inc_pincode =999;
		for(int i=0; i<selected_jobtype.size(); i++)
		{
			for(int j=0; j<selected_category.size(); j++)
			{
				if(!(SeekerService.findJobs(selected_jobtype.get(i), selected_category.get(j), info_pincode)==null))
				{
					jobList.addAll(SeekerService.findJobs(selected_jobtype.get(i), selected_category.get(j), info_pincode));
				}
				while(count<10)
				{
					if(dec_pincode>=1 && dec_pincode<=5 && inc_pincode==999)
					{
						if(!(SeekerService.findJobs(selected_jobtype.get(i), selected_category.get(j), info_pincode-dec_pincode)==null))
						{
							jobList.addAll(SeekerService.findJobs(selected_jobtype.get(i), selected_category.get(j), info_pincode-dec_pincode));
						}
						dec_pincode+=1;
						if(dec_pincode ==6)
						{
							inc_pincode=5;
						}
					}
					if(inc_pincode != 999){
						if(!(SeekerService.findJobs(selected_jobtype.get(i), selected_category.get(j), info_pincode+inc_pincode)==null))
						{
							jobList.addAll(SeekerService.findJobs(selected_jobtype.get(i), selected_category.get(j), info_pincode+inc_pincode));
						}
						inc_pincode--;
						
					}
					count++;
				}
			}
		}
		
		
		

		return jobList;
	}

	@PostMapping("/viewAll")
	public List<JobEmployer> viewAllJob()
	{
		return SeekerService.viewAll();
	}
	
	@PostMapping("/viewAllProfile")
	public List<Profile> viewAllProfile()
	{
		return profileService.getAllProfile();
	}
	
	
	@GetMapping("/viewAllSeeker")
	public List<User> viewAllBySeeker()
	{
		return userService.getAllBySeeker();
	}
	

	// passing username only
	@PostMapping("/deleteSeeker")
	public Boolean deleteAdmin(@Valid@RequestBody JobSeekerRequest info) {
		if (userRepository.existsByUsername(info.getUsername()) && userService.getUserType(info.getUsername()).equals("JOB_SEEKER")) {
			String username = info.getUsername();
			profileService.deleteProfile(username);
			employerService.deleteEmployer(username);
			userService.deleteUser(username);
			return true;
		}
		return false;
	}

	// job id and username
	@PostMapping("/applyJob")
	public Boolean applyJob(@RequestBody JobSeekerRequest info){
		
		List<Integer> ids = SeekerService.getAppliedJob(info.getUsername());
		
		for(Integer i: ids)
		{
			if(i==info.getId())
			{
				return false;
			}
		}

		SeekerService.applyJob(info.getId(), info.getUsername());
		return true;
	}

	// username
	@PostMapping("/getAppliedJobs")
	public List<JobEmployer> getJobsApplied(@RequestBody JobSeekerRequest info)
	{
		List<Integer> ids = SeekerService.getAppliedJob(info.getUsername());
		List<JobEmployer> jobs= new ArrayList<JobEmployer>();
		for(Integer i: ids)
		{
			jobs.add(SeekerService.getJobsFromID(i));
		}
		return jobs;
	}





}


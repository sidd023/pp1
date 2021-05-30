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
import com.PP1_BackEnd.Springboot.repository.UserRepository;
import com.PP1_BackEnd.Springboot.service.JobEmployerService;
import com.PP1_BackEnd.Springboot.service.ProfileService;
import com.PP1_BackEnd.Springboot.service.UserService;

@CrossOrigin(origins = "https://match-making-pp1.herokuapp.com")
@RestController
@RequestMapping("/job/employer")
public class JobEmployerController {

	@Autowired
	public JobEmployerService JobEmployerService;

	@Autowired
	UserRepository userRepository;

	@Autowired
	JobEmployerService employerService;

	@Autowired
	ProfileService profileService;

	@Autowired
	UserService userService;

	// view all jobs posted by an employer
	@PostMapping("/alljobs")
	public List<JobEmployer> getAllJobs(@RequestBody JobEmployerRequest info){
		return JobEmployerService.getAllJobs(info.getUsername());
	}

	// view top 3 jobs posted by an employer
	@PostMapping("/top3")
	public List<JobEmployer> getTop3Jobs(@RequestBody JobEmployerRequest info){
		return JobEmployerService.getTop3Jobs(info.getUsername());
	}

	//add a new job into the database
	@PostMapping("/postjob")
	public  void saveJob(@RequestBody JobEmployer info){
		JobEmployerService.saveJob(info);
	}

	// view all jobs posted by all employers
	@GetMapping("/viewAllJobs")
	public List<JobEmployer> viewAllJob()
	{
		return JobEmployerService.viewAll();
	}

	// get the list of employers in the database
	@GetMapping("/viewAllEmployers")
	public List<User> viewAllByEmployers()
	{
		return userService.getAllByEmployer();
	}

	// delete an employer
	@PostMapping("/deleteEmployer")
	public Boolean deleteAdmin(@Valid@RequestBody JobEmployerRequest info) {
		if (userRepository.existsByUsername(info.getUsername()) && userService.getUserType(info.getUsername()).equals("EMPLOYER")) {
			String username = info.getUsername();
			profileService.deleteProfile(username);
			employerService.deleteEmployer(username);
			userService.deleteUser(username);
			return true;
		}
		return false;
	}

	// view applicant list for an job 
	@PostMapping("/getApplicants")
	public List<Profile> getApplicantsList(@RequestBody JobEmployerRequest info)
	{
		List<String> username = employerService.getApplicantsUsername(info.getId());
		List<Profile> profileList = new ArrayList<>();
		for(int i=0; i<username.size(); i++){
			profileList.add(profileService.getByUsername(username.get(i)));
		}
		return profileList;
	}

}
package com.PP1_BackEnd.Springboot.controller;

import java.util.ArrayList; 
import java.util.List;
import java.util.stream.Collectors;

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
import com.PP1_BackEnd.Springboot.payload.request.JobSeekerRequest;
import com.PP1_BackEnd.Springboot.repository.UserRepository;
import com.PP1_BackEnd.Springboot.service.JobEmployerService;
import com.PP1_BackEnd.Springboot.service.JobSeekerService;
import com.PP1_BackEnd.Springboot.service.ProfileService;
import com.PP1_BackEnd.Springboot.service.UserService;

@CrossOrigin(origins = "https://match-making-pp1.herokuapp.com")
@RestController
@RequestMapping("/job/seeker")
/*
 * this controller executes all the operation done by
 * the user seeker on the application
 */
public class JobSeekerController {

	@Autowired
	JobSeekerService SeekerService;

	@Autowired
	UserRepository userRepository;

	@Autowired
	JobEmployerService employerService;

	@Autowired
	ProfileService profileService;

	@Autowired
	UserService userService;

	private List < JobEmployer > jobList = new ArrayList < JobEmployer > ();

	/*
	 * returns the list of all jobs stored in the database 
	 */
	@GetMapping("/getjob")
	public List < JobEmployer > getJob() {
		return SeekerService.getAllJobs();
	}

	/*
	 * controller takes in the seeker username and returns job applied 
	 * by the seeker
	 */
	@PostMapping("/username")
	public List < JobEmployer > getByUsername(@RequestBody JobSeekerRequest info) {
		return SeekerService.getByUsername(info.getUsername());
	}


	/*
	 * the algorithm is implemented:
	 * The search parameters such as Category, JobType and Pincode 
	 * are taken as input to search for a relevant job. 
	 * For any missing parameters, the details are filled in using 
	 * the profile details of the seeker
	 * For any missing data, the default values are set accordingly
	 * and the relevant matched are retrieved accordingly
	 */
	@PostMapping("/findall")
	public List < JobEmployer > getByAllSearch(@RequestBody JobSeekerRequest info) {

		List < JobEmployer > allList = SeekerService.getAllJobs();
		jobList = new ArrayList < JobEmployer > ();

		String info_jobtype = info.getJobType();
		String info_category = info.getCategory();
		int info_pincode = info.getLocationPincode();
		String username = info.getUsername();

		/*
		 * the input values are checked for null
		 * and for each null value, the values are fetched from 
		 * user's profile 
		 * If the users profile is not set up, the values are set 
		 * to default as shown below
		 */
		if (info_jobtype == null) {
			info_jobtype = "Full Time";
		}
		if (info_category == null) {
			info_category = profileService.getByCategory(username);
			if (info_category == null)
				info_category = "Engineering";
		}
		if (info_pincode == 0) {
			info_pincode = profileService.getByPincode(username);
			if (info_pincode == 0)
				info_pincode = 3000;
		}

		/*
		 * list of all job types and category stored in a list
		 */
		String[] jobType = {
				"Full Time",
				"Part Time",
				"Casual"
		};
		String[] category = {
				"Engineering",
				"Medical",
				"Art",
				"Information Technology",
				"Education"
		};

		List < String > selected_jobtype = new ArrayList < String > ();
		selected_jobtype.add(info_jobtype);

		List < String > selected_category = new ArrayList < String > ();
		selected_category.add(info_category);

		/*
		 * adds only those items into the selected list 
		 * onther than the existing item on top of the list
		 */
		for (int i = 0; i < jobType.length; i++) {
			if (!info_jobtype.equalsIgnoreCase(jobType[i])) {
				selected_jobtype.add(jobType[i]);
			}
		}

		for (int i = 0; i < category.length; i++) {
			if (!info_category.equalsIgnoreCase(category[i])) {
				selected_category.add(category[i]);
			}
		}

		int dec_pincode = 1;
		int inc_pincode = 1;
		boolean check1 = true;
		/*
		 * the algorithm retrieves the job list for every possible combination possible 
		 * starting with the job search preferences provided by the user
		 * 
		 * The Pincodes are checked for +- 20 from the start of the entry made by the user
		 * These jobs are stored in as a list and returned to the view
		 */
		for (int j = 0; j < selected_category.size(); j++) {
			for (int i = 0; i < selected_jobtype.size(); i++) {
				findMatch(selected_jobtype.get(i), selected_category.get(j), info_pincode, allList);
				dec_pincode = 1;
				inc_pincode = 1;
				for (int x = 0; x < 50; x++) {
					if (check1 == true) {
						findMatch(selected_jobtype.get(i), selected_category.get(j), info_pincode - dec_pincode, allList);
						dec_pincode += 1;
						check1 = false;
					}
					if (check1 == false) {
						findMatch(selected_jobtype.get(i), selected_category.get(j), info_pincode + inc_pincode, allList);
						inc_pincode += 1;
						check1 = true;
					}
				}
			}
		}
		/*
		 * remove any duplicate job from the list
		 */
		return jobList.stream()
                .distinct()
                .collect(Collectors.toList());
	}

	/* 
	 * function used within the getByAllSearch() for find the 
	 * correct match of a job type
	 */
	public void findMatch(String jobType, String category, int location, List < JobEmployer > allList) {
		List < JobEmployer > foundJob = new ArrayList < JobEmployer > ();
		for (int i = 0; i < allList.size(); i++) {
			if (allList.get(i).getCategory().equalsIgnoreCase(category) && allList.get(i).getJobType().equalsIgnoreCase(jobType) &&
					allList.get(i).getlocationPincode() == location) {
				foundJob.add(allList.get(i));
			}
		}
		/*
		 * the values are added into the jobList which is a 
		 * private variable of this class
		 * 
		 * The jobList is returned by the function getByAllSearch()
		 */
		if (foundJob != null)
			jobList.addAll(foundJob);

	}

	/*
	 * returns list of all jobs available in the database
	 */
	@PostMapping("/viewAll")
	public List < JobEmployer > viewAllJob() {
		return SeekerService.viewAll();
	}

	/*
	 * returns the profile details of all the 
	 * job seeker stored in the database
	 */
	@GetMapping("/viewAllProfile")
	public List < Profile > viewAllProfile() {
		return profileService.getAllProfile();
	}

	/*
	 * returns the list of all job seeker 
	 * registered with the application 
	 */
	@GetMapping("/viewAllSeeker")
	public List < User > viewAllBySeeker() {
		return userService.getAllBySeeker();
	}

	/*
	 * controller deletes the seeker and all the relevant 
	 * details of the seeker from the database
	 */
	@PostMapping("/deleteSeeker")
	public Boolean deleteAdmin(@Valid @RequestBody JobSeekerRequest info) {
		if (userRepository.existsByUsername(info.getUsername()) && userService.getUserType(info.getUsername()).equals("JOB_SEEKER")) {
			String username = info.getUsername();
			profileService.deleteProfile(username);
			employerService.deleteEmployer(username);
			userService.deleteUser(username);
			return true;
		}
		return false;
	}

	/*
	 * controller takes in the job id and username and stores them 
	 * in the applied_jobs table
	 */
	@PostMapping("/applyJob")
	public Boolean applyJob(@RequestBody JobSeekerRequest info) {
		/*
		 * check the list of all jobs by the user
		 * 
		 * if there exists a job applied by the user, 
		 * the job is not applied for the user
		 */
		List < Integer > ids = SeekerService.getAppliedJob(info.getUsername());
		for (Integer i: ids) {
			if (i == info.getId()) {
				return false;
			}
		}
		/*
		 * apply job only if the job has not been already applied by the user
		 */
		SeekerService.applyJob(info.getId(), info.getUsername());
		return true;
	}

	/*
	 * controller takes the username of seeker and 
	 * returns the list of all jobs applied by the user seeker
	 */
	@PostMapping("/getAppliedJobs")
	public List < JobEmployer > getJobsApplied(@RequestBody JobSeekerRequest info) {
		List < Integer > ids = SeekerService.getAppliedJob(info.getUsername());
		List < JobEmployer > jobs = new ArrayList < JobEmployer > ();
		for (Integer i: ids) {
			jobs.add(SeekerService.getJobsFromID(i));
		}
		return jobs;
	}

}

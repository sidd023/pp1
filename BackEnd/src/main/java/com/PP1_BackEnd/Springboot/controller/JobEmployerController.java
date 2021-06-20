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
import com.PP1_BackEnd.Springboot.service.AppliedJobService;
import com.PP1_BackEnd.Springboot.service.JobEmployerService;
import com.PP1_BackEnd.Springboot.service.ProfileService;
import com.PP1_BackEnd.Springboot.service.UserService;

/*
 * this controller is responsible for executing all the requests 
 * made by an employer over the application
 */
@CrossOrigin(origins = "https://match-making-pp1.herokuapp.com")
@RestController
@RequestMapping("/job/employer")
public class JobEmployerController {

  @Autowired
  public JobEmployerService JobEmployerService;

  @Autowired
  UserRepository userRepository;
  
  @Autowired
  AppliedJobService appliedjob;

  @Autowired
  JobEmployerService employerService;

  @Autowired
  ProfileService profileService;

  @Autowired
  UserService userService;

  /*
   *  controller takes in the employer user name as parameter and 
   *  returns a list of all jobs created by an employer
   *  stored in the database
   */
  @PostMapping("/alljobs")
  public List < JobEmployer > getAllJobs(@RequestBody JobEmployerRequest info) {
    return JobEmployerService.getAllJobs(info.getUsername());
  }

  /*
   *  controller takes in the employer user name as parameter and 
   *  returns top 3 list of all jobs created by an employer
   *  stored in the database
   */
  @PostMapping("/top3")
  public List < JobEmployer > getTop3Jobs(@RequestBody JobEmployerRequest info) {
    return JobEmployerService.getTop3Jobs(info.getUsername());
  }

  /*
   *  controller takes in the job details of a job posted
   *  by an employer and saves it in the database
   */
  @PostMapping("/postjob")
  public void saveJob(@RequestBody JobEmployer info) {
    JobEmployerService.saveJob(info);
  }

  /*
   *  returns all jobs posted by all employers that
   *  is stored in the database
   */
  @GetMapping("/viewAllJobs")
  public List < JobEmployer > viewAllJob() {
    return JobEmployerService.viewAll();
  }

  /*
   * returns the list of all employers 
   * stored in the database
   */
  @GetMapping("/viewAllEmployers")
  public List < User > viewAllByEmployers() {
    return userService.getAllByEmployer();
  }

  /*
   *  controller takes in employer user name as a parameter and deletes 
   *  all the records linked/posted by the employer
   */
  @PostMapping("/deleteEmployer")
  public Boolean deleteAdmin(@Valid @RequestBody JobEmployerRequest info) {
    if (userRepository.existsByUsername(info.getUsername()) && userService.getUserType(info.getUsername()).equals("EMPLOYER")) {
      String username = info.getUsername();
      List<Integer> jobID = appliedjob.getEmployerJob(username);
      for(int i=0;i<jobID.size(); i++)
      {
    	  appliedjob.deleteJob(jobID.get(i));
      }
      profileService.deleteProfile(username);
      employerService.deleteEmployer(username);
      userService.deleteUser(username);
      return true;
    }
    return false;
  }

  /*
   * for a job id passed in as a parameter, this controller
   * returns the list of all applicants profile details  
   */
  @PostMapping("/getApplicants")
  public List < Profile > getApplicantsList(@RequestBody JobEmployerRequest info) {
    List < String > username = employerService.getApplicantsUsername(info.getId());
    List < Profile > profileList = new ArrayList < > ();
    for (int i = 0; i < username.size(); i++) {
      profileList.add(profileService.getByUsername(username.get(i)));
    }
    return profileList;
  }

  /*
   *  for each username passed in as parameter, the controller returns
   *  the user details (firstname, lastname..) of a seeker 
   */
  @PostMapping("/getApplicantUser")
  public List < User > getApplicantsUser(@RequestBody JobEmployerRequest info) {
    List < String > username = employerService.getApplicantsUsername(info.getId());
    List < User > userList = new ArrayList < > ();
    for (int i = 0; i < username.size(); i++) {
      userList.add(userService.getAllUserByUsername(username.get(i)));
    }
    return userList;
  }

}
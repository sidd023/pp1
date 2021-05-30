package com.PP1_BackEnd.Springboot.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.PP1_BackEnd.Springboot.model.Profile;
import com.PP1_BackEnd.Springboot.service.ProfileService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/seeker/profile")
public class ProfileController {

	@Autowired
	private ProfileService profileService;


	@PostMapping("/saveProfile")
	public void saveProfile(@RequestBody Profile info){
		Profile profile = profileService.getByUsername(info.getUsername());
		//Objects.isNull(profile);
		if(Objects.nonNull(profile))
		{
			profileService.updateProfile(info.getSummary(), info.getUniversity(), info.getDegree_type(), 
					info.getDate_of_graduation(), info.getLocationPincode(), info.getCategory(), info.getUsername());
		}
		else {
			profileService.saveProfile(info);
			
		}
		
	}
	
	
	@PostMapping("/updateProfile")
	public void updateProfile(@RequestBody Profile info)
	{
		profileService.updateProfile(info.getSummary(), info.getUniversity(), info.getDegree_type(), 
				info.getDate_of_graduation(), info.getLocationPincode(), info.getCategory(), info.getUsername());
	}
	
	@PostMapping("/getProfile")
	public Profile getProfile(@RequestBody Profile info)
	{
		return profileService.getByUsername(info.getUsername());
	}
	
	

}


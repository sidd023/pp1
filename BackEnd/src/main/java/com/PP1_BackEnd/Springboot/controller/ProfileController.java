package com.PP1_BackEnd.Springboot.controller;


import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.PP1_BackEnd.Springboot.model.Profile;
import com.PP1_BackEnd.Springboot.service.ProfileService;

/*
 * this controller is responsible to get, save and update 
 * the profile details for a user seeker 
 */
@CrossOrigin(origins = "https://match-making-pp1.herokuapp.com")
@RestController
@RequestMapping("/seeker/profile")
public class ProfileController {

	@Autowired
	private ProfileService profileService;

	/*
	 * method takes input parameter with all the details required 
	 * as profile details and stored them in the database
	 */
	@PostMapping("/saveProfile")
	public void saveProfile(@RequestBody Profile info) {
		Profile profile = profileService.getByUsername(info.getUsername());
		/*
		 * only if the profile is empty, the values are saves else updated
		 */
		if (Objects.nonNull(profile)) {
			profileService.updateProfile(info.getSummary(), info.getUniversity(), info.getDegree_type(),
					info.getDate_of_graduation(), info.getLocationPincode(), info.getCategory(), info.getUsername());
		} else {
			profileService.saveProfile(info);

		}

	}

	/*
	 * the method takes the user profiles details an input and 
	 * updates with the existing record in the database
	 */
	@PostMapping("/updateProfile")
	public void updateProfile(@RequestBody Profile info) {
		profileService.updateProfile(info.getSummary(), info.getUniversity(), info.getDegree_type(),
				info.getDate_of_graduation(), info.getLocationPincode(), info.getCategory(), info.getUsername());
	}

	/*
	 * this method takes the username and 
	 * returns the profile details of the seeker
	 */
	@PostMapping("/getProfile")
	public Profile getProfile(@RequestBody Profile info) {
		return profileService.getByUsername(info.getUsername());
	}

}
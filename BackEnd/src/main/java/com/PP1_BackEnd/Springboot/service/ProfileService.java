package com.PP1_BackEnd.Springboot.service;

import java.util.List; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.PP1_BackEnd.Springboot.model.Profile;
import com.PP1_BackEnd.Springboot.repository.ProfileRepo;

@Service
public class ProfileService {
	
	
	@Autowired
	private ProfileRepo profileRepo ;

	public void saveProfile(Profile job) {
		 profileRepo.save(job);
	}
	
	public List<Profile> getByUsername(String username)
	{
		return profileRepo.getByUsername(username);
	}
	
	
	public void updateProfile(String summary, String university, String degreeType, 
			String dateOfgraduation, String locationPincode, String category, String username)
	{
		profileRepo.updateProfile(summary, university, degreeType, dateOfgraduation, locationPincode, category, username);
	}
	
	


	

}


package com.PP1_BackEnd.Springboot.service;

import java.util.List; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.PP1_BackEnd.Springboot.model.Profile;
import com.PP1_BackEnd.Springboot.repository.ProfileRepo;

/*
 * service responsible and used by the controllers
 * to communicate with the repositories
 * passes the details from the controller to the repositories
 */
@Service
public class ProfileService {
	
	
	@Autowired
	private ProfileRepo profileRepo ;

	public void saveProfile(Profile job) {
		 profileRepo.save(job);
	}
	
	public Profile getByUsername(String username)
	{
		return profileRepo.getByUsername(username);
	}
	
	
	public void updateProfile(String summary, String university, String degreeType, 
			String dateOfgraduation, String locationPincode, String category, String username)
	{
		profileRepo.updateProfile(summary, university, degreeType, dateOfgraduation, locationPincode, category, username);
	}
	
	public void deleteProfile(String username)
	{
		profileRepo.deleteProfile(username);
	}
	
	public List<Profile> getAllProfile()
	{
		return profileRepo.getAllProfile();
	}
	
	public String getByCategory(String username)
	{
		return profileRepo.getUserByCategory(username);
	}
	
	
	public int getByPincode(String username)
	{
		return profileRepo.getUserByLocation(username);
	}
	
}


package com.PP1_BackEnd.Springboot.service;

import java.util.List; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import com.PP1_BackEnd.Springboot.model.User;
import com.PP1_BackEnd.Springboot.repository.RoleRepository;
import com.PP1_BackEnd.Springboot.repository.UserRepository;

/*
 * User repository is accesssed to make necessary changes from function call
 */
@Service
public class UserService {

  @Autowired
  private UserRepository userRepo;

  
  @Autowired
  private RoleRepository roleRepo;

  
  //get only employee list form USER table
  public String getUserType(String username) {
    return userRepo.getUserType(username);
  }
  
  public int getAdminExistanceCount()
  {
	  return userRepo.getAdminExistanceCount();
  }
  
  public int getId(String username)
  {
	  return userRepo.getId(username);
  }
  
  public List<User> getAllBySeeker()
  {
	  return userRepo.getAllUserBySeeker();
  }
  
  public List<User> getAllByEmployer()
  {
	  return userRepo.getAllUserByEmployer();
  }
  
  public List<User> getAllByAdmin()
  {
	  return userRepo.getAllUserByAdmin();
  }
  
  
  
  
  
  public void deleteUser(String username)
	{
	 
	  userRepo.offKey();
	
	  userRepo.deleteUser(username);
	  userRepo.onKey();
	}
  
  
  
  
	
}
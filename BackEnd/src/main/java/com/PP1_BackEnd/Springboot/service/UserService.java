package com.PP1_BackEnd.Springboot.service;

import java.util.List; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.PP1_BackEnd.Springboot.model.User;
import com.PP1_BackEnd.Springboot.repository.UserRepository;

/*
 * User repository is accesssed to make necessary changes from function call
 */
@Service
public class UserService {

  @Autowired
  private UserRepository userRepo;

  //get only employee list form USER table
  public String getUserType(String username) {
    return userRepo.getUserType(username);
  }

}
package com.PP1_BackEnd.Springboot.payload.request;

import java.util.List;

import javax.validation.constraints.NotBlank;

//Used in Employee Controller, passed as a paramater

public class JobEmployerRequest {
	
	private int id;

 private String username;

public String getUsername() {
	return username;
}

public void setUsername(String username) {
	this.username = username;
}

public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}
 
 

}
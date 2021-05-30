package com.PP1_BackEnd.Springboot.payload.request;

/*
 * request partamater used on seeker controller
 */
public class JobSeekerRequest {

	private int id;

	private String username;

	private int locationPincode;

	private String category;

	private String jobType;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getLocationPincode() {
		return locationPincode;
	}

	public void setLocationPincode(int locationPincode) {
		this.locationPincode = locationPincode;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


}

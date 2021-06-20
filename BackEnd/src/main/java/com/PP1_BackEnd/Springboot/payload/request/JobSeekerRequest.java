package com.PP1_BackEnd.Springboot.payload.request;

/*
 * request parameter used on seeker controller as input
 */
public class JobSeekerRequest {

	private int id;

	private String username;

	private String locationPincode;

	private String category;

	private String jobType;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getLocationPincode() {
		return locationPincode;
	}

	public void setLocationPincode(String locationPincode) {
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

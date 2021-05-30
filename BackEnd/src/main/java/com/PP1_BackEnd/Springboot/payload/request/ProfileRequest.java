package com.PP1_BackEnd.Springboot.payload.request;

/*
 * profile request bode required in
 * profile controller
 */
public class ProfileRequest {


	private String summary;


	private String university;


	private String degree_type;

	private String date_of_graduation;


	private String locationPincode;

	private String category;

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getUniversity() {
		return university;
	}

	public void setUniversity(String university) {
		this.university = university;
	}

	public String getDegree_type() {
		return degree_type;
	}

	public void setDegree_type(String degree_type) {
		this.degree_type = degree_type;
	}

	public String getDate_of_graduation() {
		return date_of_graduation;
	}

	public void setDate_of_graduation(String date_of_graduation) {
		this.date_of_graduation = date_of_graduation;
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



}

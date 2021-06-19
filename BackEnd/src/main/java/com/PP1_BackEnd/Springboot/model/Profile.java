package com.PP1_BackEnd.Springboot.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/*
 * table stores the details of profile user input 
 * given by each user 
 */
@Entity
@Table(name = "Profile")

public class Profile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "Summary")
	private String summary;

	@Column(name = "Username")
	private String username;

	@Column(name = "University")
	private String university;

	@Column(name = "Degree_Type")
	private String degree_type;

	@Column(name = "DateOfGraduation")
	private String date_of_graduation;

	@Column(name = "Pincode")
	private String locationPincode;

	@Column(name = "Category")
	private String category;

	public Profile()
	{
		super();
	}

	public Profile(long id, String summary, String university, String degree_type, String date_of_graduation,
			String locationPincode, String category) {
		super();
		this.id = id;
		this.summary = summary;
		this.university = university;
		this.degree_type = degree_type;
		this.date_of_graduation = date_of_graduation;
		this.locationPincode = locationPincode;
		this.category = category;
	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

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


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


}

package com.PP1_BackEnd.Springboot.model;

import javax.persistence.Column; 
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

/*
 * table used to store data of the jobs applied by a user
 * the column ID is the auto generated id number for each record
 * the column JobID stores the job id of each job
 * The column username stores the username who applied job 
 */
@Entity
@Table(name = "Applied_Jobs")

public class AppliedJobs {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer autoID;

	@Column(name = "JobId")
	private int id;

	@Column(name = "username")
	private String username;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}



}

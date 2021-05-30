package com.PP1_BackEnd.Springboot.payload.request;

import org.springframework.web.multipart.MultipartFile;

/*
 * request parameter that is used by the doc controller 
 * for input fields from the user
 */
public class DocRequest {

	private String username;

	private MultipartFile file;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public MultipartFile getFiles() {
		return file;
	}

	public void setFiles(MultipartFile files) {
		this.file = files;
	}



}

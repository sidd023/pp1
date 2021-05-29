package com.PP1_BackEnd.Springboot.payload.request;

import org.springframework.web.multipart.MultipartFile;

public class DocRequest {
	
	private String username;
	
	private MultipartFile files;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public MultipartFile getFiles() {
		return files;
	}

	public void setFiles(MultipartFile files) {
		this.files = files;
	}
	
	

}

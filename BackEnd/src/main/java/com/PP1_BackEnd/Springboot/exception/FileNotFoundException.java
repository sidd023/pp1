package com.PP1_BackEnd.Springboot.exception;


import org.springframework.http.HttpStatus; 
import org.springframework.web.bind.annotation.ResponseStatus;

/*
 *  exception to handle any file error 
 *  by the DocController in accessing file from 
 *  database
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class FileNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public FileNotFoundException(String message) {
        super(message);
    }

    public FileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
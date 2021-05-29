package com.PP1_BackEnd.Springboot.service;

import java.util.List; 
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.PP1_BackEnd.Springboot.exception.FileNotFoundException;
import com.PP1_BackEnd.Springboot.exception.FileStorageException;
import com.PP1_BackEnd.Springboot.model.Doc;
import com.PP1_BackEnd.Springboot.repository.DocRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;

@Service
public class DocStorageService {

    @Autowired
    private DocRepository dbFileRepository;

    public Doc storeFile(MultipartFile file, String username) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            Doc dbFile = new Doc(fileName, file.getContentType(), file.getBytes(), username);

            return dbFileRepository.save(dbFile);
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Doc getFile(String fileId) {
        return dbFileRepository.findById(fileId)
                .orElseThrow(() -> new FileNotFoundException("File not found with id " + fileId));
    }
}

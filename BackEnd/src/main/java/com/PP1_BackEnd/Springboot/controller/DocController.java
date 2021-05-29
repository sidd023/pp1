package com.PP1_BackEnd.Springboot.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.PP1_BackEnd.Springboot.model.Doc;
import com.PP1_BackEnd.Springboot.payload.request.DocRequest;
import com.PP1_BackEnd.Springboot.payload.response.DocResponse;
import com.PP1_BackEnd.Springboot.service.DocStorageService;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/seeker/docs")
public class DocController {
	
	
	
	@Autowired
    private DocStorageService fileStorageService;

    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<?> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Doc databaseFile = fileStorageService.getFile(fileName);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(databaseFile.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + databaseFile.getFileName() + "\"")
                .body(new ByteArrayResource(databaseFile.getData()));
    }

    @PostMapping("/uploadFile")
    public DocResponse uploadFile(@RequestParam("file") MultipartFile file,@RequestParam("username") String username) {
    	
    	
    	Doc fileName = fileStorageService.storeFile(file, username);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName.getFileName())
                .toUriString();

        return new 
        		DocResponse(fileName.getFileName(), fileDownloadUri,
                file.getContentType(), file.getSize());
    }

//    @PostMapping("/uploadMultipleFiles")
//    public List<DocResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
//        return Arrays.asList(files)
//                .stream()
//                .map(file -> uploadFile(file))
//                .collect(Collectors.toList());
//    }
    
    
    
//    
//
//	@Autowired 
//	private DocStorageService docStorageService;
//	
//	@GetMapping("/")
//	public String get(Model model) {
//		List<Doc> docs = docStorageService.getFiles();
//		model.addAttribute("docs", docs);
//		return "doc";
//	}
//	
//	@PostMapping("/uploadFiles")
//	public void uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
//		for (MultipartFile file: files) {
//			docStorageService.saveFile(file);
//			System.out.println("success");
//		}	
//	}
//	
//	
//	@GetMapping("/downloadFile/{fileId}")
//	public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable Integer fileId){
//		Doc doc = docStorageService.getFile(fileId).get();
//		return ResponseEntity.ok()
//				.contentType(MediaType.parseMediaType(doc.getDocType()))
//				.header(HttpHeaders.CONTENT_DISPOSITION,"attachment:filename=\""+doc.getDocName()+"\"")
//				.body(new ByteArrayResource(doc.getData()));
//	}
//	
}
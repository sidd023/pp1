package com.PP1_BackEnd.Springboot.controller;


import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.PP1_BackEnd.Springboot.model.Doc;
import com.PP1_BackEnd.Springboot.payload.response.DocResponse;
import com.PP1_BackEnd.Springboot.service.DocStorageService;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/seeker/docs")
public class DocController {

	@Autowired
	private DocStorageService fileStorageService;

	// download the file uploaded by the user
	// resume or cover letter uploaded by the seeker can be downloaded
	@GetMapping("/downloadFile/{fileName:.+}")
	public ResponseEntity<?> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
		// Load file as Resource
		Doc databaseFile = fileStorageService.getFile(fileName);

		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(databaseFile.getFileType()))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + databaseFile.getFileName() + "\"")
				.body(new ByteArrayResource(databaseFile.getData()));
	}

	// upload a document into the database
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

}
//package com.icic.test.app.s3bucket;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//@RestController
//public class MainController {
//
//	@GetMapping("")
//	public String viewHomePage() {
//		return "upload";
//	}
//
//	@PostMapping("/upload")
//	public String handleUploadForm(Model model, String description,
//			@RequestParam("file") MultipartFile multipart) {
//		String fileName = multipart.getOriginalFilename();
//		String a ="/usr/local/bin:/usr/bin:/bin:/usr/sbin:/sbin:/Applications/Docker.app/Contents/Resources/bin/:/Users/Kh0a/Library/Group\\ Containers/group.com.docker/Applications/Docker.app/Contents/Resources/bin";
//		System.out.println("Description: " + description);
//		System.out.println("filename: " + fileName);
//
//		String message = "";
//
//		try {
//			S3Util.uploadFile(fileName, multipart.getInputStream());
//			message = "Your file has been uploaded successfully!";
//		} catch (Exception ex) {
//			message = "Error uploading file: " + ex.getMessage();
//		}
//
//		model.addAttribute("message", message);
//
//		return "message";
//	}
//}

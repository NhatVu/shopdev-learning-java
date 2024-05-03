package com.learning.shopdevjava.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

//https://spring.io/guides/gs/uploading-files
@RestController
@RequestMapping("/fileUpload")
@AllArgsConstructor
public class FileUploadController {

//    private StorageService storageService;
    @PostMapping("/")
    public String handleFileUpload(@RequestParam("file") MultipartFile file) {

//        storageService.store(file);


        return "redirect:/";
    }
}

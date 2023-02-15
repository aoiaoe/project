package com.cz.spring_boot_mix.upload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author jzm
 * @date 2023/2/9 : 11:21
 */
@RequestMapping(value = "/upload")
@RestController
public class UploadController {

    @Autowired
    private CodeUploadService codeUploadService;

    @GetMapping(value = "/codeUpload")
    public String testCodeUpload(String filePath){
        return codeUploadService.upload(filePath);
    }

    @PostMapping
    public String upload(MultipartFile[] file){
        return codeUploadService.upload(file);
    }
}

package com.cz.spring_boot_dfs.aliyunoss.controller;

import com.cz.spring_boot_dfs.aliyunoss.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@RequestMapping(value = "/aliyunoss")
@RestController
public class FileUploadController {

    @Autowired
    private OssService ossService;

    @PostMapping
    public boolean upload(MultipartFile file){
        this.ossService.upload(file);

        return true;
    }

    /**
     * 下载文件
     */
    @GetMapping
    public void download(HttpServletResponse response) throws Exception {
        this.ossService.download(response);
    }

}

package com.cz.spring_boot_dfs.fastdfs.controller;

import com.cz.spring_boot_dfs.fastdfs.service.FastDfsClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RequestMapping(value = "/fastdfs")
@RestController
public class FastDfsController {

    @Autowired
    private FastDfsClientService fastDfsClientService;

    /**
     * 上传文件
     * @param files
     * @return
     */
    @PostMapping
    public List<String> upload(MultipartFile[] files){
        return this.fastDfsClientService.upload(files);
    }

    /**
     * 下载文件
     */
    @GetMapping
    public void download(String file, HttpServletResponse response) throws Exception {
        this.fastDfsClientService.download(file, response);
    }
}

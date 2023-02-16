package com.cz.spring_boot_mix.upload;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;

/**
 * @author jzm
 * @date 2023/2/9 : 11:10
 */
@Slf4j
@Service
public class CodeUploadService {

    @Autowired
    private RestTemplate restTemplate;

    String url = "http://localhost:18101/upload";

    public String upload(String filePath){
        try {
            MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            param.add("file", new CommonInputStreamResource(fis, file.length(), file.getName()));
            return this.post(url, param);
        }catch (Exception e){
            log.error("上传失败:", e);
        }
        return "fail";
    }

    public String post(String url, MultiValueMap<String, Object> param){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> formEntity =
                new HttpEntity<>(param, headers);
        ResponseEntity<String> exchange = this.restTemplate.exchange(url,
                HttpMethod.POST, formEntity, String.class);
        if(exchange.getStatusCode() == HttpStatus.OK){
            return exchange.getBody();
        }
        return null;
    }

    public String upload(MultipartFile[] file) {
        try {
            for (MultipartFile multipartFile : file) {
                multipartFile.transferTo(new File("/Users/sephiroth/test/" + multipartFile.getOriginalFilename()));
            }
            return "success";
        }catch (Exception e){

        }
        return "fail";
    }
}

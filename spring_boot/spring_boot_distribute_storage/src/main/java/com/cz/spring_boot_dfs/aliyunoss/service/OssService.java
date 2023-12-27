package com.cz.spring_boot_dfs.aliyunoss.service;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import com.cz.spring_boot_dfs.aliyunoss.AliyunConfig;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.UUID;

@Service
public class OssService {
    @Autowired(required = false)
    private AliyunConfig aliyunConfig;
    @Autowired(required = false)
    private OSSClient ossClient;

    public void upload(MultipartFile file){
        String fileName = file.getOriginalFilename();
        String filePath = getFilePath(fileName);
        try {
            this.ossClient.putObject(aliyunConfig.getBucketName(), filePath, new ByteArrayInputStream(file.getBytes()));
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("上传成功: 路径" + aliyunConfig.getUrlPrefix() + filePath);
    }

    public String getFilePath(String fileName){
        DateTime dateTime = new DateTime();
        return "images/" + dateTime.toString("yyyy")+"/" + dateTime.toString("MM") + "/" +
                dateTime.toString("dd") + "/" + UUID.randomUUID().toString() + "." +
                StringUtils.substringAfterLast(fileName, ".");
    }

    public void download(HttpServletResponse response) {
        // key是上传后oss中的路径, 不包含bucketName，不包含域名
        String key = "images/2023/07/05/af36fc86-8d83-42d2-aeb7-12ed96c2463c.jpg";
//        String key = "p2579081446.jpg";
        OSSObject object = this.ossClient.getObject(aliyunConfig.getBucketName(), key);
        try(InputStream is = object.getObjectContent()) {
            // 清空response
            response.reset();
            // 设置response的Header
            response.setCharacterEncoding("UTF-8");
            //Content-Disposition的作用：告知浏览器以何种方式显示响应返回的文件，用浏览器打开还是以附件的形式下载到本地保存
            //attachment表示以附件方式下载 inline表示在线打开 "Content-Disposition: inline; filename=文件名.mp3"
            // filename表示文件的默认名称，因为网络传输只支持URL编码的相关支付，因此需要将文件名URL编码后进行传输,前端收到后需要反编码才能获取到真正的名称
            response.addHeader("Content-Disposition", "inline;filename=" + URLEncoder.encode("12ed96c2463c.jpg", "UTF-8"));
            OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            byte[] bytes = new byte[1024];
            int count = 0;
            int size = 0;
            // 流式下载，大文件需要多次读流
            while ((count = is.read(bytes)) != -1) {
                outputStream.write(bytes, 0, count);
                size += count;
            }
            // 告知浏览器文件的大小
            response.addHeader("Content-Length", "" + size);
            outputStream.flush();
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                object.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}

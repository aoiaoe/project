package com.cz.spring_boot_dfs.fastdfs.service;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class FastDfsClientService {

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    public String uploadFile(byte[] bytes, long filesize, String extension) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        StorePath storePath = fastFileStorageClient.uploadFile(byteArrayInputStream, filesize, extension, null);
        log.info("[文件上传路径]:{}, {}, {}", storePath.getGroup(), storePath.getPath(), storePath.getFullPath());
        return storePath.getFullPath();
    }

    public String uploadFile(InputStream is, long filesize, String extension) {
        StorePath storePath = fastFileStorageClient.uploadFile(is, filesize, extension, null);
        log.info("[文件上传路径]:{}, {}, {}", storePath.getGroup(), storePath.getPath(), storePath.getFullPath());
        return storePath.getFullPath();
    }

    public byte[] download(String fileUrl) throws Exception {
        String group = fileUrl.substring(0, fileUrl.indexOf("/"));
        String path = fileUrl.substring(fileUrl.indexOf("/") + 1);
        return this.fastFileStorageClient.downloadFile(group, path, new DownloadByteArray());
    }

    public List<String> upload(MultipartFile[] files) {
        List<String> res = new ArrayList<>();
        try {
            for (MultipartFile file : files) {
                InputStream in = file.getInputStream();
                String originalFilename = file.getOriginalFilename();
                String ext = originalFilename.substring(originalFilename.lastIndexOf(".")+1);
                res.add(this.uploadFile(in, in.available(), ext));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public void download(String file, HttpServletResponse response) throws Exception {
        byte[] download = this.download(file);

        String fileName = file.substring(file.lastIndexOf("/") + 1);
        // 清空response
        response.reset();
        // 设置response的Header
        response.setCharacterEncoding("UTF-8");
        //Content-Disposition的作用：告知浏览器以何种方式显示响应返回的文件，用浏览器打开还是以附件的形式下载到本地保存
        //attachment表示以附件方式下载 inline表示在线打开 "Content-Disposition: inline; filename=文件名.mp3"
        // filename表示文件的默认名称，因为网络传输只支持URL编码的相关支付，因此需要将文件名URL编码后进行传输,前端收到后需要反编码才能获取到真正的名称
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        // 告知浏览器文件的大小
        response.addHeader("Content-Length", "" + download.length);
        OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
        response.setContentType("application/octet-stream");
        outputStream.write(download);
        outputStream.flush();

    }
}

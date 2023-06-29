package com.cz.tomcat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * 封装Response对象
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Response {

    private OutputStream os;

    public void output(String content) throws IOException {
        os.write(content.getBytes(StandardCharsets.UTF_8));
    }

    /**
     *
     * @param path 根据path读取静态文件
     */
    public void outPutHtml(String path) throws IOException {
        String resourcePathAbsolute = StaticResourceUtil.getAbsoluteResource(path);

        // 输出
        File file = new File(resourcePathAbsolute);
        if(!file.exists() || file.isDirectory()){
            // 输出404
            output(HttpProtocolUtil.getHttpHeader404());
            return;
        }
        // 输出静态资源
        StaticResourceUtil.outputStaticResource(new FileInputStream(file), os);

    }
}

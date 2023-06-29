package com.cz.tomcat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class StaticResourceUtil {

    public static String getAbsoluteResource(String path){
        String absolute = StaticResourceUtil.class.getResource("/").getPath();
        return absolute.replace("\\\\", "/") + path;
    }

    public static void outputStaticResource(InputStream is, OutputStream os) throws IOException {
        int count = 0;
        while (count == 0){
            count = is.available();
        }
        int resourceSize = count;
        // http请求头
        os.write(HttpProtocolUtil.getHttpHeader200(resourceSize).getBytes(StandardCharsets.UTF_8));
        int written = 0;
        int byteSize = 1024;
        byte[] bytes = new byte[byteSize];
        while (written < resourceSize) {
            if(written + byteSize > resourceSize){
                byteSize = resourceSize - written;
                bytes = new byte[byteSize];
            }
            is.read(bytes);
            os.write(bytes);
            os.flush();
            written+=byteSize;

        }
    }
}

package com.cz.spring_boot_dfs;

import io.minio.*;
import io.minio.messages.Item;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

public class MinioTest {

    private MinioClient minioClient;
    @Before
    public void setUp(){
        minioClient = MinioClient.builder()
                .endpoint("http://192.168.18.251:9000")
//                .credentials("aaaaxxxx", "aaaaxxxx") // 只读
                .credentials("aaaacccc", "aaaacccc") // 可读可写
                .build();
    }

    @Test
    public void testUpload(){
//        MinioClient minioClient = MinioClient.builder().endpoint("http://192.168.18.251:9000").credentials("aaaacccc", "aaaacccc").build();
        try(InputStream is = new FileInputStream(new File("F:\\Desktop\\算法.png"))) {
            PutObjectArgs request = PutObjectArgs.builder().bucket("test").object("/root/algo12.png").stream(is, is.available(), 0).build();
            ObjectWriteResponse response = minioClient.putObject(request);
            System.out.println(response.versionId());
            System.out.println(response.bucket());
            System.out.println(response.etag());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void testDownload(){
//        MinioClient minioClient = MinioClient.builder().endpoint("http://192.168.18.251:9000").credentials("aaaacccc", "aaaacccc").build();
        DownloadObjectArgs args = DownloadObjectArgs.builder().bucket("test").object("/root/algo.png").filename("F:\\Desktop\\algo.png").build();
        try{
            minioClient.downloadObject(args);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void testGet(){
        GetObjectArgs args = GetObjectArgs.builder().bucket("test")
                .matchETag("2272a4d539bf8463b5426e1e37c75c3f")
                .object("/root/algo.png")
                .offset(0L).length(9000L).build();
        try(OutputStream os = new FileOutputStream(new File("F:\\Desktop\\algo1.png"));
            InputStream inputStream = minioClient.getObject(args)) {
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes);
            os.write(bytes);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void testDelete(){
//        MinioClient minioClient = MinioClient.builder().endpoint("http://192.168.18.251:9000").credentials("aaaacccc", "aaaacccc").build();
        RemoveObjectArgs args = RemoveObjectArgs.builder().bucket("test").object("/root/algo.png").build();
        try {
            minioClient.removeObject(args);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void testList(){
        ListObjectsArgs args = ListObjectsArgs.builder().bucket("test").recursive(true).build();
        Iterable<Result<Item>> results = minioClient.listObjects(args);
        results.forEach(e -> {
            try {
                Item item = e.get();
                System.out.println(item.objectName());
            }catch (Exception ee){
                ee.printStackTrace();
            }
        });
    }

}

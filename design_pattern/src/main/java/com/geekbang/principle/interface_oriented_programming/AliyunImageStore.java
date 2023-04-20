package com.geekbang.principle.interface_oriented_programming;

public class AliyunImageStore implements ImageStore {
    //... 省略属性、构造函数等...
    public String upload(Image image, String bucketName) {
        createBucketIfNotExisting(bucketName);
        String accessToken = generateAccessToken();
        //... 上传图片到阿里云...
        //... 返回图片在阿里云上的地址 (url)...
        return null;
    }

    public Image download(String url) {
        String accessToken = generateAccessToken();
        //... 从阿里云下载图片...
        return null;
    }

    private void createBucketIfNotExisting(String bucketName) {
        // ... 创建 bucket...
        // ... 失败会抛出异常..
    }

    private String generateAccessToken() {
        // ... 根据 accesskey/secrectkey 等生成 access token
        return null;
    }
}
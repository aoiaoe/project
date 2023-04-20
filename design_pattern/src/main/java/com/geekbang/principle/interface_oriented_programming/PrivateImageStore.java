package com.geekbang.principle.interface_oriented_programming;

public class PrivateImageStore implements ImageStore {
    public String upload(Image image, String bucketName) {
        createBucketIfNotExisting(bucketName);
        //... 上传图片到私有云...
        //... 返回图片的 url...
        return null;
    }

    public Image download(String url) {
        //... 从私有云下载图片...
        return null;
    }

    private void createBucketIfNotExisting(String bucketName) {
// ... 创建 bucket...
// ... 失败会抛出异常..
    }
}
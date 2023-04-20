package com.geekbang.principle.interface_oriented_programming;

public interface ImageStore {
    String upload(Image image, String bucketName);

    Image download(String url);
}
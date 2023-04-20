package com.geekbang.principle.interface_oriented_programming;

/**
 * 面向接口而非实现编程
 * 可以实现更好的扩展
 *
 */
public class ImageProcessingJob {
    private static final String BUCKET_NAME = "ai_images_bucket";

    //... 省略其他无关代码...
    public void process() {
        Image image = null;//...;// 处理图片，并封装为 Image 对象
        ImageStore imageStore = new PrivateImageStore();
        imageStore.upload(image, BUCKET_NAME);
    }
}
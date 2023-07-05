package com.cz.spring_boot_dfs;

import com.cz.spring_boot_dfs.fastdfs.FastDFSUtil;
import org.csource.common.MyException;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class SpringBootDfsApplicationTests {

	private FastDFSUtil fastDFSUtil;

	public void setUp() {
		try {

			fastDFSUtil = new FastDFSUtil();
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	@Test
	public void uploadFile(){
		setUp();
		String fileName = "F:\\Desktop\\尺寸.png";
		try(FileInputStream fileInputStream = new FileInputStream(new File(fileName))) {
			byte[] bytes = new byte[fileInputStream.available()];
			fileInputStream.read(bytes);
			System.out.println("上传后路径： " + fastDFSUtil.upload(bytes, fileName));
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	@Test
	public void testDownload(){
		setUp();
		String uploadedPath = "group1/M00/00/00/wKgSZWSk2JWARCCRAAIFKcXX8bQ595.png";
		byte[] download = this.fastDFSUtil.download(uploadedPath);
		String fileName = "F:\\Desktop\\尺寸_从fastdfs下载的.png";
		try(FileOutputStream fileOutputStream = new FileOutputStream(new File(fileName))){
			fileOutputStream.write(download);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}

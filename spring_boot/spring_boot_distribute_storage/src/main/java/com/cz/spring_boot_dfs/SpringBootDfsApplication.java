package com.cz.spring_boot_dfs;

import com.cz.spring_boot_dfs.minio.MinioComp;
import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

@SpringBootApplication
public class SpringBootDfsApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(SpringBootDfsApplication.class, args);


		// 上传文件到minio中
//		MinioComp bean = context.getBean(MinioComp.class);
//		File file = new File("/Users/sephiroth/Downloads/minio_test.jpg");
//		bean.upload(file, "spring-boot-minio-bucket");

		MinioClient minioClient = context.getBean(MinioClient.class);
		try(InputStream is = new FileInputStream(new File("F:\\Desktop\\算法.png"))) {
			PutObjectArgs request = PutObjectArgs.builder().bucket("test").object("/root/算法.png").stream(is, is.available(), 0).build();
			ObjectWriteResponse response = minioClient.putObject(request);
			System.out.println(response.versionId());
			System.out.println(response.bucket());
			System.out.println(response.etag());
		}catch (Exception e){
			e.printStackTrace();
		}
	}

}

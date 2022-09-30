package com.cz.spring_boot_minio;

import com.cz.spring_boot_minio.config.MinioComp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.File;

@SpringBootApplication
public class SpringBootMinioApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(SpringBootMinioApplication.class, args);
		MinioComp bean = context.getBean(MinioComp.class);
		File file = new File("/Users/sephiroth/Downloads/minio_test.jpg");
		bean.upload(file, "spring-boot-minio-bucket");
	}

}

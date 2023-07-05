package com.cz.spring_boot_dfs;

import com.cz.spring_boot_dfs.minio.MinioComp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringBootDfsApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(SpringBootDfsApplication.class, args);


		// 上传文件到minio中
//		MinioComp bean = context.getBean(MinioComp.class);
//		File file = new File("/Users/sephiroth/Downloads/minio_test.jpg");
//		bean.upload(file, "spring-boot-minio-bucket");
	}

}

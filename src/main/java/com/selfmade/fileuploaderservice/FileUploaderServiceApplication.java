package com.selfmade.fileuploaderservice;

import com.selfmade.fileuploaderservice.storage.LocalFileSystemStorageServiceProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(LocalFileSystemStorageServiceProperties.class)
public class FileUploaderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FileUploaderServiceApplication.class, args);
	}

}

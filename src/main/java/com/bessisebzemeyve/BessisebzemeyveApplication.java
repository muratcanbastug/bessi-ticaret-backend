package com.bessisebzemeyve;

import com.bessisebzemeyve.storage.StorageProperties;
import com.bessisebzemeyve.storage.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class BessisebzemeyveApplication {

	public static void main(String[] args) {
		SpringApplication.run(BessisebzemeyveApplication.class, args);
	}
	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			// storageService.deleteAll();
			storageService.init();
		};
	}
}

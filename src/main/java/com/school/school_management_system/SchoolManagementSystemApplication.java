
package com.school.school_management_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.schoolmanagement")
@EnableJpaRepositories(basePackages = "com.schoolmanagement")
@EntityScan(basePackages = "com.schoolmanagement")
public class SchoolManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(
				SchoolManagementSystemApplication.class, args
		);
	}
}

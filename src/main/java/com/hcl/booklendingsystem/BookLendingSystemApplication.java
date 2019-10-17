package com.hcl.booklendingsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BookLendingSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookLendingSystemApplication.class, args);
	}

}

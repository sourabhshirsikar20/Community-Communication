package com.community.communication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CommunityCommunicationApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommunityCommunicationApplication.class, args);

		//Log when scheduling is enabled
		System.out.println("Scheduling enabled!");
    	System.out.println("Expiry cleanup will run every 2 minutes");
	}

}

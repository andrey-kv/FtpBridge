package com.epam.ankov.FtpBridge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FtpBridgeApplication {

	public static void main(String[] args) {
		SpringApplication.run(FtpBridgeApplication.class, args);
	}

}

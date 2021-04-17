package com.fabienIT.friendsEscalade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class EscaladeApplication {
	
	private static final Logger logger = LoggerFactory.getLogger(EscaladeApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(EscaladeApplication.class, args);
		logger.info("Start Les Amis de l'Escalade Application ...");
	}

}

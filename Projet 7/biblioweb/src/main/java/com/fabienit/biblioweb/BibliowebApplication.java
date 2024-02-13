package com.fabienit.biblioweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages={"com.fabienit.biblioweb.service","com.fabienit.biblioweb.repository","com.fabienit.biblioweb.controller","com.fabienit.biblioweb.model","com.fabienit.biblioweb.configuration"})
@ComponentScan({"com.fabienit.biblioweb.service"})
public class BibliowebApplication {

	public static void main(String[] args) {
		SpringApplication.run(BibliowebApplication.class, args);
	}


}

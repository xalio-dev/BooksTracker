package com.xaliocode.bookstracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

//	openssl genrsa -out keypair.pem 2048

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

}

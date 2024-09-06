package com.edu.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

@SpringBootApplication
public class B09aSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(B09aSecurityApplication.class, args);
		
		String passwd =
				PasswordEncoderFactories.createDelegatingPasswordEncoder()
					.encode("1234");
		System.out.println(passwd);
	}
/*
 {bcrypt}$2a$10$ru1rA3AIMWJbiUB2szqFjuaXMWGWMiGOHrn5/tW6KSHrGzwNmra/e
 {bcrypt}$2a$10$kcdFxkr1WG3Kuel.xZDIq.1hAZmppEi7uend1xBuvh.72vfifAwQC
 */
}

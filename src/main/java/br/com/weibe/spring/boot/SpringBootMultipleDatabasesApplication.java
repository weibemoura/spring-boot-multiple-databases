package br.com.weibe.spring.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SpringBootMultipleDatabasesApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootMultipleDatabasesApplication.class, args);
	}
}

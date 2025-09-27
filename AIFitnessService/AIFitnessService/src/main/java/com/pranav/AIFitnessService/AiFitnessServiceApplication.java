package com.pranav.AIFitnessService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class AiFitnessServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AiFitnessServiceApplication.class, args);
	}

}

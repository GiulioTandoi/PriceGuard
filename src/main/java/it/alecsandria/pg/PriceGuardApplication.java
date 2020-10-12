package it.alecsandria.pg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PriceGuardApplication {

	public static void main(String[] args) {
		SpringApplication.run(PriceGuardApplication.class, args);
	}

}

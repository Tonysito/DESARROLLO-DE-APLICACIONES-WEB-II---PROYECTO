package org.cibertec;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;



@SpringBootApplication
@EnableFeignClients
public class ControlStockServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ControlStockServiceApplication.class, args);
	}

}

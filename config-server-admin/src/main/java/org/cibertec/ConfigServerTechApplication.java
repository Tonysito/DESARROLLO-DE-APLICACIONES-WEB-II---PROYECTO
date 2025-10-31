package org.cibertec;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class ConfigServerTechApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigServerTechApplication.class, args);
	}

}

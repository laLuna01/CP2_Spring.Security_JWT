package com.example.CP2_Spring.Security_JWT;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info =
@Info(title = "API de Diplomas", version = "0.0.1", description = "API RESTful da diplomas usando Spring Security + JWT"))
public class Cp2SpringSecurityJwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(Cp2SpringSecurityJwtApplication.class, args);
	}

}

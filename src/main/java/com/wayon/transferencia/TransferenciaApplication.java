package com.wayon.transferencia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.wayon.transferencia.controller",
		"com.wayon.transferencia.domain.service",
		"com.wayon.transferencia.domain.repository",
		"com.wayon.transferencia.domain.exceptions",
		"com.wayon.transferencia.ExceptionHandler",
		"com.wayon.transferencia"})
public class TransferenciaApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransferenciaApplication.class, args);
	}

}

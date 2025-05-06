package com.ccarrasco.msvc.pacientes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MsvcPacientesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcPacientesApplication.class, args);
	}

}

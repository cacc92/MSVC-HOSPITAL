package com.ccarrasco.msvc.medicos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MsvcMedicosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcMedicosApplication.class, args);
	}

}

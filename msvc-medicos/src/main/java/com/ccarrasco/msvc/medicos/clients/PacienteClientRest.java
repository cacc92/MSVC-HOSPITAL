package com.ccarrasco.msvc.medicos.clients;

import com.ccarrasco.msvc.medicos.models.Paciente;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-pacientes", url = "localhost:8080/api/v1/pacientes")
public interface PacienteClientRest {

    @GetMapping("/{id}")
    Paciente findById(@PathVariable Long id);

}

package com.ccarrasco.msvc.atenciones.clients;

import com.ccarrasco.msvc.atenciones.models.Paciente;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "msvc-pacientes", url = "localhost:8080/api/v1/pacientes")
public interface PacienteClientRest {

    @GetMapping("/{id}")
    public Paciente findById(@PathVariable Long id);

}

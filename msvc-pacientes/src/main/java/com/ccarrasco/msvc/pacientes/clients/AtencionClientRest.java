package com.ccarrasco.msvc.pacientes.clients;

import com.ccarrasco.msvc.pacientes.models.Atencion;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "msvc-atenciones", url="localhost:8083/api/v1/atenciones")
public interface AtencionClientRest {

    @GetMapping("/paciente/{id}")
    List<Atencion> findByIdPaciente(@PathVariable Long id);

}

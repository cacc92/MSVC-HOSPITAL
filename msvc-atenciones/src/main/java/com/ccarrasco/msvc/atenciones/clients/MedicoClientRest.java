package com.ccarrasco.msvc.atenciones.clients;

import com.ccarrasco.msvc.atenciones.models.Medico;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-medicos", url = "localhost:8081/api/v1/medicos")
public interface MedicoClientRest {

    @GetMapping("/{id}")
    Medico findById(@PathVariable Long id);


}

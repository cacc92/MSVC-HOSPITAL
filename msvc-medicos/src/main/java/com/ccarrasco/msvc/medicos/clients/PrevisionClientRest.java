package com.ccarrasco.msvc.medicos.clients;

import com.ccarrasco.msvc.medicos.models.Prevision;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-prevision", url = "localhost:8082/api/v1/previsiones")
public interface PrevisionClientRest {

    @GetMapping("/{id}")
    Prevision findById(@PathVariable Long id);
}

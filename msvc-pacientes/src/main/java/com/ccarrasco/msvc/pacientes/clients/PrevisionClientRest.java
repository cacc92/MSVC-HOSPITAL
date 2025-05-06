package com.ccarrasco.msvc.pacientes.clients;


import com.ccarrasco.msvc.pacientes.models.Prevision;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "msvc-prevision", url = "localhost:8082/api/v1/previsiones")
public interface PrevisionClientRest {

    @GetMapping
    List<Prevision> findAll();

    @GetMapping("/{id}")
    Prevision findById(@PathVariable Long id);

}

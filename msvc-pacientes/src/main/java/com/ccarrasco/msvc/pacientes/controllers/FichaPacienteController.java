package com.ccarrasco.msvc.pacientes.controllers;

import com.ccarrasco.msvc.pacientes.dtos.FichaPacienteCreationDTO;
import com.ccarrasco.msvc.pacientes.models.FichaPaciente;
import com.ccarrasco.msvc.pacientes.services.FichaPacienteService;
import com.ccarrasco.msvc.pacientes.services.PacienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ficha-pacientes")
@Validated
public class FichaPacienteController {
    @Autowired
    private FichaPacienteService fichaPacienteService;

    @GetMapping
    public ResponseEntity<List<FichaPaciente>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(this.fichaPacienteService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FichaPaciente> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.fichaPacienteService.findById(id));
    }

    @PostMapping
    public ResponseEntity<FichaPaciente> save(@Valid @RequestBody FichaPacienteCreationDTO fichaPaciente) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.fichaPacienteService.save(fichaPaciente));
    }

}

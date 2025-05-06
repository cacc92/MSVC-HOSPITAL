package com.ccarrasco.msvc.pacientes.controllers;

import com.ccarrasco.msvc.pacientes.models.entities.Paciente;
import com.ccarrasco.msvc.pacientes.services.PacienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pacientes")
@Validated
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @GetMapping
    public ResponseEntity<List<Paciente>> findAll() {
        List<Paciente> pacientes = this.pacienteService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(pacientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> findById(@PathVariable Long id) {
        Paciente paciente = this.pacienteService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(paciente);
    }

    @PostMapping
    public ResponseEntity<Paciente> save(@Valid @RequestBody Paciente paciente) {
        Paciente saved = this.pacienteService.save(paciente);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }


}

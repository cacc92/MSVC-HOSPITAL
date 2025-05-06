package com.ccarrasco.msvc.medicos.controllers;

import com.ccarrasco.msvc.medicos.models.Medico;
import com.ccarrasco.msvc.medicos.services.MedicoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/medicos")
@Validated
public class MedicoController {

    @Autowired
    private MedicoService medicoService;

    @GetMapping
    public ResponseEntity<List<Medico>> findAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.medicoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medico> findById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.medicoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Medico>  create(@Valid @RequestBody Medico medico) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.medicoService.save(medico));
    }

}

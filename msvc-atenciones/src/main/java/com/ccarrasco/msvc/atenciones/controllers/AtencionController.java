package com.ccarrasco.msvc.atenciones.controllers;

import com.ccarrasco.msvc.atenciones.dtos.AtencionDTO;
import com.ccarrasco.msvc.atenciones.models.entities.Atencion;
import com.ccarrasco.msvc.atenciones.services.AtencionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/atenciones")
@Validated
public class AtencionController {

    @Autowired
    private AtencionService atencionService;

    @GetMapping
    public ResponseEntity<List<AtencionDTO>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(this.atencionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Atencion> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.atencionService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Atencion> save(@RequestBody @Valid Atencion atencion) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.atencionService.save(atencion));
    }

    // Estos metodos nos permitiran mostrar las atenciones filtradas par aun paciente

    @GetMapping("/paciente/{id}")
    public ResponseEntity<List<Atencion>> findByIdPaciente(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.atencionService.findByPacienteId(id));
    }

    @GetMapping("/medico/{id}")
    public ResponseEntity<List<Atencion>> findByIdMedico(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.atencionService.findByMedicoId(id));
    }

}
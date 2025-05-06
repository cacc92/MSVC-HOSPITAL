package com.ccarrasco.msvc.prevision.controllers;

import com.ccarrasco.msvc.prevision.models.Prevision;
import com.ccarrasco.msvc.prevision.services.PrevisionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/previsiones")
@Validated
public class PrevisionControllers {
    @Autowired
    private PrevisionService previsionService;

    @GetMapping
    public ResponseEntity<List<Prevision>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(this.previsionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Prevision> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.previsionService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Prevision> save(@Valid @RequestBody Prevision prevision) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.previsionService.save(prevision));
    }
}

package com.ccarrasco.msvc.medicos.controllers;

import com.ccarrasco.msvc.medicos.dtos.AtencionMedicoDTO;
import com.ccarrasco.msvc.medicos.models.entities.Medico;
import com.ccarrasco.msvc.medicos.services.MedicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/medicos")
@Validated
@Tag(name = "Medicos", description = "Operaciones CRUD de medicos")
public class MedicoController {

    @Autowired
    private MedicoService medicoService;

    @GetMapping
    @Operation(summary = "Obtiene todos los medicos", description = "Devuele un List de Medicos en el Body")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Operacion existosa")
    })
    public ResponseEntity<List<Medico>> findAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.medicoService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtiene un medico", description = "A través del id suministrado devuelve el medico con esa id")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Operacion existosa"),
            @ApiResponse(responseCode = "404", description = "Medico no encontrado"),
    })
    @Parameters(value = {
            @Parameter(name="id", description = "Este es el id unico del medico", required = true)
    })
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


    @GetMapping("/{id}/atenciones")
    public ResponseEntity<List<AtencionMedicoDTO>> findAtencionesById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.medicoService.findAtencionesById(id));
    }
}

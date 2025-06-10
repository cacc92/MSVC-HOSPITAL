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
@Tag(name = "Medicos", description = "Esta sección contiene los CRUD de medicos")
public class MedicoController {

    @Autowired
    private MedicoService medicoService;

    @GetMapping
    @Operation(
            summary = "Devuelve todos los medicos",
            description = "Este metodo debe retornar un List de Medico, en caso "+
                    "de que no encuentre nada retorna una List vacia"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se retornaron todos los medicos ok")
    })
    public ResponseEntity<List<Medico>> findAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.medicoService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Devuelve un medico con respecto a su id",
            description = "Este metodo debe retornar un Medico cuando es consultado "+
                    "mediante su id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se retorna el medico encontrado"),
            @ApiResponse(responseCode = "400", description = "Error - Medico con ID no existe")
    })
    @Parameters(value = {
            @Parameter(name = "id", description = "Este es el id unico de un medico", required = true)
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

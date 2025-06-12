package com.ccarrasco.msvc.medicos.controllers;

import com.ccarrasco.msvc.medicos.dtos.AtencionMedicoDTO;
import com.ccarrasco.msvc.medicos.dtos.ErrorDTO;
import com.ccarrasco.msvc.medicos.models.entities.Medico;
import com.ccarrasco.msvc.medicos.services.MedicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/medicos")
@Validated
@Tag(
        name = "Medico API",
        description = "Aquí se generar todos los métodos CRUD para medico"
)
public class MedicoController {

    @Autowired
    private MedicoService medicoService;

    @GetMapping
    @Operation(
            summary = "Endpoint que obtiene todos los medicos",
            description = "Este endpoint devuelve en un List todos los médicos que se encuentren " +
                    "en la base de datos"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Operacion de extración de medicos exitosa"
            )
    })
    public ResponseEntity<List<Medico>> findAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.medicoService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Endpoint que devuelve un médico por id",
            description = "Endpoint que va devolver un Medico.class al momento de buscarlo por id"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Obtención por id correcta"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Error cuando el medico con cierto id no existe",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class)
                            //examples = @ExampleObject(
                            //        name = "ERROR NOT FOUND",
                            //        value = "{\"status\":\"200\", \"error\":\"medico no encontrado\"}"
                            //)
                    )
            )
    })
    @Parameters(value = {
            @Parameter(
                    name = "id",
                    description = "Primary Key - Entidad médico",
                    required = true
            )

    })
    public ResponseEntity<Medico> findById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.medicoService.findById(id));
    }

    @PostMapping
    @Operation(
            summary = "Endpoint guardado de un médico",
            description = "Endpoint que me permite capturar un elemento Medico.class y lo guarda " +
                    "dentro de nuestra base de datos"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Creacion exitosa"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Algun elemento de un msvc no se encuentra",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "El elemento que intentas crear ya existe",
                    content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ErrorDTO.class)
                    )
            )
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Estructura de datos que me permite realizar la creación de un médico",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Medico.class)
            )
    )
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

package com.ccarrasco.msvc.medicos.controllers;

import com.ccarrasco.msvc.medicos.assemblers.AtencionMedicoDTOModelAssembler;
import com.ccarrasco.msvc.medicos.assemblers.MedicoModelAssembler;
import com.ccarrasco.msvc.medicos.dtos.AtencionMedicoDTO;
import com.ccarrasco.msvc.medicos.dtos.ErrorDTO;
import com.ccarrasco.msvc.medicos.models.entities.Medico;
import com.ccarrasco.msvc.medicos.services.MedicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/medicos")
@Validated
@Tag(name = "Medicos V2", description = "Operaciones CRUD de medicos hateoas")
public class MedicoControllerV2 {

    @Autowired
    private MedicoService medicoService;

    @Autowired
    private MedicoModelAssembler medicoModelAssembler;

    @Autowired
    private AtencionMedicoDTOModelAssembler  atencionMedicoDTOModelAssembler;

    @GetMapping
    @Operation(summary = "Obtiene todos los medicos", description = "Devuele un List de Medicos en el Body")
    @ApiResponses( value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Operacion existosa",
                    content = @Content(
                            mediaType = MediaTypes.HAL_JSON_VALUE,
                            schema = @Schema(implementation = Medico.class)
                    )
            )
    })
    public ResponseEntity<CollectionModel<EntityModel<Medico>>> findAll() {
        List<EntityModel<Medico>> entityModels = this.medicoService.findAll()
                .stream()
                .map(medicoModelAssembler::toModel)
                .toList();

        CollectionModel<EntityModel<Medico>> collectionModel = CollectionModel.of(
                entityModels,
                linkTo(methodOn(MedicoControllerV2.class).findAll()).withSelfRel()

        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(collectionModel);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtiene un medico", description = "A través del id suministrado devuelve el medico con esa id")
    @ApiResponses( value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Operacion existosa",
                    content = @Content(
                            mediaType = MediaTypes.HAL_JSON_VALUE,
                            schema = @Schema(implementation = Medico.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Medico no encontrado, con el id suministrado",
                    content = @Content(
                                mediaType = "application/json",
                                schema =  @Schema(implementation = ErrorDTO.class)
                    )
            )
    })
    @Parameters(value = {
            @Parameter(name="id", description = "Este es el id unico del medico", required = true)
    })
    public ResponseEntity<EntityModel<Medico>> findById(@PathVariable Long id) {
        EntityModel<Medico> entityModel = this.medicoModelAssembler.toModel(
                this.medicoService.findById(id)
        );
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(entityModel);
    }

    @PostMapping
    @Operation(
            summary = "Guarda un medico",
            description = "Con este método podemos enviar los datos mediante un body y realizar el guardado"
    )
    @ApiResponses( value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Guardado exitoso",
                    content = @Content(
                            mediaType = MediaTypes.HAL_JSON_VALUE,
                            schema = @Schema(implementation = Medico.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "El medico guardado ya se encuentra en la base de datos",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class)
                    )
            )
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "medico a crear",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Medico.class)
            )
    )
    public ResponseEntity<EntityModel<Medico>>  create(@Valid @RequestBody Medico medico) {
        Medico medicoNew = this.medicoService.save(medico);
        EntityModel<Medico> entityModel = this.medicoModelAssembler.toModel(medicoNew);

        return ResponseEntity
                .created(linkTo(methodOn(MedicoControllerV2.class).findById(medicoNew.getIdMedico())).toUri())
                .body(entityModel);
    }


    @GetMapping("/{id}/atenciones")
    @Operation(summary = "Obtiene un medico", description = "A través del id suministrado devuelve el medico con esa id")
    @ApiResponses( value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Operacion existosa",
                    content = @Content(
                            mediaType = MediaTypes.HAL_JSON_VALUE,
                            schema = @Schema(implementation = AtencionMedicoDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Medico no encontrado, con el id suministrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema =  @Schema(implementation = ErrorDTO.class)
                    )
            )
    })
    @Parameters(value = {
            @Parameter(name="id", description = "Este es el id unico del medico", required = true)
    })
    public ResponseEntity<CollectionModel<EntityModel<AtencionMedicoDTO>>> findAtencionesById(@PathVariable Long id) {
        List<EntityModel<AtencionMedicoDTO>> entityModels = this.medicoService.findAtencionesById(id)
                .stream()
                .map(atencionMedicoDTOModelAssembler::toModel)
                .toList();

        CollectionModel<EntityModel<AtencionMedicoDTO>> collectionModel = CollectionModel.of(
                entityModels,
                linkTo(methodOn(MedicoControllerV2.class).findById(id)).withSelfRel()
        );


        return ResponseEntity.status(HttpStatus.OK).body(collectionModel);
    }
}

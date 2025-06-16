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
@Tag(
        name = "Medico API HATEOAS",
        description = "Aquí se generar todos los métodos CRUD para medico"
)
public class MedicoControllerV2 {

    @Autowired
    private MedicoService medicoService;

    @Autowired
    private MedicoModelAssembler medicoModelAssembler;

    @Autowired
    private AtencionMedicoDTOModelAssembler  atencionMedicoDTOModelAssembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(
            summary = "Endpoint que obtiene todos los medicos",
            description = "Este endpoint devuelve en un List todos los médicos que se encuentren " +
                    "en la base de datos"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Operacion de extración de medicos exitosa",
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

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(
            summary = "Endpoint que devuelve un médico por id",
            description = "Endpoint que va devolver un Medico.class al momento de buscarlo por id"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Obtención por id correcta",
                    content = @Content(
                            mediaType = MediaTypes.HAL_JSON_VALUE,
                            schema = @Schema(implementation = Medico.class)
                    )
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
    public ResponseEntity<EntityModel<Medico>> findById(@PathVariable Long id) {
        EntityModel<Medico> entityModel = this.medicoModelAssembler.toModel(
                this.medicoService.findById(id)
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(entityModel);
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(
            summary = "Endpoint guardado de un médico",
            description = "Endpoint que me permite capturar un elemento Medico.class y lo guarda " +
                    "dentro de nuestra base de datos"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Creacion exitosa",
                    content = @Content(
                            mediaType = MediaTypes.HAL_JSON_VALUE,
                            schema = @Schema(implementation = Medico.class)
                    )
            ),
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
    public ResponseEntity<EntityModel<Medico>>  create(@Valid @RequestBody Medico medico) {
        Medico medicoNew = this.medicoService.save(medico);
        EntityModel<Medico> entityModel = this.medicoModelAssembler.toModel(medicoNew);
        return ResponseEntity
                .created(linkTo(methodOn(MedicoControllerV2.class).findById(medicoNew.getIdMedico())).toUri())
                .body(entityModel);
    }


    @GetMapping(value = "/{id}/atenciones", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(
            summary = "Endpoint que devuelve un médico por id",
            description = "Endpoint que va devolver un Medico.class al momento de buscarlo por id"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Obtención por id correcta",
                    content = @Content(
                            mediaType = MediaTypes.HAL_JSON_VALUE,
                            schema = @Schema(implementation = AtencionMedicoDTO.class)
                    )
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
    public ResponseEntity<CollectionModel<EntityModel<AtencionMedicoDTO>>> findAtencionesById(@PathVariable Long id) {
        List<EntityModel<AtencionMedicoDTO>> entityModels = this.medicoService.findAtencionesById(id)
                .stream()
                .map(atencionMedicoDTOModelAssembler::toModel)
                .toList();

        CollectionModel<EntityModel<AtencionMedicoDTO>> collectionModel = CollectionModel.of(
                entityModels,
                linkTo(methodOn(MedicoControllerV2.class).findAtencionesById(id)).withRel("atenciones-medico")
        );
        return ResponseEntity.status(HttpStatus.OK).body(collectionModel);
    }
}

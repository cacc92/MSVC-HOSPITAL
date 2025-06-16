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
@Tag(name = "Medicos HATEOAS", description = "Esta sección contiene los CRUD de medicos")
public class MedicoControllerV2 {

    @Autowired
    private MedicoService medicoService;

    @Autowired
    private MedicoModelAssembler medicoModelAssembler;

    @Autowired
    private AtencionMedicoDTOModelAssembler atencionMedicoDTOModelAssembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(
            summary = "Devuelve todos los medicos",
            description = "Este metodo debe retornar un List de Medico, en caso "+
                    "de que no encuentre nada retorna una List vacia"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Se retornaron todos los medicos ok",
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

    @GetMapping(value = "/{id}",  produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(
            summary = "Devuelve un medico con respecto a su id",
            description = "Este metodo debe retornar un Medico cuando es consultado "+
                    "mediante su id"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Se retorna el medico encontrado",
                    content = @Content(
                            mediaType = MediaTypes.HAL_JSON_VALUE,
                            schema = @Schema(implementation = Medico.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Error - Medico con ID no existe",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class)
                            //examples = @ExampleObject(
                            //        name = "ERROR DTO SIN CLASE",
                            //        value = "{\"code\":\"200\", \"error\": \"mensaje de error\"}"
                            //)
                    )
            )
    })
    @Parameters(value = {
            @Parameter(name = "id", description = "Este es el id unico de un medico", required = true)
    })
    public ResponseEntity<EntityModel<Medico>> findById(@PathVariable Long id) {
        EntityModel<Medico> entityModel = this.medicoModelAssembler.toModel(
                medicoService.findById(id)
        );
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(entityModel);
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(
            summary = "Endpoint que me permite guardar un medico",
            description = "Este endpoint debo mandar un body con el formato de Medico.class " +
                    "y me permitirá realizar la creación de un medico"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Medico creado correctamente",
                    content = @Content(
                            mediaType = MediaTypes.HAL_JSON_VALUE,
                            schema = @Schema(implementation = Medico.class)
                    )

            )
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            description = "Este debe ser Json con los datos de medico",
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
            summary = "Devuelve todas las atenciones de un medico con respecto a su id",
            description = "Este metodo debe retornar las atenciones del Medico cuando es consultado "+
                    "mediante su id"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Se retorna el medico encontrado",
                    content = @Content(
                            mediaType = MediaTypes.HAL_JSON_VALUE,
                            schema = @Schema(implementation = AtencionMedicoDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Error - Medico con ID no existe",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class)
                    )
            )
    })
    @Parameters(value = {
            @Parameter(name = "id", description = "Este es el id unico de un medico", required = true)
    })
    public ResponseEntity<CollectionModel<EntityModel<AtencionMedicoDTO>>> findAtencionesById(@PathVariable Long id) {
        List<EntityModel<AtencionMedicoDTO>> entityModels = this.medicoService.findAtencionesById(id)
                .stream()
                .map(atencionMedicoDTOModelAssembler::toModel)
                .toList();

        CollectionModel<EntityModel<AtencionMedicoDTO>> collectionModel = CollectionModel.of(
          entityModels,
          linkTo(methodOn(MedicoControllerV2.class).findAtencionesById(id)).withSelfRel()
        );
        return ResponseEntity.status(HttpStatus.OK).body(collectionModel);
    }
}

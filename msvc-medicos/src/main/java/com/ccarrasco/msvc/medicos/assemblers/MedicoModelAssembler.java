package com.ccarrasco.msvc.medicos.assemblers;

import com.ccarrasco.msvc.medicos.controllers.MedicoControllerV2;
import com.ccarrasco.msvc.medicos.models.entities.Medico;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class MedicoModelAssembler implements RepresentationModelAssembler<Medico, EntityModel<Medico>> {


    @Override
    public EntityModel<Medico> toModel(Medico entity) {
        return EntityModel.of(
                entity,
                linkTo(methodOn(MedicoControllerV2.class).findById(entity.getIdMedico())).withSelfRel(),
                linkTo(methodOn(MedicoControllerV2.class).findAll()).withRel("medicos"),
                linkTo(methodOn(MedicoControllerV2.class).findAtencionesById(entity.getIdMedico())).withRel("atenciones-medicos"),
                Link.of("http://localhost:8080/sucursal/"+entity.getIdMedico()).withRel("Sucursales"),
                Link.of("http://localhost:8082/cliente/"+entity.getIdMedico()).withRel("cliente")
        );
    }
}
